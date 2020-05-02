package com.intive.patronage.smarthome.notifications.api

import com.intive.patronage.smarthome.api.*
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

private const val URL = "https://patronage20-js-master.herokuapp.com"

class NotificationsAPIFactory(private val httpClient: OkHttpClient) {
    private fun buildMoshiConverter(): Moshi = Moshi.Builder()
        .add(TemperatureAdapter())
        .add(HVACRoomAdapter())
        .add(WindowBlindAdapter())
        .add(LightAdapter())
        .build()

    fun buildRetrofitClient(): Retrofit = Retrofit.Builder()
        .callFactory(httpClient)
        .baseUrl(URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(buildMoshiConverter()))
        .build()
}