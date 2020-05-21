package com.intive.patronage.smarthome.feature.hvac.model.api

import com.intive.patronage.smarthome.api.SmartHomeAPI

class HVACDetailsService(private val smartHomeAPI: SmartHomeAPI) {

    fun changeHVACStatus (hvacRoomDTO: HVACRoomDTO) =smartHomeAPI.updateHVACRoom(hvacRoomDTO)


}