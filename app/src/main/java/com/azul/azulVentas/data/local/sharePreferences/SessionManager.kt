package com.azul.azulVentas.data.local.sharePreferences

import android.content.SharedPreferences
import com.google.type.Date
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        private const val KEY_USER_UID = "key_user_uid"
        private const val KEY_USER_EMAIL = "key_user_email"
        private const val KEY_USER_LAST_DAY = "key_user_last_day"
        private const val KEY_IS_LOGGED_IN = "key_is_logged_in"
    }

    fun saveUserSession(uid: String, email: String, day_Date: String) {
        sharedPreferences.edit().putString(KEY_USER_UID, uid).apply()
        sharedPreferences.edit().putString(KEY_USER_EMAIL, email).apply()
        sharedPreferences.edit().putString(KEY_USER_LAST_DAY, day_Date).apply()
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, true).apply()
    }

    fun getUserSession(): String? {
        return sharedPreferences.getString(KEY_USER_UID, null) ?: ""
    }

    fun getUserEmail(): String? {
        return sharedPreferences.getString(KEY_USER_EMAIL, null)
    }

    fun getUserLastDay(): String? {
        return sharedPreferences.getString(KEY_USER_LAST_DAY, null)
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun clearSession() {
        //sharedPreferences.edit().remove(KEY_USER_UID).apply()
        //sharedPreferences.edit().remove(KEY_USER_EMAIL).apply()
        //sharedPreferences.edit().remove(KEY_USER_LAST_DAY).apply()

        sharedPreferences.edit().clear().apply()
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, false).apply()
    }

    fun isUserLoggedIn(): Boolean {
        return getUserSession() != null
    }

}
