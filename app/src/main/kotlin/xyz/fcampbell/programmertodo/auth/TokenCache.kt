package xyz.fcampbell.programmertodo.auth

import android.content.SharedPreferences
import com.google.gson.Gson

class TokenCache(
        private val sharedPreferences: SharedPreferences,
        private val gson: Gson,
        keySuffix: String
) {
    private val key = "token.${keySuffix.toLowerCase()}"

    fun save(token: Authenticator.Token) {
        val data = gson.toJson(token)
        sharedPreferences.edit().putString(key, data).apply()
    }

    fun get(): Authenticator.Token? {
        return gson.fromJson(sharedPreferences.getString(key, null), Authenticator.Token::class.java)
    }

    fun delete() {
        sharedPreferences.edit().remove(key).apply()
    }
}