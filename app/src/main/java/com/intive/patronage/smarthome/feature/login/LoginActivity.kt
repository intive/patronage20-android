package com.intive.patronage.smarthome.feature.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.login.animation.startLoginActivityEnterAnimation
import com.intive.patronage.smarthome.feature.login.animation.startLoginActivityExitAnimation
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

const val NO_ANIMATION = 0

class LoginActivity : AppCompatActivity() {
    private val authentication: Authentication by inject {
        parametersOf(this)
    }
    private val coordinator: DashboardCoordinator by inject {
        parametersOf(this)
    }

    private var isTheFirstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authentication.initAuthFirebase()
        authentication.initGoogleSignIn()

        signInWithGoogle.setOnClickListener {
            authentication.signInWithGoogle()
        }

        signIn.setOnClickListener {
            signIn()
        }

        loginInfo.setOnClickListener {
            it.isEnabled = false
            startLoginActivityExitAnimation(this, coordinator, loginCard, loginInfo)
        }
    }

    private fun signIn() {
        val email = email.text.toString()
        val password = password.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
        } else {
            authentication.signInWithEmailAndPassword(email, password)
        }
    }

    override fun onResume() {
        super.onResume()
        setupView()

        if (isTheFirstTime) {
             isTheFirstTime = false
        } else {
            overridePendingTransition(NO_ANIMATION, NO_ANIMATION)
            startLoginActivityEnterAnimation(this, loginCard, loginInfo)
        }
    }

    private fun setupView() {
        loginInfo.isEnabled = true
        email.text.clear()
        password.text.clear()
    }

    override fun onStart() {
        super.onStart()
        authentication.checkIfUserIsLogged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        authentication.accountVerification(requestCode, data)
    }
}
