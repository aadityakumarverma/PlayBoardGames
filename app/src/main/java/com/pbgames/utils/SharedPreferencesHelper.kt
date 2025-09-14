package com.pbgames.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesHelper constructor(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("ZemstoJetcomposeAppPreferences", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private var instance: SharedPreferencesHelper? = null

        fun getInstance(context: Context): SharedPreferencesHelper {
            if (instance == null) {
                synchronized(SharedPreferencesHelper::class) {
                    instance = SharedPreferencesHelper(context)
                }
            }
            return instance!!
        }
    }

    // Helper methods to save and retrieve data
    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue) ?: defaultValue
    }

    /*fun putUser(key: String, user: MyUserData) {
        val json = gson.toJson(user)
        sharedPreferences.edit().putString(key, json).apply()
    }

    fun getUser(key: String, defaultValue: MyUserData): MyUserData {
        val json = sharedPreferences.getString(key, null)
        return if (json != null) {
            gson.fromJson(json, MyUserData::class.java)
        } else {
            defaultValue
        }
    }
*/

    fun putStringList(key: String, list: MutableList<String>) {
        val json = gson.toJson(list)
        sharedPreferences.edit().putString(key, json).apply()
    }

    // New method to retrieve ArrayList<String>
    fun getStringList(key: String, defaultValue: MutableList<String>): MutableList<String> {
        val json = sharedPreferences.getString(key, null)
        return if (json != null) {
            val type = object : TypeToken<MutableList<String>>() {}.type
            gson.fromJson(json, type)
        } else {
            defaultValue
        }
    }

}
