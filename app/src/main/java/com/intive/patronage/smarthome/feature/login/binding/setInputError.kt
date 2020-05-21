package com.intive.patronage.smarthome.feature.login.binding

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("inputError")
fun setInputError(view: TextInputLayout, errorMessage: String) {
    view.error = if (errorMessage.isEmpty()) null else errorMessage
}