package com.intive.patronage.smarthome.feature.light.binding

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.intive.patronage.smarthome.feature.light.viewmodel.LightsDetailsViewModel

@BindingAdapter("onLightsLayoutChange")
fun setOnLightsLayoutChangeListener(view: ConstraintLayout, lightsDetailsViewModel: LightsDetailsViewModel) {
    view.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
        with(lightsDetailsViewModel.colorPickerEventListener) {
            this.setBrightnessBarColor(
                lightsDetailsViewModel.redForBrightnessBar,
                lightsDetailsViewModel.greenForBrightnessBar,
                lightsDetailsViewModel.blueForBrightnessBar
            )

            this.setCurrentImageViewColor(
                lightsDetailsViewModel.red,
                lightsDetailsViewModel.green,
                lightsDetailsViewModel.blue
            )

            this.setBrightnessBarPointerPosition(
                lightsDetailsViewModel.brightnessBarPointerX
            )
        }
    }
}