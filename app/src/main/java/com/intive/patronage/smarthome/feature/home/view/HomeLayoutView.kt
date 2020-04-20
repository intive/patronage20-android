package com.intive.patronage.smarthome.feature.home.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.SensorDialogType
import com.intive.patronage.smarthome.common.percentToCoordinateX
import com.intive.patronage.smarthome.common.percentToCoordinateY
import com.intive.patronage.smarthome.feature.home.model.api.HomeSensor

const val SENSOR_SIZE: Float = 30f

class HomeLayoutView(context: Context, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatImageView(context, attrs) {

    private lateinit var bitmap: Bitmap
    private lateinit var cvs: Canvas
    private lateinit var clearBitmap: Bitmap
    private lateinit var paint: Paint
    private var sensList: MutableList<HomeSensor> = mutableListOf()
    private var setup = false

    private fun setupBitmap() {
        val drawable = ContextCompat.getDrawable(context, R.drawable.ic_house)
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
        clearBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (!setup) {
            setupBitmap()
        }
        paint.color = ContextCompat.getColor(context, R.color.backgroundColor)
        cvs.drawRect(0.toFloat(), 0.toFloat(), this.width.toFloat(), this.height.toFloat(), paint)
        cvs.drawBitmap(clearBitmap, 0f, 0f, null)
        for (sensor in sensList) {
            if (sensor.mapPosition != null) {
                drawSensor(
                    percentToCoordinateX(sensor.mapPosition.x, this.width),
                    percentToCoordinateY(sensor.mapPosition.y, this.height),
                    sensor.type
                )
            }
        }
        this.setImageBitmap(bitmap)
    }

    fun setData(sensList: List<HomeSensor>){
        this.sensList.clear()
        this.sensList.addAll(sensList)
    }

    fun create(fragmentManager: FragmentManager) {
        val gesture = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onLongPress(e: MotionEvent?) {
                super.onLongPress(e)
                val x = e!!.x.toInt()
                val y = e.y.toInt()
                val sensorDialog = SensorDialog()
                sensorDialog.setSensorPosition(x.toFloat(), y.toFloat(), this@HomeLayoutView.width, this@HomeLayoutView.height)
                sensorDialog.show(fragmentManager, "SensorList")
            }
        })
        this.setOnTouchListener { _, event ->
            gesture.onTouchEvent(event)
            true
        }
    }

    fun addSensor(x: Float, y: Float, sensorType: String): Boolean {
        if (checkForSensors(x, y)) {
            drawSensor(x, y, sensorType)
            this.setImageBitmap(bitmap)
            showMessage(R.string.sensor_add_success)
            return true
        } else {
            showMessage(R.string.sensor_add_failure)
        }
        return false
    }

    private fun showMessage(textId: Int) {
        Toast.makeText(
            context,
            context.getString(textId),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun drawSensor(x: Float, y: Float, sensorType: String) {
        val findSensor = SensorDialogType.values().find {
            it.type == sensorType
        }
        var color = R.color.colorAccent
        for (sensorT in SensorDialogType.values()) {
            if (sensorT == findSensor) {
                color = sensorT.getPaintColor()
            }
        }
        paint.color = ContextCompat.getColor(context!!, color)
        cvs.drawCircle(x, y, SENSOR_SIZE, paint)
        this.setImageBitmap(bitmap)
    }

    private fun checkForSensors(x: Float, y: Float): Boolean {
        val distance = SENSOR_SIZE * 2 + 2
        if (!sensList.isNullOrEmpty()) {
            for (sensor in sensList) {
                if(sensor.mapPosition != null){
                    val sensorCoordX = percentToCoordinateX(sensor.mapPosition.x, this.width)
                    val sensorCoordY = percentToCoordinateY(sensor.mapPosition.y, this.height)
                    if (sensorCoordX >= x - distance && sensorCoordX <= x + distance && sensorCoordY >= y - distance && sensorCoordY <= y + distance)
                        return false
                }
            }
        }
        return true
    }

}