package com.utachiwana.messenger.data.local

import android.content.Context
import com.utachiwana.messenger.dagger.Auth
import javax.inject.Inject

@Auth
class AppSharedPreferences @Inject constructor(val context: Context) {

    companion object {
        const val SETTINGS = "settings"
    }

    private val prefs = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    private val edit = prefs.edit()

    fun getString(field: String): String = prefs.getString(field, "") ?: ""

    fun putString(field: String, value: String) = edit.putString(field, value).commit()
    fun logout() = edit.clear().commit()

}