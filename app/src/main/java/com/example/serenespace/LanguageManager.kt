package com.example.serenespace

import android.content.Context
import android.content.SharedPreferences

data class AppLanguage(val code: String, val displayName: String)

object LanguageManager {
    private const val PREF_NAME = "SereneSpacePrefs"
    private const val LANGUAGE_KEY = "selected_language"

    // A comprehensive list of languages
    val supportedLanguages = listOf(
        AppLanguage("en", "English"),
        AppLanguage("ur", "اردو (Urdu)"),
        AppLanguage("tr", "Türkçe (Turkish)"),
        AppLanguage("ar", "العربية (Arabic)"),
        AppLanguage("fr", "Français (French)"),
        AppLanguage("es", "Español (Spanish)"),
        AppLanguage("de", "Deutsch (German)"),
        AppLanguage("hi", "हिन्दी (Hindi)"),
        AppLanguage("zh", "中文 (Chinese)"),
        AppLanguage("ja", "日本語 (Japanese)"),
        AppLanguage("ko", "한국어 (Korean)"),
        AppLanguage("pt", "Português (Portuguese)"),
        AppLanguage("ru", "Русский (Russian)"),
        AppLanguage("it", "Italiano (Italian)"),
        AppLanguage("bn", "বাংলা (Bengali)"),
        AppLanguage("fa", "فارسی (Persian)"),
        AppLanguage("id", "Bahasa Indonesia (Indonesian)"),
        AppLanguage("th", "ไทย (Thai)"),
        AppLanguage("vi", "Tiếng Việt (Vietnamese)"),
        AppLanguage("nl", "Nederlands (Dutch)")
    )

    fun saveLanguage(context: Context, languageCode: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(LANGUAGE_KEY, languageCode).apply()
    }

    fun getLanguage(context: Context): String {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(LANGUAGE_KEY, "en") ?: "en"
    }
}