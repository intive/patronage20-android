package com.intive.patronage.smarthome.feature.home.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.intive.patronage.smarthome.AnalyticsWrapper
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.common.replace
import com.intive.patronage.smarthome.feature.dashboard.view.SmartHomeActivity
import com.intive.patronage.smarthome.feature.home.viewmodel.SensorDialogViewModel
import kotlinx.android.synthetic.main.smart_home_activity.*

import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val dialogViewModel: SensorDialogViewModel by viewModel()
    lateinit var image: HomeLayoutView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myView = inflater.inflate(R.layout.home_fragment, container, false)
        image = myView.findViewById(R.id.home)
        image.create(dialogViewModel.items, fragmentManager!!)
        return myView
    }

}
