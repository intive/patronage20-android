package com.intive.patronage.smarthome.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.api.HVACRoomAdapter
import com.intive.patronage.smarthome.api.LightAdapter
import com.intive.patronage.smarthome.api.TemperatureAdapter
import com.intive.patronage.smarthome.api.WindowBlindAdapter
import com.intive.patronage.smarthome.feature.dashboard.model.api.authorization.AuthorizationInterceptor
import com.intive.patronage.smarthome.notifications.model.Notification
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val CHANNEL_ID = "default_channel_id"
private const val URL = "https://patronage20-js-master.herokuapp.com"

class NotificationsService : Service() {
    private val timeout = 2L
    private val initialDelay = 0L
    private val period = 5L

    private lateinit var notificationsAPI: NotificationsAPI
    private var disposable: Disposable? = null

    private lateinit var previousNotifications: HashMap<Int, Notification>

    private fun buildMoshiConverter() = Moshi.Builder()
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
        .build()

    private fun getNotifications() = notificationsAPI.getNotifications().toObservable()

    private fun getNotificationsInInterval() = Observable.interval(initialDelay, period, TimeUnit.SECONDS)
            .flatMap { getNotifications() }

    private fun showNotification(title: String, text: String) {
        createNotificationChannel()

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_home_white_24dp)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(0, notification.build())
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
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        notificationsAPI = buildRetrofitClient().create(NotificationsAPI::class.java)

        disposable = getNotificationsInInterval()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .subscribe({ it ->
                Log.d("Notifications Service", it.toString())
                showNotification("Title", "Some text content.")

                if (previousNotifications.isNotEmpty()) {
                    it.notifications.forEach {
                        for (key in previousNotifications.keys) {

                        }
                    }
                }

                it.notifications.forEach { notification ->
                    previousNotifications[notification.id] = notification
                }

                for (key in previousNotifications.keys) {
                    Log.d("Notifications", previousNotifications[key].toString())
                }
            },{
                Log.d("Exception", "ERROR")
            })

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }
}