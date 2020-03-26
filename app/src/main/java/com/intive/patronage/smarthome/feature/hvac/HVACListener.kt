package com.intive.patronage.smarthome.feature.hvac

import android.annotation.SuppressLint
import android.text.Editable
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.BindingAdapter

@BindingAdapter("onLayout")
fun setLayout(view: HvacCircle, hvacViewModel: HvacViewModel) {
    view.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
        view.temperatureFloat = hvacViewModel.temperature
        view.hysteresis = hvacViewModel.hysteresis
        view.maxTemperature = hvacViewModel.coolingTemperature
        view.minTemperature = hvacViewModel.heatingTemperature
    }
}

@SuppressLint("ClickableViewAccessibility")
@BindingAdapter("setTemperature")
fun setTemperature(view: HvacCircle, hvacViewModel: HvacViewModel) {
    view.setOnTouchListener { _, motionEvent ->
        when(motionEvent.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                view.onTouch(motionEvent.x, motionEvent.y).apply {
                    hvacViewModel.coolingTemperature = view.maxTemperature
                    hvacViewModel.heatingTemperature = view.minTemperature
                }
            }
            MotionEvent.ACTION_UP ->{
                view.reset()
            }
        }
        true
    }
}
