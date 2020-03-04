package com.intive.patronage.smarthome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.intive.patronage.smarthome.dashboard.view.DashboardActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }
}
