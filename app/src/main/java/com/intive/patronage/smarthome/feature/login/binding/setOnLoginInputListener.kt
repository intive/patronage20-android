package com.intive.patronage.smarthome.feature.login.binding

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.intive.patronage.smarthome.feature.login.authentication.TextWatcherWrapper
import com.intive.patronage.smarthome.feature.login.viewmodel.LoginViewModel

@BindingAdapter("onEmailInput")
fun setOnEmailInputListener(view: EditText, viewModel: LoginViewModel) {
    view.addTextChangedListener(object: TextWatcherWrapper() {
        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            viewModel.apply {
                email = text.toString()
                emailError.value = ""
                checkIfInputIsEmpty()
            }
        }
    })
}

@BindingAdapter("onPasswordInput")
fun setOnPasswordInputListener(view: EditText, viewModel: LoginViewModel) {
    view.addTextChangedListener(object: TextWatcherWrapper() {
        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            viewModel.apply {
                password = text.toString()
                passwordError.value = ""
                checkIfInputIsEmpty()
            }
        }
    })
}