package com.intive.patronage.smarthome.feature.login.viewmodel

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.ObservableViewModel
import com.intive.patronage.smarthome.feature.login.authentication.AuthenticationService
import com.intive.patronage.smarthome.feature.login.view.RegisterEventListener

class RegisterViewModel(
    private val authenticationService: AuthenticationService,
    var registerEventListener: RegisterEventListener
) : ObservableViewModel() {

    var newEmail: String = ""
    var newPassword: String = ""
    var confirmPassword: String = ""
    var newEmailError = MutableLiveData("")
    var newPasswordError = MutableLiveData("")
    var confirmPasswordError = MutableLiveData("")
    var isCreateAccountEnabled = MutableLiveData(false)

    fun signIn() {
        registerEventListener.startAnimation()
    }

    fun createAccount() {
        authenticationService.createUserWithEmailAndPassword(newEmail, newPassword, this)
    }

    fun checkIfInputIsEmpty() {
        isCreateAccountEnabled.value =
            newEmail.isNotEmpty() &&
            newPassword.isNotEmpty() &&
            confirmPassword.isNotEmpty() &&
            (newPassword == confirmPassword) &&
            newEmailError.value.isNullOrEmpty() &&
            newPasswordError.value.isNullOrEmpty()
    }

    fun checkIfConfirmedPasswordIsTheSame(resources: Resources) {
        if (newPassword != confirmPassword) {
            confirmPasswordError.value = resources.getString(R.string.different_password)
        } else {
            confirmPasswordError.value = ""
        }
    }
}