package com.intive.patronage.smarthome.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.SmartHomeApplication
import com.intive.patronage.smarthome.common.NOTIFICATIONS_VISIBILITY
import com.intive.patronage.smarthome.common.PreferencesWrapper
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

private const val TAG = "FCM_SERVICE"
private const val CHANNEL_ID = "default"

class SmartHomeMessagingService : FirebaseMessagingService() {
    private val preferences: PreferencesWrapper by inject {
        parametersOf(this)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if (preferences.checkIfContains(NOTIFICATIONS_VISIBILITY)) {
            if (preferences.getBooleanFromPreference(NOTIFICATIONS_VISIBILITY)) {
                message.data.isNotEmpty().let {
                    sendNotification(message)
                    Log.d(TAG, "From: ${message.from}")
                    Log.d(TAG, "Notification: id: ${message.messageId}, body: ${message.notification?.body}")
                }
            }
        }
    }

    private fun sendNotification(message: RemoteMessage) {
        createNotificationChannel()

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.splash_screen_icon)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(getPendingIntent())
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(message.notification?.body)
                    .setBigContentTitle(message.notification?.title)
            )
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            notify(0, builder.build())
        }

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getPendingIntent(): PendingIntent {
        val intent = Intent(this, SmartHomeApplication::class.java)
        return PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }
}