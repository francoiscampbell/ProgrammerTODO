package xyz.fcampbell.programmertodo.api.github

import io.reactivex.Observable
import okhttp3.Credentials
import retrofit2.HttpException
import xyz.fcampbell.programmertodo.api.Client
import xyz.fcampbell.programmertodo.api.github.model.Repo
import xyz.fcampbell.programmertodo.api.github.model.TokenRequest
import xyz.fcampbell.programmertodo.auth.TokenCache
import xyz.fcampbell.programmertodo.auth.exception.OtpRequiredException

/**
 * Created by francois on 2017-02-24.
 */
class GitHubClient(
        private val gitHubApi: GitHubApi
) : Client(TokenCache.Service.GITHUB) {
    val isLoggedIn = cache.get() != null

    fun login(username: String, password: String, otp: String?, tokenRequest: TokenRequest): Observable<Client.Token> {
        val cachedToken = cache.get()
        if (cachedToken != null) {
            return Observable.just(cachedToken)
        }

        return gitHubApi.getToken(Credentials.basic(username, password), otp, tokenRequest)
                .map { Client.Token(it.token, it.scopes) }
                .onErrorResumeNext { error: Throwable ->
                    if (error is HttpException
                            && error.response().code() == 401
                            && error.response().headers()["X-GitHub-OTP"] != null) {
                        return@onErrorResumeNext Observable.error(OtpRequiredException())
                    }
                    return@onErrorResumeNext Observable.error(error)
                }.doOnNext { cache.save(it) }
    }

    fun getMyRepos(): Observable<List<Repo>> {
        return gitHubApi.getMyRepos(cache.get()?.token)
    }

    fun getUserRepos(username: String): Observable<List<Repo>> {
        return gitHubApi.getUserRepos(username)
    }
}