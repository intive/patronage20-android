package com.intive.patronage.smarthome.feature.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.R
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class LoginActivity : AppCompatActivity() {

    private val loginGoogle: LoginGoogle by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginGoogle.initialAuthFirebase()
        loginGoogle.initialGoogleSignIn()

        findViewById<com.google.android.gms.common.SignInButton>(R.id.sign_in_button).setOnClickListener {
            loginGoogle.signIn()
        }
    }

    override fun onStart() {
        super.onStart()
        loginGoogle.userIsLogged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginGoogle.accountVerification(requestCode, data)
    }
}
