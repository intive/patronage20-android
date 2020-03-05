package com.intive.patronage.smarthome.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//TODO: add service to constructor
class LightsDetailsViewModel : ViewModel() {

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
        //TODO: fetch data from service
    }

    // buttons on click methods
    fun onCancelClicked() {
        //TODO: reset seek bar state to previous
    }

    fun onOkClicked() {
        /*TODO:
            - change view color on seek bar swipe
            - send data to API
         */
    }

    fun onAppbarBackButtonClicked() {
        //TODO: implement navigator
    }

    override fun onCleared() {
        super.onCleared()
        //TODO: something when fragment destroyed
    }
}