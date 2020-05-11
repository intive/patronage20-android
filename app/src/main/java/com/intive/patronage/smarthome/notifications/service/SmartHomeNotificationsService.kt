package com.intive.patronage.smarthome.notifications.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.notifications.api.NotificationsAPI
import com.intive.patronage.smarthome.notifications.model.Notification
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.get
import java.util.concurrent.TimeUnit

private const val CHANNEL_ID = "DEFAULT_CHANNEL_ID"
private const val INITIAL_DELAY = 0L
private const val PERIOD = 5L
const val BROADCAST_INTENT_ACTION = "RESTART_SERVICE"
private const val EXCEPTION_TAG = "Exception"
private const val SUCCESS_TAG = "Success"

class SmartHomeNotificationsService : Service(), KoinComponent {
    private var notificationsAPI: NotificationsAPI = get()
    private var notificationsList: Disposable? = null
    private var deleteAPICall: Disposable? = null

    private var previousNotifications: HashMap<Int, Notification> = HashMap(100)
    private var wakeLock: PowerManager.WakeLock? = null

    private fun getNotifications() = notificationsAPI.getNotifications().toObservable()

    private fun getNotificationsInInterval() = Observable.interval(INITIAL_DELAY, PERIOD, TimeUnit.SECONDS)
            .flatMap { getNotifications() }

    private fun deleteNotification(id: Int) {
        deleteAPICall = notificationsAPI.deleteNotification(id)
            .retry()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .subscribe({
                Log.d(SUCCESS_TAG, it.toString())
            }, {
                Log.d(EXCEPTION_TAG, it.toString())
            })
    }

    private fun showNotification(title: String, text: String, id: Int) {
        createNotificationChannel()

        val notification = createNotification(title,text)
        with(NotificationManagerCompat.from(this)) {
            notify(id, notification.build())
        }

        deleteNotification(id)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun findNewNotifications(notificationsList: List<Notification>) {
        notificationsList.filter {
            !it.isChecked
        }.forEach {
            if (previousNotifications.containsKey(it.id)) {
                if (previousNotifications[it.id] != it) {
                    showNotification("Title", "Notification id: ${it.id}", it.id)
                }
            } else {
                showNotification("Title", "Notification id: ${it.id}", it.id)
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        wakeLock = (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "SmartHomeNotificationsService::lock").apply {
                acquire()
            }
        }

        notificationsList = getNotificationsInInterval()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .subscribe({
                if (previousNotifications.isNotEmpty()) {
                    findNewNotifications(it)
                }

                it.forEach { notification ->
                    previousNotifications[notification.id] = notification
                }
            },{
                Log.d(EXCEPTION_TAG, it.toString())
            })

        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

        val foregroundNotification = createNotification(
            resources.getString(R.string.foreground_notification_title),
            ""
        )
        startForeground(101, foregroundNotification.build())
    }

    private fun createNotification(title: String, text: String) = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_home_white_24dp)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        notificationsList?.dispose()
        deleteAPICall?.dispose()

        val broadcastIntent = Intent().apply {
            action = BROADCAST_INTENT_ACTION
            setClass(this@SmartHomeNotificationsService, SmartHomeNotificationsServiceReceiver::class.java)
        }
        this.sendBroadcast(broadcastIntent)

        wakeLock?.let {
            if (it.isHeld) {
                it.release()
            }
        }

        super.onDestroy()
    }
}