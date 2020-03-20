package com.intive.patronage.smarthome.feature.blind.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.databinding.FragmentBlindDetailsBinding
import com.intive.patronage.smarthome.feature.blind.viewmodel.BlindDetailsViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class BlindDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.title = resources.getString(R.string.blind_details_appbar)
        toolbar.setDisplayHomeAsUpEnabled(true)

        val binding: FragmentBlindDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_blind_details, container, false)

        val blindDetailsViewModel by viewModel<BlindDetailsViewModel> {
            parametersOf(binding.blindView, this.arguments?.getInt("ID"))
        }

        binding.lifecycleOwner = this
        binding.blindDetailsViewModel = blindDetailsViewModel
        return binding.root
    }
}
