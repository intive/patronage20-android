package com.intive.patronage.smarthome.feature.light.viewmodel

import android.util.Log
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.MutableLiveData
import com.intive.patronage.smarthome.R
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
const val type = "LED_CONTROLLER"

class LightsDetailsViewModel(
    private var dashboardService: DashboardService,
    var colorPickerEventListener: ColorPickerEventListener,
    private val id: Int
) : ObservableViewModel() {

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
                it?.let { sensor -> loadColor(sensor) }
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
            .subscribe(
                {checkResponse(it.code())},
                { toastMessage.value = R.string.api_connection_error }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
        lightChangerDisposable?.dispose()
    }

    private fun checkResponse(code :Int){
        when(code){
            200 -> toastMessage.value = R.string.apply_toast
            400 -> {toastMessage.value = R.string.http_400
                 loadLight()}
            404 -> { toastMessage.value = R.string.http_404
                loadLight()}
        }
    }
}