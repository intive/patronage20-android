package com.intive.patronage.smarthome.api

import com.intive.patronage.smarthome.feature.dashboard.model.Dashboard
import io.reactivex.Single
import retrofit2.http.*

interface SmartHomeAPI {

    @GET("/api/v1/dashboard")
    fun getDashboard(): Single<Dashboard>

    @GET("/api/v1/dashboard/delete")
    fun deleteSensors(): Single<Boolean>
}