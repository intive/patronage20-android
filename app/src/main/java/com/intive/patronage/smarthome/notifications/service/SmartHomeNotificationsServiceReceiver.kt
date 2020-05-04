package com.intive.patronage.smarthome.notifications.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class SmartHomeNotificationsServiceReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED || intent.action == BROADCAST_INTENT_ACTION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(Intent(context, SmartHomeNotificationsService::class.java))
            } else {
                context.startService(Intent(context, SmartHomeNotificationsService::class.java))
            }
        }
    }
}