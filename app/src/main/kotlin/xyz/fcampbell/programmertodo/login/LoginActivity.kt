package xyz.fcampbell.programmertodo.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import xyz.fcampbell.programmertodo.R
import xyz.fcampbell.programmertodo.api.github.model.TokenRequest

class LoginActivity : AppCompatActivity(), LoginPresenter.View {
    companion object {
        const val TAG = "LoginActivity"
    }

    private val presenter = LoginPresenter().apply { attach(this@LoginActivity) } //todo dagger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sign_in_button.setOnClickListener {
            doLogin()
        }
    }

    private fun doLogin() {
        val tokenRequest = TokenRequest(listOf("repo"), "Test Android app 2")
        presenter.login(
                username.text.toString(),
                password.text.toString(),
                tokenRequest,
                otp.text.toString())
    }

    override fun onLoginStart() {
    }

    override fun showOtpField() {
        Toast.makeText(this, getString(R.string.otp_required), Toast.LENGTH_SHORT).show()
        otp.scaleY = 0f
        otp.visibility = View.VISIBLE
        otp.animate().scaleY(1f).duration = 300
    }

    override fun onLoginSuccess() {
        Log.d(LoginActivity.TAG, "onLoginSuccess")
    }

    override fun onLoginError(throwable: Throwable) {
        Log.d(TAG, "onLoginError: $throwable")
    }
}
