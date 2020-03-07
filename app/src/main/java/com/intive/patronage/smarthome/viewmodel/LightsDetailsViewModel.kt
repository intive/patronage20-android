package com.intive.patronage.smarthome.viewmodel

import android.graphics.Color
import android.util.Log
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//TODO: add service to constructor
class LightsDetailsViewModel : ViewModel() {

    // just for now
    data class LightsData(val id: Int, val type: String, val hue: Int, val saturation: Int, val value: Int)
    var redValue = 4
    var greenValue = 138
    var blueValue = 191

    private val lights: MutableLiveData<LightsData> by lazy {
        MutableLiveData<LightsData>().also {
            loadLights()
        }
    }

    fun getLights(): LiveData<LightsData> {
        return lights
    }

    private fun loadLights() {
        //TODO: fetch data from service
    }

    // buttons on click methods
    fun onCancelClicked() {
        //TODO: reset seek bar state to previous
    }

    fun onOkClicked() {
        convertRGBtoHSV()
        //TODO: send data to API
    }

    fun onAppbarBackButtonClicked() {
        //TODO: implement navigator
    }

    fun convertRGBtoHSV() {
        val hsv = FloatArray(3)
        Color.RGBToHSV(redValue, greenValue, blueValue, hsv)
        Log.d("HUE", hsv[0].toString())
        Log.d("SATURATION", hsv[1].toString())
        Log.d("VALUE", hsv[2].toString())
    }

    fun convertHSVtoRGB(hue: Int, saturation: Int, value: Int) {
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