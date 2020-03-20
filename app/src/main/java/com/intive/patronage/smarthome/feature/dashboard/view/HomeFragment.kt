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


const val SENSOR_SIZE: Float = 30f

class SensorMock(val x: Float, val y: Float)

class HomeFragment : Fragment() {

    private lateinit var image: ImageView
    private lateinit var bitmap: Bitmap
    private lateinit var cvs: Canvas
    private lateinit var paint: Paint
    private var setup = false
    private val sensList: MutableList<SensorMock> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myView = inflater.inflate(R.layout.fragment_home, container, false)
        image = myView.findViewById(R.id.hose)
        val gesture = GestureDetector(activity, object : SimpleOnGestureListener() {
            override fun onLongPress(e: MotionEvent?) {
                val x = e!!.x.toInt()
                val y = e.y.toInt()
                addSensor(x.toFloat(), y.toFloat())
            }
        })
        myView.setOnTouchListener { _, event -> gesture.onTouchEvent(event) }
        return myView
    }

    private fun addSensor(x: Float, y: Float) {
        if (!setup) {
            setupBitmap()
        }
        if (checkForSensors(x, y)) {
            drawSensor(x, y)
            image.setImageBitmap(bitmap)
            Toast.makeText(
                activity,
                "Sensor został dodany x:" + x.toString() + ", y:" + y.toString(),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(activity, "W tym miejscu istnieje już sensor", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupBitmap() {
        val drawable = ContextCompat.getDrawable(context!!, R.drawable.ic_house)
        bitmap = Bitmap.createBitmap(image.width, image.height, Bitmap.Config.ARGB_8888)
        cvs = Canvas(bitmap)
        drawable!!.setBounds(0, 0, cvs.width, cvs.height)
        drawable.draw(cvs)
        image.setImageBitmap(bitmap)
        setup = true
        paint = Paint()
        paint.isAntiAlias = true
    }

    private fun drawSensor(x: Float, y: Float) {
        paint.color = ContextCompat.getColor(context!!, R.color.colorAccent)
        cvs.drawCircle(x, y, SENSOR_SIZE, paint)
        sensList.add(SensorMock(x, y))
    }

    private fun checkForSensors(x: Float, y: Float): Boolean {
        if (!sensList.isNullOrEmpty()) {
            sensList.forEach lit@{
                if (it.x >= x - SENSOR_SIZE && it.x <= x + SENSOR_SIZE && it.y >= y - SENSOR_SIZE && it.y <= y + SENSOR_SIZE)
                    return false
            }
        }
        return true
    }
}
