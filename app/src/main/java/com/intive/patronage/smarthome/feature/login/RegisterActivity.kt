package com.intive.patronage.smarthome.feature.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.login.Authentication
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

        signIn.setOnClickListener {
            coordinator.goToLoginScreen()
        }

        createAccount.setOnClickListener {
            val isConfirmed = newAccountPassword.text.toString() == confirmPassword.text.toString()
            if (isConfirmed) {
                authentication.createAccount(newAccountEmail.text.toString(), newAccountPassword.text.toString())
            } else {
                Toast.makeText(this, "Passwords are not the same", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
