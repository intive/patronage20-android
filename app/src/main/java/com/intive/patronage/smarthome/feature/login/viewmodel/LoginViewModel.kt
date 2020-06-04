package com.intive.patronage.smarthome.feature.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.intive.patronage.smarthome.common.ObservableViewModel
import com.intive.patronage.smarthome.feature.login.authentication.AuthenticationService
import com.intive.patronage.smarthome.feature.login.view.LoginEventListener

class LoginViewModel(
    private val authenticationService: AuthenticationService,
    var loginEventListener: LoginEventListener
) : ObservableViewModel() {

    var email: String = ""
    var password: String = ""
    var emailError = MutableLiveData("")
    var passwordError = MutableLiveData("")
    var isSignInEnabled = MutableLiveData(false)

    fun signInWithGoogle() {
        authenticationService.signInWithGoogle()
    }

    fun signIn() {
        authenticationService.signInWithEmailAndPassword(email, password, this)
    }

    fun signUp() {
        loginEventListener.startAnimation()
    }

    fun checkIfInputIsEmpty() {
        isSignInEnabled.value =
            email.isNotEmpty() &&
            password.isNotEmpty() &&
            emailError.value.isNullOrEmpty() &&
            passwordError.value.isNullOrEmpty()
    }
}