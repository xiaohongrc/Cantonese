package hong.cantonese.utils

import android.content.Context
import android.content.SharedPreferences
import hong.cantonese.ConstantValue

/**
 * Created by Xiaohong on 2019-05-23.
 * desc:
 */
object MyPref {

    private const val PREF_NAME = "my_pref"
    private const val PREF_KEY_READING_LANGUAGE = "pref_key_reading_language"

    private lateinit var prefs: SharedPreferences


    fun initPref(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }


    // 存储朗读语言
    fun cacheReadingLanguage(readingLanguage: String) {
        prefs.edit().putString(PREF_KEY_READING_LANGUAGE, readingLanguage).apply()
    }

    fun readReadingLanguage(): String = prefs.getString(PREF_KEY_READING_LANGUAGE, ConstantValue.CANTONESE)


}