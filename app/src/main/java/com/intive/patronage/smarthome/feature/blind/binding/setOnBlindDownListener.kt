package com.intive.patronage.smarthome.feature.blind.binding

import android.widget.Button
import androidx.databinding.BindingAdapter
import com.intive.patronage.smarthome.feature.blind.viewmodel.BlindDetailsViewModel

@BindingAdapter("onBlindDown")
fun setOnBlindDownListener(view: Button, blindDetailsViewModel: BlindDetailsViewModel) {
    view.setOnClickListener {
        blindDetailsViewModel.blindViewEventListener.blindDown()
        if (blindDetailsViewModel.position < 100) blindDetailsViewModel.position++
        blindDetailsViewModel.setPercent("${100-blindDetailsViewModel.position} %")
    }
}