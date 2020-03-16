package com.intive.patronage.smarthome.dashboard.viewmodel

import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.databinding.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.intive.patronage.smarthome.BR
import com.intive.patronage.smarthome.dashboard.logic.ObservableViewModel
import com.intive.patronage.smarthome.dashboard.logic.convertHSVtoRGB
import com.intive.patronage.smarthome.dashboard.logic.convertRGBtoHSV
import com.intive.patronage.smarthome.dashboard.model.Light

//TODO: add service to constructor
class LightsDetailsViewModel : ObservableViewModel() {

    private var redProgress: Int = 0
    private var greenProgress: Int = 0
    private var blueProgress: Int = 0
    private var currentColor: Int = Color.rgb(redProgress, greenProgress, blueProgress)

    private val light: MutableLiveData<Light> by lazy {
        MutableLiveData<Light>().also {
            loadLight()
        }
    }

    fun getLight(): LiveData<Light> = light

    private fun loadLight() {
        //TODO: fetch data from service
        val rgb = convertHSVtoRGB(0, 0, 0)
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
        val hsv = convertRGBtoHSV(redProgress, greenProgress, blueProgress)
        //TODO: send data to API
    }
}