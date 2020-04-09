package com.intive.patronage.smarthome.feature.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.R
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class LoginActivity : AppCompatActivity() {

    private val googleLogin: GoogleLogin by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        googleLogin.initialAuthFirebase()
        googleLogin.initialGoogleSignIn()

        findViewById<com.google.android.gms.common.SignInButton>(R.id.sign_in_button).setOnClickListener {
            googleLogin.signIn()
        }
    }

    override fun onStart() {
        super.onStart()
        googleLogin.userIsLogged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googleLogin.accountVerification(requestCode, data)
    }
}
