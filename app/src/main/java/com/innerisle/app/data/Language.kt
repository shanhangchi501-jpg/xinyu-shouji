package com.innerisle.app.data

enum class Language(val tag: String) {
    ZH("zh"),
    EN("en")
}

/**
 * Lightweight runtime localisation. The app is rebuilt on language change,
 * so a plain global is enough and keeps the data/content layer simple.
 */
object L {
    var language: Language = Language.ZH

    val isEn: Boolean
        get() = language == Language.EN

    fun t(zh: String, en: String): String = if (isEn) en else zh
}

fun tr(zh: String): String =
    if (L.isEn) UiTranslationsEn[zh] ?: zh else zh
