package com.intive.patronage.smarthome.feature.dashboard.viewmodel

import android.graphics.Color
import android.util.Log
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.databinding.*
import com.intive.patronage.smarthome.BR
import com.intive.patronage.smarthome.common.ObservableViewModel
import com.intive.patronage.smarthome.common.convertHSVtoRGB
import com.intive.patronage.smarthome.common.convertRGBtoHSV
import com.intive.patronage.smarthome.feature.dashboard.model.Light
import com.intive.patronage.smarthome.feature.dashboard.model.api.service.DashboardService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LightsDetailsViewModel(private var dashboardService: DashboardService, private val id: Int) : ObservableViewModel() {

    private var redProgress: Int = 46
    private var greenProgress: Int = 45
    private var blueProgress: Int = 50
    private var currentColor: Int = Color.rgb(redProgress, greenProgress, blueProgress)
    private var disposable: Disposable? = null

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
        val rgb = convertHSVtoRGB(
            light.hue,
            light.saturation,
            light.value
        )
        setRedProgress(rgb.red)
        setGreenProgress(rgb.green)
        setBlueProgress(rgb.blue)
    }

    @InverseMethod("convertIntToString")
    fun convertStringToInt(value: String) = value.toInt()

    fun convertIntToString(value: Int) = value.toString()

    @Bindable
    fun getCurrentColor() = this.currentColor

    fun setCurrentColor(value: Int) {
        if (this.currentColor != value) this.currentColor = value
        notifyPropertyChanged(BR.currentColor)
    }

    private fun setColor(progress: Int): Int {
        setCurrentColor(Color.rgb(redProgress, greenProgress, blueProgress))
        return progress
    }

    @Bindable
    fun getRedProgress() = setColor(this.redProgress)

    fun setRedProgress(value: Int) {
        if (this.redProgress != value) this.redProgress = value
        notifyPropertyChanged(BR.redProgress)
    }

    @Bindable
    fun getGreenProgress() = setColor(this.greenProgress)

    fun setGreenProgress(value: Int) {
        if (this.greenProgress != value) this.greenProgress = value
        notifyPropertyChanged(BR.greenProgress)
    }

    @Bindable
    fun getBlueProgress() = setColor(this.blueProgress)

    fun setBlueProgress(value: Int) {
        if (this.blueProgress != value) this.blueProgress = value
        notifyPropertyChanged(BR.blueProgress)
    }

    fun onResetClicked() {
        loadLight()
    }

    fun onApplyClicked() {
        val hsv = convertRGBtoHSV(
            redProgress,
            greenProgress,
            blueProgress
        )
        //TODO: send data to API
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}