package com.intive.patronage.smarthome.notifications.model

import com.squareup.moshi.Json

data class NotificationsList(
    @Json(name = "notifications") val notifications: List<Notification>
)