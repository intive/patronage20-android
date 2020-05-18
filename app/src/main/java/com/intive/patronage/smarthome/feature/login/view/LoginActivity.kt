package com.intive.patronage.smarthome.feature.login.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.login.authentication.Authentication
import com.intive.patronage.smarthome.feature.login.animation.startEnterAnimation
import com.intive.patronage.smarthome.feature.login.animation.startExitAnimation
import com.intive.patronage.smarthome.feature.login.authentication.TextWatcherWrapper
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

    private var isTheFirstTime: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val view = window.decorView.rootView

        isTheFirstTime = savedInstanceState != null

        setupInput()
        authentication.initAuthFirebase()
        authentication.initGoogleSignIn()

        signInWithGoogle.setOnClickListener {
            authentication.signInWithGoogle()
        }

        signIn.setOnClickListener {
            authentication.signInWithEmailAndPassword(getEmail(), getPassword(), view)
        }

        loginInfo.setOnClickListener {
            it.isEnabled = false
            startExitAnimation(this, coordinator, loginCard, loginInfo, true)
        }
    }

    private fun setupInput() {
        email.addTextChangedListener(object: TextWatcherWrapper() {
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkIfInputIsEmpty(text, getPassword())
                emailLayout.error = null
            }
        })

        password.addTextChangedListener(object: TextWatcherWrapper() {
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkIfInputIsEmpty(text, getEmail())
                passwordLayout.error = null
            }
        })
    }

    private fun checkIfInputIsEmpty(text: CharSequence?, anotherInput: String) {
        if (text != null) {
            if (text.isNotEmpty() && anotherInput.isNotEmpty()) {
                enableSignInButton()
            } else {
                disableSignInButton()
            }
        }
    }

    private fun getEmail() = email.text.toString()

    private fun getPassword() = password.text.toString()

    private fun enableSignInButton() {
        if (!signIn.isEnabled) {
            signIn.isEnabled = true
            signIn.background.setTint(ContextCompat.getColor(this, R.color.colorAccent))
        }
    }

    private fun disableSignInButton() {
        if (signIn.isEnabled) {
            signIn.isEnabled = false
            signIn.background.setTint(ContextCompat.getColor(this, R.color.colorAccentLightDark))
        }
    }

    override fun onResume() {
        super.onResume()
        setupView()

        if (isTheFirstTime) {
             isTheFirstTime = false
        } else {
            overridePendingTransition(NO_ANIMATION, NO_ANIMATION)
            startEnterAnimation(this, loginCard, loginInfo, true)
        }
    }

    private fun setupView() {
        loginInfo.isEnabled = true
        disableSignInButton()

        email.text.clear()
        emailLayout.error = null

        password.text.clear()
        passwordLayout.error = null
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
