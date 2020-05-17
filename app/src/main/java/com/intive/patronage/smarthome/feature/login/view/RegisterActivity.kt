package com.intive.patronage.smarthome.feature.login.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.login.authentication.Authentication
import com.intive.patronage.smarthome.feature.login.animation.startEnterAnimation
import com.intive.patronage.smarthome.feature.login.animation.startExitAnimation
import com.intive.patronage.smarthome.feature.login.authentication.createAccount
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class RegisterActivity : AppCompatActivity() {
    private val authentication: Authentication by inject {
        parametersOf(this)
    }
    private val coordinator: DashboardCoordinator by inject {
        parametersOf(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val view = window.decorView.rootView

        authentication.initAuthFirebase()

        registerInfo.setOnClickListener {
            it.isEnabled = false
            startExitAnimation(this, coordinator, registerCard, registerInfo, false)
        }

        createAccount.setOnClickListener {
            createAccount(view, this, authentication)
        }
    }

    override fun onResume() {
        super.onResume()
        registerInfo.isEnabled = true

        overridePendingTransition(NO_ANIMATION, NO_ANIMATION)
        startEnterAnimation(this, registerCard, registerInfo, false)
    }
}
