package com.intive.patronage.smarthome.dashboard.viewmodel

import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.databinding.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.BR
import com.intive.patronage.smarthome.dashboard.model.Light

//TODO: add service to constructor
class LightsDetailsViewModel : ViewModel(), Observable {

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()
    // this values will be fetched from service
    private var redProgress = 78
    private var greenProgress = 77
    private var blueProgress = 83
    private var currentColor: Int = Color.rgb(redProgress, greenProgress, blueProgress)

    private val light: MutableLiveData<Light> by lazy {
        MutableLiveData<Light>().also {
            loadLight()
        }
    }

    fun getLight(): LiveData<Light> = light

    private fun loadLight() {
        //TODO: fetch data from service
        //convertHSVtoRGB(hue, saturation, value)
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
        val hsv = convertRGBtoHSV()
        //TODO: send data to API
    }

    private fun convertRGBtoHSV(): FloatArray {
        val hsv = FloatArray(3)
        Color.RGBToHSV(redProgress, greenProgress, blueProgress, hsv)
        return hsv
    }

    private fun convertHSVtoRGB(hue: Int, saturation: Int, value: Int) {
        val hsv = floatArrayOf(hue.toFloat(), saturation.toFloat(), value.toFloat())
        val rgb = Color.HSVToColor(hsv)
        redProgress = rgb.red
        greenProgress = rgb.green
        blueProgress = rgb.blue
    }

    override fun addOnPropertyChangedCallback(
        callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(
        callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }

    private fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }
}