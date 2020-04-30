package com.intive.patronage.smarthome.notifications

import com.intive.patronage.smarthome.notifications.model.NotificationsList
import io.reactivex.Single
import retrofit2.http.GET

interface NotificationsAPI {
    @GET("/api/v1/notifications")
    fun getNotifications(): Single<NotificationsList>
}