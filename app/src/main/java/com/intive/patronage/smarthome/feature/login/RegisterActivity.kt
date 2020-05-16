package com.intive.patronage.smarthome.feature.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
            it.isEnabled = false
            val toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom)
            registerInfo.startAnimation(toBottom)

            val toRight = AnimationUtils.loadAnimation(this, R.anim.to_right)
            toRight.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {}
                override fun onAnimationEnd(p0: Animation?) {
                    coordinator.goToLoginScreen()
                }
                override fun onAnimationStart(p0: Animation?) {}
            })
            registerCard.startAnimation(toRight)
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

    override fun onResume() {
        super.onResume()
        signIn.isEnabled = true
        overridePendingTransition(0, 0)

        val fromRight = AnimationUtils.loadAnimation(this, R.anim.from_right)
        registerCard.startAnimation(fromRight)

        val fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom)
        registerInfo.startAnimation(fromBottom)
    }
}
