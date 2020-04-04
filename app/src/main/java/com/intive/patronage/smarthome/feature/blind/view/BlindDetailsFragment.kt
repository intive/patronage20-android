package com.intive.patronage.smarthome.feature.blind.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.databinding.FragmentBlindDetailsBinding
import com.intive.patronage.smarthome.feature.blind.viewmodel.BlindDetailsViewModel
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeActivity
import kotlinx.android.synthetic.main.smart_home_activity.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class BlindDetailsFragment : Fragment(), BlindViewEventListener {

    private lateinit var binding: FragmentBlindDetailsBinding

    val blindDetailsViewModel by viewModel<BlindDetailsViewModel> {
        parametersOf(this ,this.arguments?.getInt("ID"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.title = resources.getString(R.string.blind_details_appbar)
        toolbar.setDisplayHomeAsUpEnabled(true)
        (activity as SmartHomeActivity).hideLogo()

        blindDetailsViewModel.blindViewEventListener = this

        blindDetailsViewModel.toastMessage.observe(this, Observer {
            if (it != null) {
                val message = getString(it)
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }
        })

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_blind_details, container, false)
        binding.lifecycleOwner = this
        binding.blindDetailsViewModel = blindDetailsViewModel
        return binding.root
    }

    override fun blindUp() {
        with(binding.blindView) {
            if (this.position >= this.onePercent) this.position -= this.onePercent
            else this.position = 0F
            this.invalidate()
        }
    }

    override fun blindDown() {
        with(binding.blindView) {
            if (this.position < (this.maxPosition - this.onePercent)) this.position += this.onePercent
            else this.position = this.maxPosition
            this.invalidate()
        }
    }

    override fun setStartingPosition(percentPosition: Int) {
        with(binding.blindView) {
            this.position = this.onePercent * percentPosition
            this.invalidate()
        }
    }
}
