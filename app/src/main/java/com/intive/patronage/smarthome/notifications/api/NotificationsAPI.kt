package com.intive.patronage.smarthome.notifications.api

import com.intive.patronage.smarthome.notifications.model.Notification
import io.reactivex.Single
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface NotificationsAPI {
    @GET("/api/v1/notifications")
    fun getNotifications(): Single<List<Notification>>

    @DELETE("/api/v1/notifications/{id}")
    fun deleteNotification(@Path("id") id: Int): Single<Any>
}