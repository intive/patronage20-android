package com.intive.patronage.smarthome.feature.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.login.animation.startRegisterActivityEnterAnimation
import com.intive.patronage.smarthome.feature.login.animation.startRegisterActivityExitAnimation
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

        authentication.initAuthFirebase()

        registerInfo.setOnClickListener {
            it.isEnabled = false
            startRegisterActivityExitAnimation(this, coordinator, registerCard, registerInfo)
        }

        createAccount.setOnClickListener {
            val email = newAccountEmail.text.toString()
            val password = newAccountPassword.text.toString()
            val confirm = confirmPassword.text.toString()
            if (email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                val isConfirmed = password == confirm
                if (isConfirmed) {
                    authentication.createUserWithEmailAndPassword(email, password)
                } else {
                    Toast.makeText(this, R.string.different_password, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerInfo.isEnabled = true

        overridePendingTransition(NO_ANIMATION, NO_ANIMATION)
        startRegisterActivityEnterAnimation(this, registerCard, registerInfo)
    }
}
