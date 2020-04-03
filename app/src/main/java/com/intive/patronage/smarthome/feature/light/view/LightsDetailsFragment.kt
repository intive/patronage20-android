package com.intive.patronage.smarthome.feature.light.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.feature.light.viewmodel.LightsDetailsViewModel
import com.intive.patronage.smarthome.databinding.FragmentLightsDetailsBinding
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeActivity
import kotlinx.android.synthetic.main.smart_home_activity.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LightsDetailsFragment : Fragment() {

    private val lightsDetailsViewModel by viewModel<LightsDetailsViewModel>() {
        parametersOf(this.arguments?.getInt("ID"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.title = resources.getString(R.string.lights_details_appbar)
        toolbar.setDisplayHomeAsUpEnabled(true)
        (activity as SmartHomeActivity).hideLogo()

        lightsDetailsViewModel.toastMessage.observe(this, Observer {
            if (it != null) {
                val message = getString(it)
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }
        })

        val binding: FragmentLightsDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_lights_details, container, false)
        binding.lifecycleOwner = this
        binding.lightDetailsViewModel = lightsDetailsViewModel
        return binding.root
    }
}
