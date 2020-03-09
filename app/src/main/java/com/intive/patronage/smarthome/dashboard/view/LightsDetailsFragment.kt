package com.intive.patronage.smarthome.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.dashboard.viewmodel.LightsDetailsViewModel
import com.intive.patronage.smarthome.databinding.FragmentLightsDetailsBinding

class LightsDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.title = resources.getString(R.string.lights_details_appbar)
        toolbar.setDisplayHomeAsUpEnabled(true)

        val binding: FragmentLightsDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_lights_details, container, false)
        binding.lifecycleOwner = this
        binding.lightDetailsViewModel = ViewModelProviders.of(this).get(LightsDetailsViewModel::class.java)
        return binding.root
    }
}
