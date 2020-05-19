package com.intive.patronage.smarthome.feature.login.binding

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.intive.patronage.smarthome.feature.login.authentication.TextWatcherWrapper
import com.intive.patronage.smarthome.feature.login.viewmodel.RegisterViewModel

@BindingAdapter("onNewEmailInput")
fun setOnNewEmailInputListener(view: EditText, viewModel: RegisterViewModel) {
    view.addTextChangedListener(object: TextWatcherWrapper() {
        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            viewModel.apply {
                newEmail = text.toString()
                newEmailError.value = ""
                checkIfInputIsEmpty()
            }
        }
    })
}

@BindingAdapter("onNewPasswordInput")
fun setOnNewPasswordInputListener(view: EditText, viewModel: RegisterViewModel) {
    view.addTextChangedListener(object: TextWatcherWrapper() {
        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            viewModel.apply {
                newPassword = text.toString()
                newPasswordError.value = ""
                checkIfInputIsEmpty()
                checkIfConfirmedPasswordIsTheSame(view.resources)
            }
        }
    })
}

@BindingAdapter("onConfirmPasswordInput")
fun setOnConfirmPasswordInputListener(view: EditText, viewModel: RegisterViewModel) {
    view.addTextChangedListener(object: TextWatcherWrapper() {
        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            viewModel.apply {
                confirmPassword = text.toString()
                checkIfInputIsEmpty()
                checkIfConfirmedPasswordIsTheSame(view.resources)
            }
        }
    })
}