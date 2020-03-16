package com.intive.patronage.smarthome.api

import com.intive.patronage.smarthome.dashboard.model.Dashboard
import io.reactivex.Single
import retrofit2.http.GET

interface SmartHomeAPI {

    @GET("/dashboard")
    fun getDashboard(): Single<Dashboard>
}