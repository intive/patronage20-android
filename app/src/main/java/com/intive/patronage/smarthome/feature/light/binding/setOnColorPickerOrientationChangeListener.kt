package com.intive.patronage.smarthome.feature.light.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.intive.patronage.smarthome.common.convertRGBtoHSV
import com.intive.patronage.smarthome.feature.light.viewmodel.LightsDetailsViewModel
import kotlin.math.PI
import kotlin.math.sin

@BindingAdapter("onColorPickerOrientationChange")
fun setOnColorPickerOrientationChangeListener(imageView: ImageView, lightsDetailsViewModel: LightsDetailsViewModel) {
    imageView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
        val hsv = convertRGBtoHSV(
            lightsDetailsViewModel.red,
            lightsDetailsViewModel.green,
            lightsDetailsViewModel.blue
        )

        val radius = if (imageView.width < imageView.height) {
            imageView.width / 2 - lightsDetailsViewModel.halfOfPointerWidth
        } else {
            imageView.height / 2 - lightsDetailsViewModel.halfOfPointerWidth
        }

        val angleInRadians: Float = (hsv[0] * PI.toFloat()) / 180
        val partOfRadius: Float = hsv[1] * radius

        val y = partOfRadius * sin(angleInRadians)
        val x = partOfRadius * sin((PI / 2) - angleInRadians).toFloat()

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

            this.setColorPickerPointerPosition(
                (imageView.width / 2) + x,
                (imageView.height / 2) - y
            )
        }
    }
}