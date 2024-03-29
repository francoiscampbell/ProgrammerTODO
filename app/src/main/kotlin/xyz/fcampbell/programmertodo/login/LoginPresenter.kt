package xyz.fcampbell.programmertodo.login

import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import xyz.fcampbell.programmertodo.ProgrammerTodo
import xyz.fcampbell.programmertodo.api.github.GitHubApi
import xyz.fcampbell.programmertodo.api.github.GitHubClient
import xyz.fcampbell.programmertodo.api.github.model.TokenRequest
import xyz.fcampbell.programmertodo.auth.exception.OtpRequiredException

/**
 * Created by francois on 2017-02-24.
 */
class LoginPresenter {
    lateinit var view: View

    private val gson = ProgrammerTodo.gson
    private val gitHubApi by lazy {
        Retrofit.Builder()
                .baseUrl(GitHubApi.URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(GitHubApi::class.java)
    }
    private val gitHubClient by lazy { GitHubClient(gitHubApi) }//todo dagger

    fun attach(view: View) {
        if (gitHubClient.isLoggedIn) view.onLoginSuccess()

        this.view = view
    }

    fun login(username: String, password: String, tokenRequest: TokenRequest, otp: String = "") {
        view.onLoginStart()
        gitHubClient.login(username, password, otp, tokenRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.onLoginSuccess()
                }, {
                    if (it is OtpRequiredException) {
                        view.showOtpField()
                    } else {
                        view.onLoginError(it)
                    }
                })
    }

    interface View {
        fun onLoginStart()
        fun showOtpField()
        fun onLoginError(throwable: Throwable)
        fun onLoginSuccess()
    }
}