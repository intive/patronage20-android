package com.intive.patronage.smarthome.feature.hvac.binding

import android.annotation.SuppressLint
import android.view.MotionEvent
import androidx.databinding.BindingAdapter
import com.intive.patronage.smarthome.feature.hvac.view.HvacCircle
import com.intive.patronage.smarthome.feature.hvac.viewmodel.HvacViewModel

@BindingAdapter("onLayout")
fun setLayout(view: HvacCircle, hvacViewModel: HvacViewModel) {
    view.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
        view.temperatureFloat = hvacViewModel.temperature
        view.hysteresis = hvacViewModel.hysteresis
        view.coolingTemperature = hvacViewModel.coolingTemperature
        view.heatingTemperature = hvacViewModel.heatingTemperature
    }
}

@SuppressLint("ClickableViewAccessibility")
@BindingAdapter("setTemperature")
fun setTemperature(view: HvacCircle, hvacViewModel: HvacViewModel) {
    view.setOnTouchListener { _, motionEvent ->
        when(motionEvent.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                view.onTouch(motionEvent.x, motionEvent.y).apply {
                    hvacViewModel.coolingTemperature = view.coolingTemperature
                    hvacViewModel.heatingTemperature = view.heatingTemperature
                    hvacViewModel.hysteresis = view.hysteresis
                }
            }
            MotionEvent.ACTION_UP ->{
                view.reset()
            }
        }
        true
    }
}
