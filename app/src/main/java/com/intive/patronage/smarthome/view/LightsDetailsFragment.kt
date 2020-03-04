package com.intive.patronage.smarthome.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.databinding.FragmentLightsDetailsBinding
import com.intive.patronage.smarthome.viewmodel.LightsDetailsViewModel
import kotlinx.android.synthetic.main.fragment_lights_details.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class LightsDetailsFragment : Fragment() {

    //private val lightsDetailsViewModel: LightsDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*val binding: FragmentLightsDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_lights_details, container, false)
        binding.lifecycleOwner = this
        binding.lightDetailsViewModel = lightsDetailsViewModel
        var view: View = binding.root*/

        val view = inflater.inflate(R.layout.fragment_lights_details, container, false)

        val lightsDetailsViewModel = ViewModelProviders.of(this).get(LightsDetailsViewModel::class.java)

        //observeViewModel()

        return view
    }

    /*fun observeViewModel() {
        lightsDetailsViewModel.getLights().observe(this, Observer {
            //TODO: update UI
        })
    }*/
}
