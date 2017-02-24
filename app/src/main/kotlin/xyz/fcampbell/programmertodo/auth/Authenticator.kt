package xyz.fcampbell.programmertodo.auth

import android.preference.PreferenceManager
import xyz.fcampbell.programmertodo.ProgrammerTodo

/**
 * Created by francois on 2017-02-24.
 */
abstract class Authenticator {
    protected val cache = TokenCache(
            PreferenceManager.getDefaultSharedPreferences(ProgrammerTodo.application),
            ProgrammerTodo.gson,
            javaClass.simpleName) //todo dagger

    fun logout() = cache.delete()

    data class Token(val token: String, val scopes: List<String>)
}