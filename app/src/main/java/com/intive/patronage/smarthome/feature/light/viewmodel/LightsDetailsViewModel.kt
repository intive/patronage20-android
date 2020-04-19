package com.intive.patronage.smarthome.feature.light.viewmodel

import android.util.Log
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.MutableLiveData
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.ObservableViewModel
import com.intive.patronage.smarthome.common.convertHSVtoRGB
import com.intive.patronage.smarthome.feature.dashboard.model.Light
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import com.intive.patronage.smarthome.feature.light.view.ColorPickerEventListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LightsDetailsViewModel(
    private var dashboardService: DashboardService,
    var colorPickerEventListener: ColorPickerEventListener,
    private val id: Int
) : ObservableViewModel() {

    var red = 0
    var green = 0
    var blue = 0

    var redForBrightnessSeekBar = 0
    var greenForBrightnessSeekBar = 0
    var blueForBrightnessSeekBar = 0

    private var disposable: Disposable? = null
    val toastMessage = MutableLiveData<Int>()

    init {
        loadLight()
    }

    private fun loadLight() {
        disposable = dashboardService.getLightById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it != null) loadColor(it)
                else Log.d("Exception", "NULL")
            },{
                Log.d("Exception", "ERROR")
            })
    }

    private fun loadColor(light: Light) {
        val rgb = convertHSVtoRGB(light.hue, light.saturation, light.value)

        red = rgb.red
        green = rgb.green
        blue = rgb.blue

        redForBrightnessSeekBar = rgb.red
        greenForBrightnessSeekBar = rgb.green
        blueForBrightnessSeekBar = rgb.blue

        colorPickerEventListener.setBrightnessSeekBarColor(red, green, blue)
        colorPickerEventListener.setCurrentImageViewColor(red, green, blue)
    }

    fun onResetClicked() {
        loadLight()
    }

    fun onApplyClicked() {
        toastMessage.value = R.string.apply_toast
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}