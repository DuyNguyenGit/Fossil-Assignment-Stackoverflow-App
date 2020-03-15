package com.fossil.duy.stackoverflow.common

import android.content.Context
import android.content.SharedPreferences


class SharedPreference(val context: Context) {
    private val PREFS_NAME = "app_data"

    companion object {
        const val DISPLAY_ONLY_BOORMARK_USER = "display_only_bookmark_users"
    }

    val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, status: Boolean) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putBoolean(KEY_NAME, status)

        editor.apply()
    }

    fun getValueBoolien(KEY_NAME: String, defaultValue: Boolean): Boolean {

        return sharedPref.getBoolean(KEY_NAME, defaultValue)

    }
}
