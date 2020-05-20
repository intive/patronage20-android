package com.intive.patronage.smarthome.feature.login.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.databinding.ActivityLoginBinding
import com.intive.patronage.smarthome.feature.login.authentication.AuthenticationService
import com.intive.patronage.smarthome.feature.login.animation.startEnterAnimation
import com.intive.patronage.smarthome.feature.login.animation.startExitAnimation
import com.intive.patronage.smarthome.feature.login.viewmodel.LoginViewModel
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

const val NO_ANIMATION = 0

class LoginActivity : AppCompatActivity(), LoginEventListener {
    private val authenticationService: AuthenticationService by inject {
        parametersOf(this)
    }
    private val coordinator: DashboardCoordinator by inject {
        parametersOf(this)
    }
    private val loginViewModel: LoginViewModel by viewModel {
        parametersOf(authenticationService, this)
    }

    private var isTheFirstTime: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel.loginEventListener = this

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = loginViewModel
        binding.lifecycleOwner = this

        authenticationService.initAuthFirebase()
        authenticationService.initGoogleSignIn()
    }

    override fun onResume() {
        super.onResume()
        setupView()

        if (isTheFirstTime) {
             isTheFirstTime = false
        } else {
            overridePendingTransition(NO_ANIMATION, NO_ANIMATION)
            startEnterAnimation(loginCard, loginInfo, true)
        }
    }

    private fun setupView() {
        loginInfo.isEnabled = true
        loginViewModel.isSignInEnabled.value = false

        email.text.clear()
        loginViewModel.emailError.value = ""

        password.text.clear()
        loginViewModel.passwordError.value = ""
    }

    override fun onStart() {
        super.onStart()
        authenticationService.checkIfUserIsLogged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        authenticationService.accountVerification(requestCode, data)
    }

    override fun startAnimation() {
        loginInfo.isEnabled = false
        startExitAnimation(coordinator, loginCard, loginInfo, true)
    }
}