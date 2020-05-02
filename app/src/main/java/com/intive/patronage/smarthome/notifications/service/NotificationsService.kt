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
import android.widget.Toast
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

private const val CHANNEL_ID = "default_channel_id"
//private const val URL = "https://patronage20-js-master.herokuapp.com"

class NotificationsService : Service(), KoinComponent {
    //private val timeout = 2L
    private val initialDelay = 0L
    private val period = 5L

    private var notificationsAPI: NotificationsAPI = get()
    private var getDisposable: Disposable? = null
    private var deleteDisposable: Disposable? = null

    private var previousNotifications: HashMap<Int, Notification> = HashMap(100)
    private var wakeLock: PowerManager.WakeLock? = null

    /*private fun buildMoshiConverter() = Moshi.Builder()
        .add(TemperatureAdapter())
        .add(HVACRoomAdapter())
        .add(WindowBlindAdapter())
        .add(LightAdapter())
        .build()

    private fun buildOkHttpClient() = OkHttpClient.Builder()
        .callTimeout(timeout, TimeUnit.MINUTES)
        .connectTimeout(timeout, TimeUnit.MINUTES)
        .addInterceptor(AuthorizationInterceptor())
        .cache(null)
        .build()

    private fun buildRetrofitClient() = Retrofit.Builder()
        .callFactory(buildOkHttpClient())
        .baseUrl(URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(buildMoshiConverter()))
        .build()*/

    private fun getNotifications() = notificationsAPI.getNotifications().toObservable()

    private fun getNotificationsInInterval() = Observable.interval(initialDelay, period, TimeUnit.SECONDS)
            .flatMap { getNotifications() }

    private fun deleteNotification(id: Int) {
        deleteDisposable = notificationsAPI.deleteNotification(id)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .subscribe()
    }

    private fun showNotification(title: String, text: String, id: Int) {
        createNotificationChannel()

        val notification = NotificationCompat.Builder(this,
                CHANNEL_ID
            )
            .setSmallIcon(R.drawable.ic_home_white_24dp)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(id, notification.build())
        }

        //deleteNotification(id)
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
        notificationsList.forEach {
            if (!it.isChecked) {
                var isThere = false
                for (key in previousNotifications.keys) {
                    if (previousNotifications[key] == it) {
                        isThere = true
                        break
                    }
                }
                if (!isThere) {
                    showNotification("Title", "Some text content.", it.id)
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //notificationsAPI = buildRetrofitClient().create(NotificationsAPI::class.java)

        wakeLock = (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "EndlessService::lock").apply {
                acquire()
            }
        }

        getDisposable = getNotificationsInInterval()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .subscribe({
                Log.d("Notifications Service", it.toString())

                showNotification("Title", "Some text content.", 0) // just for testing

                if (previousNotifications.isNotEmpty()) {
                    findNewNotifications(it)
                }

                it.forEach { notification ->
                    previousNotifications[notification.id] = notification
                }
            },{
                Log.d("Exception", it.toString())
            })

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        //getDisposable?.dispose()
        //deleteDisposable?.dispose()

        val broadcastIntent = Intent().apply {
            action = "restart_service"
            setClass(this@NotificationsService, Receiver::class.java)
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