package com.intive.patronage.smarthome.feature.light.viewmodel

import android.util.Log
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.MutableLiveData
import com.intive.patronage.smarthome.common.ObservableViewModel
import com.intive.patronage.smarthome.common.convertHSVtoRGB
import com.intive.patronage.smarthome.common.convertRGBtoHSV
import com.intive.patronage.smarthome.feature.dashboard.model.Light
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import com.intive.patronage.smarthome.feature.light.model.api.LightDTO
import com.intive.patronage.smarthome.feature.light.view.ColorPickerEventListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okio.EOFException
import retrofit2.HttpException

class LightsDetailsViewModel(
    private var dashboardService: DashboardService,
    var colorPickerEventListener: ColorPickerEventListener,
    private val id: Int
) : ObservableViewModel() {

    private val type: String = "RGBLight"

    var red = 0
    var green = 0
    var blue = 0

    var redForBrightnessBar = 0
    var greenForBrightnessBar = 0
    var blueForBrightnessBar = 0

    var brightnessBarPointerEndX = 0f
    var brightnessBarPointerX = 0f

    private var disposable: Disposable? = null
    private var lightChangerDisposable: Disposable? = null
    val toastMessage = MutableLiveData<Int>()

    val hsv = MutableLiveData<IntArray>()

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
            }, {
                Log.d("Exception", "ERROR")
            })
    }

    private fun loadColor(light: Light) {
        hsv.value = intArrayOf(light.hue, light.saturation, light.value)
        val saturation = if (light.saturation == 100) 99 else light.saturation

        val rgb = convertHSVtoRGB(light.hue, saturation, light.value)
        red = rgb.red
        green = rgb.green
        blue = rgb.blue
        colorPickerEventListener.setCurrentImageViewColor(red, green, blue)

        val rgbForBrightnessBar = convertHSVtoRGB(light.hue, saturation, 100)
        redForBrightnessBar = rgbForBrightnessBar.red
        greenForBrightnessBar = rgbForBrightnessBar.green
        blueForBrightnessBar = rgbForBrightnessBar.blue
        colorPickerEventListener.setBrightnessBarColor(redForBrightnessBar, greenForBrightnessBar, blueForBrightnessBar)
    }

    fun onResetClicked() {
        loadLight()
        colorPickerEventListener.resetPointersPosition()
    }

    fun onApplyClicked() {
        val lightChangeHSV = convertRGBtoHSV(red, green, blue)
        lightChangerDisposable = dashboardService.changeLightColor(
            LightDTO(id, type, lightChangeHSV[0].toInt(), lightChangeHSV[1].toInt(), lightChangeHSV[2].toInt())
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {
                when (it) {
                    is EOFException -> Log.d("testowanie", "c4")
                    is HttpException -> Log.d("testowanie", "c5")
                    else -> Log.d("testowanie", it.message.toString())
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
        lightChangerDisposable?.dispose()
    }
}