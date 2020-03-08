package com.intive.patronage.smarthome.dashboard.viewmodel

import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intive.patronage.smarthome.dashboard.model.Light

//TODO: add service to constructor
class LightsDetailsViewModel : ViewModel() {

    // just for now
    var redValue = 78
    var greenValue = 77
    var blueValue = 83
    var currentColor = Color.rgb(redValue, greenValue, blueValue)

    private val light: MutableLiveData<Light> by lazy {
        MutableLiveData<Light>().also {
            loadLight()
        }
    }

    fun getLight(): LiveData<Light> {
        return light
    }

    private fun loadLight() {
        //TODO: fetch data from service
    }

    // buttons on click methods
    fun onResetClicked() {
        //TODO: reset seek bar state to previous
    }

    fun onApplyClicked(): FloatArray {
        //TODO: send data to API
        return convertRGBtoHSV()
    }

    fun onActionBarBackButtonClicked() {
        //TODO: implement navigator
    }

    private fun convertRGBtoHSV(): FloatArray {
        val hsv = FloatArray(3)
        Color.RGBToHSV(redValue, greenValue, blueValue, hsv)
        return hsv
    }

    private fun convertHSVtoRGB(hue: Int, saturation: Int, value: Int) {
        val hsv = floatArrayOf(hue.toFloat(), saturation.toFloat(), value.toFloat())
        val rgb = Color.HSVToColor(hsv)
        redValue = rgb.red
        greenValue = rgb.green
        blueValue = rgb.blue
    }

    fun setCurrentColor(): Int {
        return Color.rgb(redValue, greenValue, blueValue)
    }

    override fun onCleared() {
        super.onCleared()
        //TODO: something when fragment destroyed
    }
}