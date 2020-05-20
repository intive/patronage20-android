package com.intive.patronage.smarthome.feature.login.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.databinding.ActivityRegisterBinding
import com.intive.patronage.smarthome.feature.login.authentication.AuthenticationService
import com.intive.patronage.smarthome.feature.login.animation.startEnterAnimation
import com.intive.patronage.smarthome.feature.login.animation.startExitAnimation
import com.intive.patronage.smarthome.feature.login.viewmodel.RegisterViewModel
import com.intive.patronage.smarthome.navigator.DashboardCoordinator
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RegisterActivity : AppCompatActivity(), RegisterEventListener {
    private val authenticationService: AuthenticationService by inject {
        parametersOf(this)
    }
    private val coordinator: DashboardCoordinator by inject {
        parametersOf(this)
    }
    private val registerViewModel: RegisterViewModel by viewModel {
        parametersOf(authenticationService, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerViewModel.registerEventListener = this

        val binding: ActivityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.viewModel = registerViewModel
        binding.lifecycleOwner = this

        authenticationService.initAuthFirebase()
    }

    override fun onResume() {
        super.onResume()
        registerInfo.isEnabled = true
        registerViewModel.isCreateAccountEnabled.value = false

        overridePendingTransition(NO_ANIMATION, NO_ANIMATION)
        startEnterAnimation(registerCard, registerInfo, false)
    }

    override fun startAnimation() {
        registerInfo.isEnabled = false
        startExitAnimation(coordinator, registerCard, registerInfo, false)
    }
}
