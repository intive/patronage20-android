package com.intive.patronage.smarthome.feature.light.binding

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.intive.patronage.smarthome.feature.light.viewmodel.LightsDetailsViewModel

@SuppressLint("ClickableViewAccessibility")
@BindingAdapter("onBrightnessBarTouch")
fun setOnBrightnessBarTouchListener(view: ImageView, lightsDetailsViewModel: LightsDetailsViewModel) {
    view.setOnTouchListener { _, motionEvent ->
        if (motionEvent.action == MotionEvent.ACTION_DOWN || motionEvent.action == MotionEvent.ACTION_MOVE) {
            lightsDetailsViewModel.colorPickerEventListener.setBrightnessBarColor(
                lightsDetailsViewModel.redForBrightnessBar,
                lightsDetailsViewModel.greenForBrightnessBar,
                lightsDetailsViewModel.blueForBrightnessBar
            )

            val cache = view.getDrawingCache()

            try {
                val pixel = cache.getPixel(motionEvent.x.toInt(), motionEvent.y.toInt())

                val red = Color.red(pixel)
                val green = Color.green(pixel)
                val blue = Color.blue(pixel)

                if (red == 0 && green == 0 && blue == 0) {
                    Log.d("Exception", "Out of touchable area")
                } else {
                    with(lightsDetailsViewModel) {
                        this.colorPickerEventListener.setBrightnessBarPointerPosition(motionEvent.x)
                        this.brightnessBarPointerX = motionEvent.x

                        this.red = red
                        this.green = green
                        this.blue = blue

                        this.colorPickerEventListener.setCurrentImageViewColor(red, green, blue)

                        if (!this.brightnessBarPointerWasTouched) {
                            this.brightnessBarPointerWasTouched = true
                        }
                    }
                }
            } catch (t: Throwable) {
                Log.d("Exception", "Out of touchable area")
            }
        }
        true
    }
}