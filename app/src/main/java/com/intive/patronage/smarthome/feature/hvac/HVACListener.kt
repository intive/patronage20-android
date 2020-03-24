package com.intive.patronage.smarthome.feature.hvac

import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.BindingAdapter

@BindingAdapter("onLayout")
fun setLayout(view: HvacCircle, hvacViewModel: HvacViewModel) {
    view.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
        view.temperatureFloat = hvacViewModel.temperature
        view.hysteresis = hvacViewModel.hysteresis
    }
}

@BindingAdapter("tempUp")
fun setOnTemperatureUp(view: Button, hvacViewModel: HvacViewModel) {
    view.setOnClickListener {
        if (hvacViewModel.temperatureFromView < 51 && hvacViewModel.temperatureFromView > -11) {
            hvacViewModel.temperature = hvacViewModel.temperatureFromView
        } else Toast.makeText(view.context, "za wysoka temperatura", Toast.LENGTH_SHORT).show()
        hvacViewModel.hvacViewEventListener.setTemperature(hvacViewModel.temperature)

    }
}

@BindingAdapter("textChangeListener")
fun textChangeListener(view: EditText, hvacViewModel: HvacViewModel) {

    view.addTextChangedListener(object : MyTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            if (!s.isNullOrBlank()) {
                val temperatureFromView = s.toString().toFloat()
                hvacViewModel.temperatureFromView = temperatureFromView
            }
        }
    })
}
