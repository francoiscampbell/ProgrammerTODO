package xyz.fcampbell.programmertodo.auth

import io.reactivex.Observable
import okhttp3.Credentials
import retrofit2.HttpException
import xyz.fcampbell.programmertodo.api.github.GitHubApi
import xyz.fcampbell.programmertodo.api.github.model.TokenRequest
import xyz.fcampbell.programmertodo.auth.exception.OtpRequiredException

class GitHubAuthenticator(
        private val gitHubApi: GitHubApi
) : Authenticator() {
    fun login(username: String, password: String, tokenRequest: TokenRequest, otp: String): Observable<Authenticator.Token> {
        val cachedToken = cache.get()
        if (cachedToken != null) {
            return Observable.just(cachedToken)
        }

        val tokenResponse = if (otp.isEmpty()) {
            gitHubApi.getToken(Credentials.basic(username, password), tokenRequest)
        } else {
            gitHubApi.getToken(Credentials.basic(username, password), otp, tokenRequest)
        }
        return tokenResponse
                .map { Authenticator.Token(it.token, it.scopes) }
                .doOnNext { cache.save(it) }
                .onErrorResumeNext { error: Throwable ->
                    if (error is HttpException
                            && error.response().code() == 401
                            && error.response().headers()["X-GitHub-OTP"] != null) {
                        return@onErrorResumeNext Observable.error(OtpRequiredException())
                    }
                    return@onErrorResumeNext Observable.error(error)
                }
    }
}