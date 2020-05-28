package com.intive.patronage.smarthome.feature.home.view

import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.SensorType
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor

private const val SHADOW_RADIUS = 0.3f
private const val SHADOW_OFFSET = 4f

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
        paint.setShadowLayer(sensorSize * SHADOW_RADIUS, SHADOW_OFFSET, SHADOW_OFFSET, Color.BLACK)
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

    override fun setAlpha(alpha: Int) {}

    fun initialize(resources: Resources, sensorToPost: DashboardSensor?, imageHeight: Int) {
        iconColor = resources.getColor(R.color.text, null)
        sensorSize = SENSOR_SIZE * imageHeight
        sensorColor = resources.getColor(R.color.colorPrimary, null)
        sensorBorderSize = SENSOR_BORDER_SIZE * imageHeight
        sensorBorderColor = resources.getColor(R.color.colorAccentLightDark, null)
        sensorToPost?.let {sensor ->
            sensorIcon = SensorType.values().find {
                it.type == sensor.type
            }?.getDrawable(sensor, resources)
        }
        sensorIconSize = SENSOR_ICON_SIZE * imageHeight
    }

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
        this.invalidateSelf()
    }


    override fun getOpacity() = PixelFormat.OPAQUE

    override fun setColorFilter(p0: ColorFilter?) {}
}