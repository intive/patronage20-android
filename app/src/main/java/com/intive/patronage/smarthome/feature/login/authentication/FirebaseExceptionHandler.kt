package com.intive.patronage.smarthome.feature.login.authentication

import android.content.Context
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.login.viewmodel.LoginViewModel
import com.intive.patronage.smarthome.feature.login.viewmodel.RegisterViewModel

private const val WEAK_PASSWORD = "ERROR_WEAK_PASSWORD"
private const val INVALID_EMAIL = "ERROR_INVALID_EMAIL"

fun showCreateUserException(task: Task<AuthResult>, context: Context, viewModel: RegisterViewModel) {
    try {
        throw task.exception!!
    } catch (exception: FirebaseAuthUserCollisionException) {
        viewModel.newEmailError.value = context.resources.getString(R.string.account_already_exists)
    } catch (exception: FirebaseAuthInvalidCredentialsException) {
        if (exception.errorCode == WEAK_PASSWORD) {
            viewModel.newPasswordError.value = context.resources.getString(R.string.weak_password)
        } else {
            viewModel.newEmailError.value = context.resources.getString(R.string.invalid_email)
        }
    } catch (exception: Exception) {
        showToast(R.string.update_value_toast_error, context)
    }
}

fun showSignInException(task: Task<AuthResult>, context: Context, viewModel: LoginViewModel) {
    try {
        throw task.exception!!
    } catch (exception: FirebaseAuthInvalidUserException) {
        viewModel.emailError.value = context.resources.getString(R.string.account_does_not_exists)
    } catch (exception: FirebaseAuthInvalidCredentialsException) {
        if (exception.errorCode == INVALID_EMAIL) {
            viewModel.emailError.value = context.resources.getString(R.string.invalid_email)
        } else {
            viewModel.passwordError.value = context.resources.getString(R.string.invalid_password)
        }
    } catch (exception: Exception) {
        showToast(R.string.update_value_toast_error, context)
    }
}

private fun showToast(text: Int, context: Context) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}
