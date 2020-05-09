package com.intive.patronage.smarthome.api

import com.intive.patronage.smarthome.feature.blind.model.BlindSensor
import com.intive.patronage.smarthome.feature.dashboard.model.Dashboard
import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface SmartHomeAPI {

    @GET("/api/v1/dashboard")
    fun getDashboard(): Single<Dashboard>

    @GET("/api/v1/dashboard/delete")
    fun deleteSensors(): Single<Boolean>
  
    @POST("/api/v1/map/{sensorID}")
    fun addSensor(@Path("sensorID") id: Int,
                  @Body body: HomeSensor): Single<Boolean>

    @DELETE("/api/v1/map/{sensorID}")
    fun deleteSensor(@Path("sensorID") id: Int): Single<Boolean>

    @PUT("/api/v1/blinds")
    fun updateBlindPosition(@Body body: BlindSensor): Single<Response<Void>>
}