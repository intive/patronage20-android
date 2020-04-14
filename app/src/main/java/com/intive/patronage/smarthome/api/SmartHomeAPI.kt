package com.intive.patronage.smarthome.api

import com.intive.patronage.smarthome.feature.dashboard.model.Dashboard
import io.reactivex.Single
import retrofit2.http.GET

interface SmartHomeAPI {

    @GET("/api/v1/dashboard")
    fun getDashboard(): Single<Dashboard>
}