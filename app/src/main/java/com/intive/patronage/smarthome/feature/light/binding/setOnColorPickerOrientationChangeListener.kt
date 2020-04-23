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

        val angleInRadians: Float = (hsv[0] * PI.toFloat()) / 180
        val partOfRadius: Float = hsv[1] * (imageView.height / 2)

        val y = partOfRadius * sin(angleInRadians)
        val x = partOfRadius * sin((PI / 2) - angleInRadians).toFloat()

        lightsDetailsViewModel.colorPickerEventListener.setColorPickerPointerPosition(
            (imageView.width / 2) + x,
            (imageView.height / 2) - y
        )
    }
}