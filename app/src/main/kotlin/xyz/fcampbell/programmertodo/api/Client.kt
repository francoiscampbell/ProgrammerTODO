package xyz.fcampbell.programmertodo.api

import android.preference.PreferenceManager
import xyz.fcampbell.programmertodo.ProgrammerTodo
import xyz.fcampbell.programmertodo.auth.TokenCache

/**
 * Created by francois on 2017-02-24.
 */
abstract class Client(
        service: TokenCache.Service
) {
    protected val cache = TokenCache(
            PreferenceManager.getDefaultSharedPreferences(ProgrammerTodo.application),
            ProgrammerTodo.gson,
            service) //todo dagger

    fun logout() = cache.delete()

    data class Token(val token: String, val scopes: List<String>)
}