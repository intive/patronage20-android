package com.intive.patronage.smarthome.feature.login.authentication

import android.content.Context
import android.view.View
import android.widget.Toast
import com.intive.patronage.smarthome.R
import kotlinx.android.synthetic.main.activity_register.view.*

fun createAccount(
    view: View,
    context: Context,
    authentication: Authentication
) {
    val email = view.newAccountEmail.text.toString()
    val password = view.newAccountPassword.text.toString()
    val confirm = view.confirmPassword.text.toString()
    if (email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
        Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
    } else {
        val isConfirmed = password == confirm
        if (isConfirmed) {
            authentication.createUserWithEmailAndPassword(email, password)
        } else {
            Toast.makeText(context, R.string.different_password, Toast.LENGTH_SHORT).show()
        }
    }
}