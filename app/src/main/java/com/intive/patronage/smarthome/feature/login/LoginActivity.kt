package com.intive.patronage.smarthome.feature.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class LoginActivity : AppCompatActivity() {
    private val authentication: Authentication by inject {
        parametersOf(this)
    }
    private val coordinator: DashboardCoordinator by inject {
        parametersOf(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authentication.initAuthFirebase()
        authentication.initGoogleSignIn()

        signInButton.setOnClickListener {
            authentication.signIn()
        }

        signUp.setOnClickListener {
            it.isEnabled = false
            val toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom)
            loginInfo.startAnimation(toBottom)

            val toLeft = AnimationUtils.loadAnimation(this, R.anim.to_left)
            toLeft.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(p0: Animation?) {}
                override fun onAnimationEnd(p0: Animation?) {
                    coordinator.goToRegisterScreen()
                }
                override fun onAnimationStart(p0: Animation?) {}
            })
            loginCard.startAnimation(toLeft)
        }
    }

    override fun onResume() {
        super.onResume()
        signUp.isEnabled = true
        overridePendingTransition(0, 0)

        val fromLeft = AnimationUtils.loadAnimation(this, R.anim.from_left)
        loginCard.startAnimation(fromLeft)

        val fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom)
        loginInfo.startAnimation(fromBottom)
    }

    override fun onStart() {
        super.onStart()
        authentication.checkIfUserIsLogged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        authentication.accountVerification(requestCode, data)
    }
}
