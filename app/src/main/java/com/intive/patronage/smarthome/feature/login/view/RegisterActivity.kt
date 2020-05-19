package com.intive.patronage.smarthome.feature.login.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.login.authentication.AuthenticationService
import com.intive.patronage.smarthome.feature.login.animation.startEnterAnimation
import com.intive.patronage.smarthome.feature.login.animation.startExitAnimation
import com.intive.patronage.smarthome.feature.login.authentication.TextWatcherWrapper
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class RegisterActivity : AppCompatActivity() {
    private val authenticationService: AuthenticationService by inject {
        parametersOf(this)
    }
    private val coordinator: DashboardCoordinator by inject {
        parametersOf(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val view = window.decorView.rootView

        setupInput()
        authenticationService.initAuthFirebase()

        registerInfo.setOnClickListener {
            it.isEnabled = false
            startExitAnimation(this, coordinator, registerCard, registerInfo, false)
        }

        createAccount.setOnClickListener {
            authenticationService.createUserWithEmailAndPassword(getNewEmail(), getNewPassword(), view)
        }
    }

    private fun setupInput() {
        newAccountEmail.addTextChangedListener(object: TextWatcherWrapper() {
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkIfInputIsEmpty(text, getNewPassword(), getConfirmedPassword())
                newAccountEmailLayout.error = null
            }
        })

        newAccountPassword.addTextChangedListener(object: TextWatcherWrapper() {
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkIfInputIsEmpty(text, getNewEmail(), getConfirmedPassword())
                newAccountPasswordLayout.error = null
                checkIfConfirmedPasswordIsTheSame()
            }
        })

        confirmPassword.addTextChangedListener(object: TextWatcherWrapper() {
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkIfInputIsEmpty(text, getNewEmail(), getNewPassword())
                checkIfConfirmedPasswordIsTheSame()
            }
        })
    }

    private fun checkIfInputIsEmpty(text: CharSequence?, secondInput: String, thirdInput: String) {
        if (text != null) {
            if (text.isNotEmpty() && secondInput.isNotEmpty() && thirdInput.isNotEmpty() && (getNewPassword() == getConfirmedPassword())) {
                enableSignUpButton()
            } else {
                disableSignUpButton()
            }
        }
    }

    private fun checkIfConfirmedPasswordIsTheSame() {
        if (getNewPassword() != getConfirmedPassword()) {
            confirmPasswordLayout.error = resources.getString(R.string.different_password)
        } else {
            confirmPasswordLayout.error = null
        }
    }

    private fun getNewEmail() = newAccountEmail.text.toString()

    private fun getNewPassword() = newAccountPassword.text.toString()

    private fun getConfirmedPassword() = confirmPassword.text.toString()

    private fun enableSignUpButton() {
        if (!createAccount.isEnabled) {
            createAccount.isEnabled = true
            createAccount.background.setTint(ContextCompat.getColor(this, R.color.colorAccent))
        }
    }

    private fun disableSignUpButton() {
        if (createAccount.isEnabled) {
            createAccount.isEnabled = false
            createAccount.background.setTint(ContextCompat.getColor(this, R.color.colorAccentLightDark))
        }
    }

    override fun onResume() {
        super.onResume()
        registerInfo.isEnabled = true
        disableSignUpButton()

        overridePendingTransition(NO_ANIMATION, NO_ANIMATION)
        startEnterAnimation(this, registerCard, registerInfo, false)
    }
}
