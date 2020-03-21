package com.intive.patronage.smarthome.feature.dashboard.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.intive.patronage.smarthome.R
import kotlinx.android.synthetic.main.fragment_home.*




class SensorMock(val x: Float, val y: Float)

class HomeFragment : Fragment() {

    private val sensList: MutableList<SensorMock> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myView = inflater.inflate(R.layout.fragment_home, container, false)
        val image = myView.findViewById<HomeLayoutView>(R.id.home)
        image.create(sensList)
        return myView
    }

}
