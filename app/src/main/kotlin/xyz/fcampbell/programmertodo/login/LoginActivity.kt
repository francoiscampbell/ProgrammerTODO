package xyz.fcampbell.programmertodo.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import xyz.fcampbell.programmertodo.R
import xyz.fcampbell.programmertodo.api.github.GitHubApi
import xyz.fcampbell.programmertodo.api.github.model.TokenRequest
import xyz.fcampbell.programmertodo.auth.GitHubAuthenticator

class LoginActivity : AppCompatActivity() {
    companion object {
        const val TAG = "LoginActivity"
    }

    private val gitHubApi = Retrofit.Builder()
            .baseUrl(GitHubApi.URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApi::class.java)
    private val gitHubAuthenticator = GitHubAuthenticator(gitHubApi) //todo dagger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tokenRequest = TokenRequest(listOf("repo"), "Test Android app")
        email_sign_in_button.setOnClickListener {
            gitHubAuthenticator.login(
                    username.text.toString(),
                    password.text.toString(),
                    otp.text.toString(),
                    tokenRequest
            ).subscribe({
                Log.d(TAG, "onNext: $it")
            }, {
                Log.d(TAG, "onError: ${(it as HttpException).response().errorBody().string()}")
            }, {
                Log.d(TAG, "onComplete")
            })
        }
    }
}
