package com.intive.patronage.smarthome.feature.hvac

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.databinding.FragmentHvacDetailsBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HvacDetailsFragment : Fragment(), HVACViewEventListener {

    private val hvacViewModel by viewModel<HvacViewModel> { parametersOf(this, this.arguments?.getInt("ID")) }
    lateinit var binding: FragmentHvacDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.title = resources.getString(R.string.hvac_details_appbar)
        toolbar.setDisplayHomeAsUpEnabled(true)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hvac_details, container, false)
        binding.lifecycleOwner = this
        binding.hvacViewModel = hvacViewModel
        Log.d("testowanie ", "Start")

        return binding.root
    }


    override fun setTemperature(temperature: Float) {
        binding.hvacCircle.changeTemperature(temperature)

    }

    override fun setHysteresis(hysteresis: Float) {
        binding.hvacCircle.hysteresis = hysteresis
        binding.hvacCircle.postInvalidate()
    }


}