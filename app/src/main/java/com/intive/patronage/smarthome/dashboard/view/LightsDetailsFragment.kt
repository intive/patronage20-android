package com.intive.patronage.smarthome.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.dashboard.viewmodel.LightsDetailsViewModel
import com.intive.patronage.smarthome.databinding.FragmentLightsDetailsBinding
import org.koin.android.viewmodel.ext.android.viewModel

class LightsDetailsFragment : Fragment() {

    val lightsDetailsViewModel by viewModel<LightsDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.title = resources.getString(R.string.lights_details_appbar)
        toolbar.setDisplayHomeAsUpEnabled(true)

        val binding: FragmentLightsDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_lights_details, container, false)
        binding.lifecycleOwner = this
        binding.lightDetailsViewModel = lightsDetailsViewModel
        return binding.root

    }
}
