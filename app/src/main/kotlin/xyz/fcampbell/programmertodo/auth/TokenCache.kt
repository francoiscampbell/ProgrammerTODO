package xyz.fcampbell.programmertodo.auth

import android.content.SharedPreferences
import com.google.gson.Gson
import xyz.fcampbell.programmertodo.api.Client

class TokenCache(
        private val sharedPreferences: SharedPreferences,
        private val gson: Gson,
        service: Service
) {
    private val key = "token.${service.name}"

    fun save(token: Client.Token) {
        val data = gson.toJson(token)
        sharedPreferences.edit().putString(key, data).apply()
    }

    fun get(): Client.Token? {
        return gson.fromJson(sharedPreferences.getString(key, null), Client.Token::class.java)
    }

    fun delete() {
        sharedPreferences.edit().remove(key).apply()
    }

    enum class Service {
        GITHUB, GITLAB, BITBUCKET
    }
}