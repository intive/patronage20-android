package com.intive.patronage.smarthome.feature.blind.binding

import android.widget.Button
import androidx.databinding.BindingAdapter
import com.intive.patronage.smarthome.feature.blind.viewmodel.BlindDetailsViewModel

@BindingAdapter("onBlindUp")
fun setOnBlindUpListener(view: Button, blindDetailsViewModel: BlindDetailsViewModel) {
    view.setOnClickListener {
        blindDetailsViewModel.blindViewEventListener.blindUp()
        if (blindDetailsViewModel.position > 0) blindDetailsViewModel.position--
        blindDetailsViewModel.setPercent("${blindDetailsViewModel.position} %")
    }
}