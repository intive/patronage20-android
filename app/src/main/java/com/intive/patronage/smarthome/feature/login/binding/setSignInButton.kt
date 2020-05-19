package com.intive.patronage.smarthome.feature.login.binding

import android.widget.Button
import androidx.databinding.BindingAdapter
import com.intive.patronage.smarthome.R

@BindingAdapter("signInEnabled")
fun setSignInButton(view: Button, isEnabled: Boolean) {
    if (isEnabled) {
        view.isEnabled = true
        view.background.setTint(view.resources.getColor(R.color.colorAccent, null))
    } else {
        view.isEnabled = false
        view.background.setTint(view.resources.getColor(R.color.colorAccentLightDark, null))
    }
}