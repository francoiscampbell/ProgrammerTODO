package xyz.fcampbell.programmertodo.auth

import io.reactivex.Observable
import okhttp3.Credentials
import xyz.fcampbell.programmertodo.api.github.GitHubApi
import xyz.fcampbell.programmertodo.api.github.model.TokenRequest

class GitHubAuthenticator(
        private val gitHubApi: GitHubApi
) {
    fun login(username: String, password: String, otp: String, tokenRequest: TokenRequest): Observable<Authenticator.Token> {
        val tokenResponse = if (otp.isEmpty()) {
            gitHubApi.getToken(Credentials.basic(username, password), tokenRequest)
        } else {
            gitHubApi.getToken(Credentials.basic(username, password), otp, tokenRequest)
        }
        return tokenResponse.map { Authenticator.Token(it.token, it.scopes) }
    }
}