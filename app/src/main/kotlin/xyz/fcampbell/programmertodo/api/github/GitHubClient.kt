package xyz.fcampbell.programmertodo.api.github

import io.reactivex.Observable
import okhttp3.Credentials
import retrofit2.HttpException
import xyz.fcampbell.programmertodo.api.github.model.Repo
import xyz.fcampbell.programmertodo.api.github.model.TokenRequest
import xyz.fcampbell.programmertodo.auth.TokenCache
import xyz.fcampbell.programmertodo.auth.exception.OtpRequiredException

/**
 * Created by francois on 2017-02-24.
 */
class GitHubClient(
        private val gitHubApi: GitHubApi,
        private val tokenCache: TokenCache
) {
    data class Token(val token: String, val scopes: List<String>)

    val isLoggedIn = tokenCache.get() != null

    fun login(username: String, password: String, otp: String?, tokenRequest: TokenRequest): Observable<Token> {
        return gitHubApi.getToken(Credentials.basic(username, password), otp, tokenRequest)
                .onErrorResumeNext { error: Throwable ->
                    if (error is HttpException
                            && error.response().code() == 401
                            && error.response().headers()["X-GitHub-OTP"] != null) {
                        return@onErrorResumeNext Observable.error(OtpRequiredException())
                    }
                    return@onErrorResumeNext Observable.error(error)
                }
                .map { Token(it.token, it.scopes) }
                .doOnNext { tokenCache.save(it) }
    }

    fun logout() = tokenCache.delete()

    fun getMyRepos(): Observable<List<Repo>> {
        return gitHubApi.getMyRepos()
    }

    fun getUserRepos(username: String): Observable<List<Repo>> {
        return gitHubApi.getUserRepos(username)
    }
}