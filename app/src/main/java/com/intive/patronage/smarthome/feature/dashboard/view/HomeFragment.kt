package com.intive.patronage.smarthome.feature.dashboard.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.intive.patronage.smarthome.R
import kotlinx.android.synthetic.main.home_fragment.*




class SensorMock(val x: Float, val y: Float)

class HomeFragment : Fragment() {

    private val sensList: MutableList<SensorMock> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myView = inflater.inflate(R.layout.home_fragment, container, false)
        val image = myView.findViewById<HomeLayoutView>(R.id.home)
        image.create(sensList)
        return myView
    }

}
