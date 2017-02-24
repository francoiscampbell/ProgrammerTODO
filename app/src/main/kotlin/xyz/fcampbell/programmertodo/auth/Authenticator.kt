package xyz.fcampbell.programmertodo.auth

import io.reactivex.Observable

/**
 * Created by francois on 2017-02-24.
 */
abstract class Authenticator {
//    protected abstract val authCache: Cache

    abstract fun login(username: String, password: String, otp: String): Observable<Token>

//    fun logout(): Completable = authCache.deleteCachedToken()

    data class Token(val token: String, val scopes: List<String>)

//    interface Cache {
//        fun getCachedToken() : Observable<Token>
//        fun deleteCachedToken() : Completable
//    }
}