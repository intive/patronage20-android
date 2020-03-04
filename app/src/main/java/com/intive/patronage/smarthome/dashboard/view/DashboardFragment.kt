package com.intive.patronage.smarthome.dashboard.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.intive.patronage.smarthome.R

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate with FRAGMENT LAYOUT
        return inflater.inflate(R.layout.dashboard_fragment, container)
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        activity?.setTitle(R.string.dashboard_appbar)
//    }

    // i'm overriding onResume() because if the title is changed somewhere else
    // i can get it back on user input(e.g. back pressed)
    override fun onResume() {
        super.onResume()
        activity?.setTitle(R.string.dashboard_appbar)
    }

}