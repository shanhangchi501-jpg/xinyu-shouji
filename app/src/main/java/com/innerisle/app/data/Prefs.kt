package com.innerisle.app.data

import android.content.Context
import android.util.Base64
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

/**
 * Non-sensitive settings and the app-lock PIN verifier.
 * The actual diary data lives in SecureStore, encrypted with an Android Keystore key.
 */
class Prefs(context: Context) {

    private val sp = context.applicationContext
        .getSharedPreferences("inner_isle_prefs", Context.MODE_PRIVATE)

    var onboardingComplete: Boolean
        get() = sp.getBoolean(KEY_ONBOARDING, false)
        set(value) = sp.edit().putBoolean(KEY_ONBOARDING, value).apply()

    var reminderEnabled: Boolean
        get() = sp.getBoolean(KEY_REMINDER_ENABLED, false)
        set(value) = sp.edit().putBoolean(KEY_REMINDER_ENABLED, value).apply()

    var reminderHour: Int
        get() = sp.getInt(KEY_REMINDER_HOUR, 20)
        set(value) = sp.edit().putInt(KEY_REMINDER_HOUR, value.coerceIn(0, 23)).apply()

    var reminderMinute: Int
        get() = sp.getInt(KEY_REMINDER_MINUTE, 30)
        set(value) = sp.edit().putInt(KEY_REMINDER_MINUTE, value.coerceIn(0, 59)).apply()

    var stealthNotifications: Boolean
        get() = sp.getBoolean(KEY_STEALTH, true)
        set(value) = sp.edit().putBoolean(KEY_STEALTH, value).apply()

    var appLockEnabled: Boolean
        get() = sp.getBoolean(KEY_APP_LOCK, false)
        set(value) = sp.edit().putBoolean(KEY_APP_LOCK, value).apply()

    var preventScreenshots: Boolean
        get() = sp.getBoolean(KEY_PREVENT_SCREENSHOTS, false)
        set(value) = sp.edit().putBoolean(KEY_PREVENT_SCREENSHOTS, value).apply()

    var guideReadIds: Set<String>
        get() = sp.getStringSet(KEY_GUIDE_READ, emptySet())?.toSet() ?: emptySet()
        set(value) = sp.edit().putStringSet(KEY_GUIDE_READ, value.toSet()).apply()

    val hasPin: Boolean
        get() = !sp.getString(KEY_PIN_HASH, null).isNullOrBlank()

    fun setPin(pin: String) {
        val salt = ByteArray(16).also { SecureRandom().nextBytes(it) }
        val iterations = PIN_ITERATIONS
        val hash = hashPin(pin, salt, iterations)
        sp.edit()
            .putString(KEY_PIN_SALT, Base64.encodeToString(salt, Base64.NO_WRAP))
            .putString(KEY_PIN_HASH, Base64.encodeToString(hash, Base64.NO_WRAP))
            .putInt(KEY_PIN_ITERATIONS, iterations)
            .apply()
    }

    fun verifyPin(pin: String): Boolean {
        val saltText = sp.getString(KEY_PIN_SALT, null) ?: return false
        val hashText = sp.getString(KEY_PIN_HASH, null) ?: return false
        val iterations = sp.getInt(KEY_PIN_ITERATIONS, LEGACY_PIN_ITERATIONS)
        return runCatching {
            val salt = Base64.decode(saltText, Base64.NO_WRAP)
            val expected = Base64.decode(hashText, Base64.NO_WRAP)
            MessageDigest.isEqual(expected, hashPin(pin, salt, iterations))
        }.getOrDefault(false)
    }

    fun clearPin() {
        sp.edit()
            .remove(KEY_PIN_SALT)
            .remove(KEY_PIN_HASH)
            .remove(KEY_PIN_ITERATIONS)
            .putBoolean(KEY_APP_LOCK, false)
            .apply()
    }

    fun reset() {
        sp.edit().clear().apply()
    }

    private fun hashPin(pin: String, salt: ByteArray, iterations: Int): ByteArray {
        val spec = PBEKeySpec(pin.toCharArray(), salt, iterations, 256)
        return try {
            SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                .generateSecret(spec)
                .encoded
        } finally {
            spec.clearPassword()
        }
    }

    private companion object {
        const val KEY_ONBOARDING = "onboarding_complete"
        const val KEY_REMINDER_ENABLED = "reminder_enabled"
        const val KEY_REMINDER_HOUR = "reminder_hour"
        const val KEY_REMINDER_MINUTE = "reminder_minute"
        const val KEY_STEALTH = "stealth_notifications"
        const val KEY_APP_LOCK = "app_lock_enabled"
        const val KEY_PREVENT_SCREENSHOTS = "prevent_screenshots"
        const val KEY_GUIDE_READ = "guide_read_ids"
        const val KEY_PIN_SALT = "pin_salt"
        const val KEY_PIN_HASH = "pin_hash"
        const val KEY_PIN_ITERATIONS = "pin_iterations"
        // This PIN is only a UI gate; the data itself is protected by Android Keystore.
        const val PIN_ITERATIONS = 30_000
        const val LEGACY_PIN_ITERATIONS = 120_000
    }
}
