package com.intive.patronage.smarthome.feature.light.binding

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.intive.patronage.smarthome.feature.light.viewmodel.LightsDetailsViewModel

@SuppressLint("ClickableViewAccessibility")
@BindingAdapter("onColorPickerTouch")
fun setOnColorPickerTouchListener(view: ImageView, lightsDetailsViewModel: LightsDetailsViewModel) {
    view.setOnTouchListener { _, motionEvent ->
        if (motionEvent.action == MotionEvent.ACTION_DOWN || motionEvent.action == MotionEvent.ACTION_MOVE) {
            val cache = view.getDrawingCache()

            try {
                val pixel = cache.getPixel(motionEvent.x.toInt(), motionEvent.y.toInt())

                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = Color.blue(pixel)

                if (red != 0 && green != 0 && blue != 0) {
                    lightsDetailsViewModel.red = red
                    lightsDetailsViewModel.green = green
                    lightsDetailsViewModel.blue = blue

                    lightsDetailsViewModel.redForBrightnessSeekBar = red
                    lightsDetailsViewModel.greenForBrightnessSeekBar = green
                    lightsDetailsViewModel.blueForBrightnessSeekBar = blue

                    lightsDetailsViewModel.colorPickerEventListener.setBrightnessSeekBarColor(
                        red,
                        green,
                        blue
                    )
                    lightsDetailsViewModel.colorPickerEventListener.setCurrentImageViewColor(
                        red,
                        green,
                        blue
                    )
                }
            } catch (t: Throwable) {
                Log.d("Exception", "Out of touchable area")
            }
        }
        true
    }
}