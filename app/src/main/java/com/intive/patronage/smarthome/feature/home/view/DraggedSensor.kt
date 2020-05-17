package com.intive.patronage.smarthome.feature.home.view

import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.SensorType
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor

class DraggedSensor : Drawable() {
    private var x: Float = 0f
    private var y: Float = 0f
    private var iconColor: Int = Color.BLACK
    private var sensorSize: Float = 0f
    private var sensorColor: Int = Color.BLACK
    private var sensorBorderSize: Float = 0f
    private var sensorBorderColor: Int = Color.BLACK
    private var sensorIcon: Drawable? = null
    private var sensorIconSize: Float = 0f

    override fun draw(canvas: Canvas) {
        val paint = Paint().apply {
            isAntiAlias = true
            isDither = true
        }
        paint.setShadowLayer(sensorSize * 0.3f, 4f, 4f, Color.BLACK)
        paint.color = sensorBorderColor
        canvas.drawCircle(x, y, sensorBorderSize, paint)
        paint.color = sensorColor
        canvas.drawCircle(x, y, sensorSize, paint)
        sensorIcon?.setBounds(
            (x - sensorIconSize).toInt(),
            (y - sensorIconSize).toInt(),
            (x + sensorIconSize).toInt(),
            (y + sensorIconSize).toInt()
        )
        sensorIcon?.setTint(iconColor)
        sensorIcon?.draw(canvas)
    }

    fun initialize(resources: Resources, sensorToPost: DashboardSensor?, imageHeight: Int) {
        iconColor = resources.getColor(R.color.text, null)
        sensorSize = SENSOR_SIZE * imageHeight
        sensorColor = resources.getColor(R.color.colorPrimary, null)
        sensorBorderSize = SENSOR_BORDER_SIZE * imageHeight
        sensorBorderColor = resources.getColor(R.color.colorAccentLightDark, null)
        sensorIcon = SensorType.values().find {
            it.type == sensorToPost?.type
        }?.getDrawable(resources)
        sensorIconSize = SENSOR_ICON_SIZE * imageHeight
    }

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
        this.invalidateSelf()
    }

    override fun setAlpha(p0: Int) {}

    override fun getOpacity() = PixelFormat.OPAQUE

    override fun setColorFilter(p0: ColorFilter?) {}
}