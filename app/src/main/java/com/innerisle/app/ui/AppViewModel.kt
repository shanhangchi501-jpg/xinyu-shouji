package com.innerisle.app.ui

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.innerisle.app.data.AppData
import com.innerisle.app.data.JournalEntry
import com.innerisle.app.data.Member
import com.innerisle.app.data.MemberRole
import com.innerisle.app.data.Prefs
import com.innerisle.app.data.SecureStore
import com.innerisle.app.data.Session
import com.innerisle.app.data.SessionType
import com.innerisle.app.work.ReminderScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

data class UiSettings(
    val onboardingComplete: Boolean,
    val reminderEnabled: Boolean,
    val reminderHour: Int,
    val reminderMinute: Int,
    val stealthNotifications: Boolean,
    val appLockEnabled: Boolean,
    val preventScreenshots: Boolean,
    val hasPin: Boolean,
    val guideReadIds: Set<String> = emptySet()
) {
    companion object {
        fun from(prefs: Prefs) = UiSettings(
            onboardingComplete = prefs.onboardingComplete,
            reminderEnabled = prefs.reminderEnabled,
            reminderHour = prefs.reminderHour,
            reminderMinute = prefs.reminderMinute,
            stealthNotifications = prefs.stealthNotifications,
            appLockEnabled = prefs.appLockEnabled,
            preventScreenshots = prefs.preventScreenshots,
            hasPin = prefs.hasPin,
            guideReadIds = prefs.guideReadIds
        )
    }
}

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application
    private val store = SecureStore(application)
    private val prefs = Prefs(application)
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }
    private val saveMutex = Mutex()
    private var backgroundAt = 0L

    private val _data = MutableStateFlow(AppData())
    val data = _data.asStateFlow()

    private val _settings = MutableStateFlow(UiSettings.from(prefs))
    val settings = _settings.asStateFlow()

    private val _loaded = MutableStateFlow(false)
    val loaded = _loaded.asStateFlow()

    private val _locked = MutableStateFlow(prefs.appLockEnabled && prefs.hasPin)
    val locked = _locked.asStateFlow()

    init {
        viewModelScope.launch {
            val raw = runCatching { store.load() }.getOrNull()
            val parsed = raw
                ?.takeIf { it.isNotBlank() }
                ?.let { runCatching { json.decodeFromString<AppData>(it) }.getOrNull() }
                ?: AppData()
            _data.value = ensureHost(parsed)
            _loaded.value = true
        }
    }

    private fun ensureHost(data: AppData): AppData {
        if (data.host != null) return data
        val host = Member(
            id = "host",
            role = MemberRole.HOST,
            name = "我",
            emoji = "🫧",
            color = "#7E8CA0"
        )
        return data.copy(members = listOf(host) + data.members)
    }

    private fun update(transform: (AppData) -> AppData) {
        _data.value = transform(_data.value)
        persist()
    }

    private fun persist() {
        viewModelScope.launch {
            saveMutex.withLock {
                runCatching { store.save(json.encodeToString(_data.value)) }
            }
        }
    }

    private fun refreshSettings() {
        _settings.value = UiSettings.from(prefs)
    }

    fun completeOnboarding(tulpaName: String, emoji: String, color: String) {
        val name = tulpaName.trim().ifBlank { "未命名" }
        update { current ->
            val members = current.members.toMutableList()
            if (members.none { it.role == MemberRole.HOST }) {
                members.add(
                    0,
                    Member(
                        id = "host",
                        role = MemberRole.HOST,
                        name = "我",
                        emoji = "🫧",
                        color = "#7E8CA0"
                    )
                )
            }
            members.add(
                Member(
                    role = MemberRole.TULPA,
                    name = name,
                    emoji = emoji,
                    color = color
                )
            )
            current.copy(members = members)
        }
        prefs.onboardingComplete = true
        refreshSettings()
    }

    fun addMember(
        name: String,
        emoji: String,
        color: String,
        form: String,
        personality: String,
        voice: String,
        likes: String,
        notes: String
    ) {
        update { current ->
            current.copy(
                members = current.members + Member(
                    role = MemberRole.TULPA,
                    name = name.trim().ifBlank { "未命名" },
                    emoji = emoji,
                    color = color,
                    form = form.trim(),
                    personality = personality.trim(),
                    voice = voice.trim(),
                    likes = likes.trim(),
                    notes = notes.trim()
                )
            )
        }
    }

    fun updateMember(member: Member) {
        update { current ->
            current.copy(
                members = current.members.map {
                    if (it.id == member.id) member else it
                }
            )
        }
    }

    fun deleteMember(memberId: String) {
        update { current ->
            current.copy(members = current.members.filterNot { it.id == memberId })
        }
    }

    fun addSession(
        tulpaId: String,
        type: SessionType,
        startedAt: Long,
        minutes: Int,
        mood: String,
        note: String
    ) {
        update { current ->
            current.copy(
                sessions = current.sessions + Session(
                    tulpaId = tulpaId,
                    type = type,
                    startedAt = startedAt,
                    minutes = minutes.coerceAtLeast(1),
                    mood = mood,
                    note = note.trim()
                )
            )
        }
    }

    fun addOrUpdateJournal(entry: JournalEntry) {
        update { current ->
            val exists = current.journals.any { it.id == entry.id }
            val journals = if (exists) {
                current.journals.map {
                    if (it.id == entry.id) entry.copy(updatedAt = System.currentTimeMillis()) else it
                }
            } else {
                current.journals + entry
            }
            current.copy(journals = journals)
        }
    }

    fun deleteJournal(entryId: String) {
        update { current ->
            current.copy(journals = current.journals.filterNot { it.id == entryId })
        }
    }

    fun setReminderEnabled(enabled: Boolean) {
        prefs.reminderEnabled = enabled
        if (enabled) {
            ReminderScheduler.scheduleDaily(app, prefs.reminderHour, prefs.reminderMinute)
        } else {
            ReminderScheduler.cancelDaily(app)
        }
        refreshSettings()
    }

    fun setReminderTime(hour: Int, minute: Int) {
        prefs.reminderHour = hour
        prefs.reminderMinute = minute
        if (prefs.reminderEnabled) {
            ReminderScheduler.scheduleDaily(app, hour, minute)
        }
        refreshSettings()
    }

    fun setStealthNotifications(enabled: Boolean) {
        prefs.stealthNotifications = enabled
        refreshSettings()
    }

    fun setPreventScreenshots(enabled: Boolean) {
        prefs.preventScreenshots = enabled
        refreshSettings()
    }

    fun toggleGuideRead(id: String) {
        val current = prefs.guideReadIds
        prefs.guideReadIds = if (id in current) current - id else current + id
        refreshSettings()
    }

    fun enableAppLock(pin: String) {
        prefs.setPin(pin)
        prefs.appLockEnabled = true
        _locked.value = false
        refreshSettings()
    }

    fun setAppLockEnabled(enabled: Boolean) {
        if (enabled && !prefs.hasPin) return
        prefs.appLockEnabled = enabled
        _locked.value = false
        refreshSettings()
    }

    fun changePin(pin: String) {
        prefs.setPin(pin)
        refreshSettings()
    }

    fun disableAppLock() {
        prefs.clearPin()
        _locked.value = false
        refreshSettings()
    }

    fun verifyPin(pin: String): Boolean = prefs.verifyPin(pin)

    fun lock() {
        if (prefs.appLockEnabled && prefs.hasPin) {
            _locked.value = true
        }
    }

    fun unlock(pin: String): Boolean {
        return if (prefs.verifyPin(pin)) {
            _locked.value = false
            true
        } else {
            false
        }
    }

    fun onAppBackgrounded() {
        backgroundAt = System.currentTimeMillis()
    }

    fun onAppForegrounded() {
        if (backgroundAt > 0L &&
            prefs.appLockEnabled &&
            prefs.hasPin &&
            System.currentTimeMillis() - backgroundAt > 30_000L
        ) {
            _locked.value = true
        }
        backgroundAt = 0L
    }

    suspend fun exportTo(uri: Uri): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val text = json.encodeToString(_data.value)
            app.contentResolver.openOutputStream(uri)?.use {
                it.write(text.toByteArray(Charsets.UTF_8))
            } ?: error("无法打开目标文件")
        }
    }

    suspend fun importFrom(uri: Uri): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val text = app.contentResolver.openInputStream(uri)?.use {
                it.readBytes().toString(Charsets.UTF_8)
            } ?: error("无法读取文件")
            val parsed = json.decodeFromString<AppData>(text)
            val withHost = ensureHost(parsed)
            _data.value = withHost
            saveMutex.withLock {
                store.save(json.encodeToString(withHost))
            }
            prefs.onboardingComplete = true
            refreshSettings()
        }
    }

    fun deleteAllData() {
        viewModelScope.launch {
            saveMutex.withLock {
                runCatching { store.clear() }
            }
            prefs.reset()
            ReminderScheduler.cancelAll(app)
            _data.value = AppData()
            _settings.value = UiSettings.from(prefs)
            _locked.value = false
        }
    }
}
