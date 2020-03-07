package com.intive.patronage.smarthome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.intive.patronage.smarthome.dashboard.dashboardModule
import com.intive.patronage.smarthome.dashboard.view.SmartHomeActivity
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainActivity)
            modules(dashboardModule)
        }
        startActivity(Intent(this, SmartHomeActivity::class.java))
        finish()
    }
}
