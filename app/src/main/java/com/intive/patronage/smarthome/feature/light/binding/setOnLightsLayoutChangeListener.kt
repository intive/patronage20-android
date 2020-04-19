package com.intive.patronage.smarthome.feature.light.binding

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.intive.patronage.smarthome.feature.blind.view.BlindView
import com.intive.patronage.smarthome.feature.light.viewmodel.LightsDetailsViewModel

@BindingAdapter("onLightsLayoutChange")
fun setOnLightsLayoutChangeListener(view: ConstraintLayout, lightsDetailsViewModel: LightsDetailsViewModel) {
    view.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
        lightsDetailsViewModel.colorPickerEventListener.setBrightnessSeekBarColor(
            lightsDetailsViewModel.redForBrightnessSeekBar,
            lightsDetailsViewModel.greenForBrightnessSeekBar,
            lightsDetailsViewModel.blueForBrightnessSeekBar
        )

        lightsDetailsViewModel.colorPickerEventListener.setCurrentImageViewColor(
            lightsDetailsViewModel.red,
            lightsDetailsViewModel.green,
            lightsDetailsViewModel.blue
        )
    }
}