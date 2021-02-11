package com.hassanhamdy.va_task.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPref {
    private val SHARED_PREFERENCE_NAME: String = "VA_COMPUTING"
    private val SERVED_LIST_KEY: String = "Served"
    private val PENDING_LIST_KEY: String = "pending"
    private lateinit var sharedPreference: SharedPreferences

    companion object {
        private var INSTANCE: SharedPref? = null

        val instance: SharedPref
            get() {
                if (INSTANCE == null) {
                    INSTANCE = SharedPref()
                }

                return INSTANCE!!
            }
    }

    fun initSharedPref(context: Context) {
        sharedPreference =
            context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun getServedListJson(): String {
        return sharedPreference.getString(SERVED_LIST_KEY, "") ?: ""
    }

    fun getPendingListJson(): String {
        return sharedPreference.getString(PENDING_LIST_KEY, "") ?: ""
    }

    fun putServedListJson(value: String) {
        val editor = sharedPreference.edit()
        editor.putString(SERVED_LIST_KEY, value)
        editor.apply()
    }

    fun putPendingListJson(value: String) {
        val editor = sharedPreference.edit()
        editor.putString(PENDING_LIST_KEY, value)
        editor.apply()
    }
}