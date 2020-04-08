package com.intive.patronage.smarthome.feature.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
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
        if (requestCode == googleLogin.RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                googleLogin.firebaseAuthWithGoogle(account!!)
                Toast.makeText(this, "Google sign in is successfully", Toast.LENGTH_SHORT).show()
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show()
                Log.w(getString(R.string.login), "Google sign in failed", e)
            }
        }
    }
}
