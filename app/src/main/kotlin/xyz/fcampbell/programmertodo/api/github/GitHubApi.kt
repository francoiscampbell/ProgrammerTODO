package xyz.fcampbell.programmertodo.api.github

import io.reactivex.Observable
import retrofit2.http.*
import xyz.fcampbell.programmertodo.api.github.model.Repo
import xyz.fcampbell.programmertodo.api.github.model.TokenRequest
import xyz.fcampbell.programmertodo.api.github.model.TokenResponse

/**
 * Created by francois on 2017-02-24.
 */
interface GitHubApi {
    companion object {
        val URL = "https://api.github.com"
    }

    @POST("/authorizations")
    fun getToken(@Header("Authorization") basicCredentials: String,
                 @Header("X-GitHub-OTP") otp: String?,
                 @Body tokenRequest: TokenRequest): Observable<TokenResponse>

    @GET("/user/repos")
    fun getMyRepos(): Observable<List<Repo>>

    @GET("/users/{username}/repos")
    fun getUserRepos(@Path("username") username: String): Observable<List<Repo>>
}