package com.innerisle.app.data

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.AtomicFile
import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.File
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * Stores the whole app dataset as one AES-GCM encrypted file.
 * The key never leaves Android Keystore.
 */
class SecureStore(context: Context) {

    private val appContext = context.applicationContext
    private val dataFile = File(appContext.filesDir, "inner_isle_data.enc")
    private val keyAlias = "inner_isle_data_key"
    private val mutex = Mutex()

    suspend fun load(): String? = withContext(Dispatchers.IO) {
        mutex.withLock {
            if (!dataFile.exists()) return@withLock null
            runCatching {
                val parts = dataFile.readText(Charsets.UTF_8).split('\n', limit = 2)
                require(parts.size == 2) { "Invalid data file" }
                val iv = Base64.decode(parts[0], Base64.NO_WRAP)
                val cipherText = Base64.decode(parts[1], Base64.NO_WRAP)
                val cipher = Cipher.getInstance(TRANSFORMATION)
                cipher.init(Cipher.DECRYPT_MODE, getOrCreateKey(), GCMParameterSpec(128, iv))
                String(cipher.doFinal(cipherText), Charsets.UTF_8)
            }.getOrElse {
                runCatching {
                    val corrupt = File(appContext.filesDir, "inner_isle_data.corrupt")
                    if (corrupt.exists()) corrupt.delete()
                    dataFile.copyTo(corrupt, overwrite = true)
                }
                null
            }
        }
    }

    suspend fun save(json: String) = withContext(Dispatchers.IO) {
        mutex.withLock {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, getOrCreateKey())
            val cipherText = cipher.doFinal(json.toByteArray(Charsets.UTF_8))
            val payload = buildString {
                append(Base64.encodeToString(cipher.iv, Base64.NO_WRAP))
                append('\n')
                append(Base64.encodeToString(cipherText, Base64.NO_WRAP))
            }.toByteArray(Charsets.UTF_8)

            val atomicFile = AtomicFile(dataFile)
            val out = atomicFile.startWrite()
            try {
                out.write(payload)
                atomicFile.finishWrite(out)
            } catch (t: Throwable) {
                atomicFile.failWrite(out)
                throw t
            }
        }
    }

    suspend fun clear() = withContext(Dispatchers.IO) {
        mutex.withLock {
            dataFile.delete()
            runCatching {
                val keyStore = KeyStore.getInstance(KEYSTORE).apply { load(null) }
                if (keyStore.containsAlias(keyAlias)) keyStore.deleteEntry(keyAlias)
            }
        }
    }

    private fun getOrCreateKey(): SecretKey {
        val keyStore = KeyStore.getInstance(KEYSTORE).apply { load(null) }
        val existing = (keyStore.getEntry(keyAlias, null) as? KeyStore.SecretKeyEntry)?.secretKey
        if (existing != null) return existing

        val generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE)
        val spec = KeyGenParameterSpec.Builder(
            keyAlias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .setRandomizedEncryptionRequired(true)
            .build()
        generator.init(spec)
        return generator.generateKey()
    }

    private companion object {
        const val KEYSTORE = "AndroidKeyStore"
        const val TRANSFORMATION = "AES/GCM/NoPadding"
    }
}
