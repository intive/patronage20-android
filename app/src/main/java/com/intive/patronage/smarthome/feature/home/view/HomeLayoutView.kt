package com.intive.patronage.smarthome.feature.home.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.intive.patronage.smarthome.R

const val SENSOR_SIZE: Float = 30f

class HomeLayoutView(context: Context, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatImageView(context, attrs) {

    private lateinit var bitmap: Bitmap
    private lateinit var cvs: Canvas
    private lateinit var paint: Paint
    private val sensList: MutableList<SensorMock> = mutableListOf()
    private var setup = false

    private fun setupBitmap() {
        val drawable = ContextCompat.getDrawable(context!!, R.drawable.ic_house)
        bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
        cvs = Canvas(bitmap)
        drawable?.let {
            it.setBounds(0, 0, cvs.width, cvs.height)
            it.draw(cvs)
        }
        this.setImageBitmap(bitmap)
        paint = Paint()
        paint.isAntiAlias = true
        setup = true
    }

    fun create(sensList: MutableList<SensorMock>) {
        this.sensList.addAll(sensList)
        val gesture = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onLongPress(e: MotionEvent?) {
                super.onLongPress(e)
                val x = e!!.x.toInt()
                val y = e.y.toInt()
                addSensor(x.toFloat(), y.toFloat())
            }
        })
        this.setOnTouchListener { _, event ->
            gesture.onTouchEvent(event)
            true
        }
    }

    private fun addSensor(x: Float, y: Float) {
        if (!setup) {
            setupBitmap()
        }
        if (checkForSensors(x, y)) {
            drawSensor(x, y)
            sensList.add(
                SensorMock(
                    x,
                    y
                )
            )
            this.setImageBitmap(bitmap)
            showMessage(R.string.sensor_add_success)
        } else {
            showMessage(R.string.sensor_add_failure)
        }
    }

    private fun showMessage(textId: Int) {
        Toast.makeText(
            context,
            context.getString(textId),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun drawSensor(x: Float, y: Float) {
        paint.color = ContextCompat.getColor(context!!, R.color.colorAccent)
        cvs.drawCircle(x, y,
            SENSOR_SIZE, paint)
    }

    private fun checkForSensors(x: Float, y: Float): Boolean {
        if (!sensList.isNullOrEmpty()) {
            for (sensor in sensList) {
                if (sensor.x >= x - SENSOR_SIZE && sensor.x <= x + SENSOR_SIZE && sensor.y >= y - SENSOR_SIZE && sensor.y <= y + SENSOR_SIZE)
                    return false
            }
        }
        return true
    }

}