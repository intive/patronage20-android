package com.intive.patronage.smarthome.viewmodel

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//TODO: add service to constructor
public class LightsDetailsViewModel() : ViewModel() {
    var redValue = 98
    var greenValue = 0
    var blueValue = 238

    // just for now
    data class LightsData(val id: Int, val type: String, val hue: Int, val saturation: Int, val value: Int)

    private val lights: MutableLiveData<LightsData> by lazy {
        MutableLiveData<LightsData>().also {
            loadLights()
        }
    }

    fun getLights(): LiveData<LightsData> {
        return lights
    }

    private fun loadLights() {
        //TODO: fetch data
    }

    // button on click methods
    fun onCancelClicked() {
        //TODO: reset seek bar state to previous
    }

    fun onOkClicked() {
        /*TODO:
            - change view color on seek bar swipe
            - send data to API
         */
        redValue = 100
        greenValue = 100
        blueValue = 100
        var color = Color.rgb(redValue, greenValue, blueValue)
    }

    override fun onCleared() {
        super.onCleared()
        //TODO: something when fragment destroyed
    }
}