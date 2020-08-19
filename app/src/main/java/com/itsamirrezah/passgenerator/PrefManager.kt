package com.itsamirrezah.passgenerator

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class PrefManager(context: Context) {

    private var pref: SharedPreferences? = null

    init {
        pref = PreferenceManager.getDefaultSharedPreferences(context)
    }

    var passwordLength: Int
        get() = pref!!.getInt("PASSWORD_LENGTH", 15)
        set(value) = pref!!.edit().putInt("PASSWORD_LENGTH", value).apply()

    var isUppercaseUsing: Boolean
        get() = pref!!.getBoolean("UPPER_CASE_ACTIVE", true)
        set(value) = pref!!.edit().putBoolean("UPPER_CASE_ACTIVE", value).apply()

    var isSymbolUsing: Boolean
        get() = pref!!.getBoolean("SYMBOL_ACTIVE", true)
        set(value) = pref!!.edit().putBoolean("SYMBOL_ACTIVE", value).apply()

    var isNumberUsing: Boolean
        get() = pref!!.getBoolean("NUMBER_ACTIVE", true)
        set(value) = pref!!.edit().putBoolean("NUMBER_ACTIVE", value).apply()

    companion object {
        var instance: PrefManager? = null

        fun getInstance(context: Context): PrefManager {
            if (instance == null) {
                instance = PrefManager(context)
            }
            return instance as PrefManager
        }
    }
}