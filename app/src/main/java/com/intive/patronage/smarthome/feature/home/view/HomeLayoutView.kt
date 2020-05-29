package com.intive.patronage.smarthome.feature.home.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import androidx.core.content.ContextCompat
import com.intive.patronage.smarthome.R
import com.intive.patronage.smarthome.SensorType
import com.intive.patronage.smarthome.common.percentToCoordinateX
import com.intive.patronage.smarthome.common.percentToCoordinateY
import com.intive.patronage.smarthome.feature.dashboard.model.DashboardSensor

const val SENSOR_SIZE: Float = 0.06f // size - radius in percentage
const val SENSOR_BORDER_SIZE: Float = 0.065f // size - radius in percentage
const val SENSOR_ICON_SIZE: Float = 0.035f // size - radius in percentage
const val SENSOR_TEXT_SIZE: Float = 0.035f
const val SENSOR_DISTANCE: Float =
    SENSOR_BORDER_SIZE * 2 + SENSOR_TEXT_SIZE / 2 // distance in percentage
const val SMOKE_SENSOR_BLINK_INTERVAL: Float = 500f // interval is milliseconds
const val DEGREE_CHAR = '°'

class HomeLayoutView(context: Context, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatImageView(context, attrs) {

    private lateinit var bitmap: Bitmap
    private lateinit var cvs: Canvas
    private lateinit var clearBitmap: Bitmap
    private lateinit var paint: Paint
    private lateinit var arcPaint: Paint
    private lateinit var textPaint: Paint
    private lateinit var stkPaint: Paint
    private lateinit var oval: RectF
    private var smokeSensorBlinkStart: Long = System.currentTimeMillis()
    private var smokeSensorBlink: Boolean = false
    private var sensList: MutableList<DashboardSensor> = mutableListOf()
    private var setup = false
    private var sensorAlpha: Float = 0.3f
    var sensorPendingToPost: DashboardSensor? = null
    var sensorPendingToPostX: Float = 0f
    var sensorPendingToPostY: Float = 0f
    var sensorPendingToDelete: DashboardSensor? = null
    var sensorPendingToMove: DashboardSensor? = null

    private fun setupView() {
        val drawable = ContextCompat.getDrawable(context, R.drawable.ic_house)
        bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
        cvs = Canvas(bitmap)
        drawable?.let {
            it.setBounds(0, 0, cvs.width, cvs.height)
            it.draw(cvs)
        }
        this.setImageBitmap(bitmap)
        oval = RectF()
        setupPaints()
        clearBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false)
        setup = true
    }

    private fun setupPaints() {
        paint = Paint()
        arcPaint = Paint()
        textPaint = Paint()
        stkPaint = Paint()
        paint.apply {
            isAntiAlias = true
            isDither = true
        }
        arcPaint.apply {
            isDither = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            color = resources.getColor(R.color.windowBlindsSensor, null)
            strokeWidth = (SENSOR_BORDER_SIZE - SENSOR_SIZE) * this@HomeLayoutView.height * 2
            isAntiAlias = true
        }
        textPaint.apply {
            isAntiAlias = true
            isDither = true
            color = resources.getColor(R.color.text, null)
            textSize = SENSOR_TEXT_SIZE * this@HomeLayoutView.height
            typeface = Typeface.DEFAULT_BOLD
        }
        stkPaint.apply {
            stkPaint.style = Paint.Style.STROKE
            stkPaint.strokeWidth = 8f
            stkPaint.color = ContextCompat.getColor(context, R.color.colorPrimary)
            stkPaint.textSize = SENSOR_TEXT_SIZE * this@HomeLayoutView.height
            stkPaint.isAntiAlias = true
            typeface = Typeface.DEFAULT_BOLD
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (!setup) {
            setupView()
        }
        paint.color = ContextCompat.getColor(context, R.color.backgroundColor)
        cvs.drawRect(0f, 0f, this.width.toFloat(), this.height.toFloat(), paint)
        cvs.drawBitmap(clearBitmap, 0f, 0f, null)
        sensList.filter {
            it.mapPosition != null && it.id != sensorPendingToDelete?.id
        }.forEach {
            drawSensor(
                percentToCoordinateX(it.mapPosition!!.x, this.width),
                percentToCoordinateY(it.mapPosition.y, this.height),
                it
            )
        }
        sensorPendingToPost?.let {
            drawSensor(
                percentToCoordinateX(sensorPendingToPostX, this.width),
                percentToCoordinateY(sensorPendingToPostY, this.height),
                it
            )
        }
        this.setImageBitmap(bitmap)
    }

    private fun setPaintAlpha(id: String, x: Float, y: Float) {
        if (id == sensorPendingToMove?.id
            && (x < percentToCoordinateX(
                sensorPendingToPostX,
                this.width
            ) - SENSOR_BORDER_SIZE * this.height
                    || x > percentToCoordinateX(
                sensorPendingToPostX,
                this.width
            ) + SENSOR_BORDER_SIZE * this.height)
            && (y < percentToCoordinateY(
                sensorPendingToPostY,
                this.height
            ) - SENSOR_BORDER_SIZE * this.height
                    || y > percentToCoordinateY(
                sensorPendingToPostY,
                this.height
            ) + SENSOR_BORDER_SIZE * this.height)
        ) {
            paint.alpha = (255 * sensorAlpha).toInt()
            arcPaint.alpha = (255 * sensorAlpha).toInt()
            stkPaint.alpha = (255 * sensorAlpha).toInt()
            textPaint.alpha = (255 * sensorAlpha).toInt()
        } else {
            paint.alpha = 255
            arcPaint.alpha = 255
            stkPaint.alpha = 255
            textPaint.alpha = 255
        }
    }

    fun setData(sensList: List<DashboardSensor>) {
        sensList.find {
            it.id == sensorPendingToPost?.id
        }?.let {
            if (it.mapPosition != null) {
                if ((percentToCoordinateX(it.mapPosition.x, this.width) < percentToCoordinateX(
                        sensorPendingToPostX,
                        this.width
                    ) + SENSOR_BORDER_SIZE * this.height
                            && percentToCoordinateX(
                        it.mapPosition.x,
                        this.width
                    ) > percentToCoordinateX(
                        sensorPendingToPostX,
                        this.width
                    ) - SENSOR_BORDER_SIZE * this.height)
                    && (percentToCoordinateY(it.mapPosition.y, this.height) < percentToCoordinateY(
                        sensorPendingToPostY,
                        this.height
                    ) + SENSOR_BORDER_SIZE * this.height
                            && percentToCoordinateY(
                        it.mapPosition.y,
                        this.height
                    ) > percentToCoordinateY(
                        sensorPendingToPostY,
                        this.height
                    ) - SENSOR_BORDER_SIZE * this.height)
                ) {
                    sensorPendingToPost = null
                    sensorPendingToMove = null
                    sensorPendingToDelete = null
                    sensorPendingToPostX = 0f
                    sensorPendingToPostY = 0f
                }
            }
        }
        this.sensList.clear()
        this.sensList.addAll(sensList)
    }

    private fun drawSensorBorder(x: Float, y: Float, sensor: DashboardSensor) {
        if (sensor.type == SensorType.RGB_LIGHT.type) {
            paint.color = sensor.details.toInt()
        } else {
            paint.color = resources.getColor(R.color.colorAccentLightDark, null)
            if (sensor.type == SensorType.SMOKE_SENSOR.type && smokeSensorBlink && sensor.details == "true") {
                paint.color = resources.getColor(R.color.alert, null)
            }
        }
        cvs.drawCircle(x, y, SENSOR_BORDER_SIZE * this.height, paint)
        if (sensor.type == SensorType.WINDOW_BLIND.type) {
            oval.set(
                x - SENSOR_SIZE * this.height,
                y - SENSOR_SIZE * this.height,
                x + SENSOR_SIZE * this.height,
                y + SENSOR_SIZE * this.height
            )
            paint.color = Color.TRANSPARENT
            cvs.drawOval(oval, paint)
            cvs.drawArc(oval, 90f, sensor.details.toFloat() / 100f * 360f, true, arcPaint)
        }
    }

    private fun drawSensorIcon(x: Float, y: Float, sensor: DashboardSensor) {
        val drawable = SensorType.values().find {
            it.type == sensor.type
        }?.getDrawable(sensor, resources)
        if (drawable != null) {
            drawable.setBounds(
                (x - SENSOR_ICON_SIZE * this.height).toInt(),
                (y - SENSOR_ICON_SIZE * this.height).toInt(),
                (x + SENSOR_ICON_SIZE * this.height).toInt(),
                (y + SENSOR_ICON_SIZE * this.height).toInt()
            )
            drawable.setTint(resources.getColor(R.color.text, null))
            if (sensor.type == SensorType.SMOKE_SENSOR.type && smokeSensorBlink && sensor.details == "true") {
                drawable.setTint(resources.getColor(R.color.alert, null))
            }
            if (System.currentTimeMillis() - smokeSensorBlinkStart > SMOKE_SENSOR_BLINK_INTERVAL) {
                smokeSensorBlinkStart = System.currentTimeMillis()
                smokeSensorBlink = !smokeSensorBlink
            }
            if (sensor.id == sensorPendingToMove?.id && (x < percentToCoordinateX(
                    sensorPendingToPostX,
                    this.width
                ) - SENSOR_BORDER_SIZE * this.height
                        || x > percentToCoordinateX(
                    sensorPendingToPostX,
                    this.width
                ) + SENSOR_BORDER_SIZE * this.height)
                && (y < percentToCoordinateY(
                    sensorPendingToPostY,
                    this.height
                ) - SENSOR_BORDER_SIZE * this.height
                        || y > percentToCoordinateY(
                    sensorPendingToPostY,
                    this.height
                ) + SENSOR_BORDER_SIZE * this.height)
            ) {
                drawable.alpha = (255 * sensorAlpha).toInt()
            }
            drawable.draw(cvs)
        }
        if (sensor.type == SensorType.TEMPERATURE_SENSOR.type) {
            cvs.drawText(
                sensor.details + DEGREE_CHAR,
                x - textPaint.measureText(sensor.details) / 2,
                (y - (textPaint.descent() + textPaint.ascent()) / 2) + SENSOR_SIZE * this.height,
                stkPaint
            )
            cvs.drawText(
                sensor.details + DEGREE_CHAR,
                x - textPaint.measureText(sensor.details) / 2,
                (y - (textPaint.descent() + textPaint.ascent()) / 2) + SENSOR_SIZE * this.height,
                textPaint
            )
        }
    }

    private fun drawSensor(x: Float, y: Float, sensor: DashboardSensor) {
        setPaintAlpha(sensor.id, x, y)
        drawSensorBorder(x, y, sensor)
        paint.color = resources.getColor(R.color.colorPrimary, null)
        cvs.drawCircle(x, y, SENSOR_SIZE * this.height, paint)
        drawSensorIcon(x, y, sensor)
        this.setImageBitmap(bitmap)
    }

    fun checkForSensors(x: Float, y: Float): Boolean {
        val distance = SENSOR_DISTANCE * this.height
        if (!sensList.isNullOrEmpty()) {
            sensList.filter {
                it.mapPosition != null && it.id != sensorPendingToMove?.id
            }.forEach {
                val sensorCoordX = percentToCoordinateX(it.mapPosition!!.x, this.width)
                val sensorCoordY = percentToCoordinateY(it.mapPosition.y, this.height)
                if (sensorCoordX >= x - distance && sensorCoordX <= x + distance && sensorCoordY >= y - distance && sensorCoordY <= y + distance)
                    return false
            }
        }
        return true
    }

    fun findSensor(x: Float, y: Float): DashboardSensor? {
        val sensor = sensList.filter {
            it.mapPosition != null
        }.find {
            x <= percentToCoordinateX(
                it.mapPosition!!.x,
                this.width
            ) + SENSOR_BORDER_SIZE * this.height && x >= percentToCoordinateX(
                it.mapPosition.x,
                this.width
            ) - SENSOR_BORDER_SIZE * this.height && y <= percentToCoordinateY(
                it.mapPosition.y,
                this.height
            ) + SENSOR_BORDER_SIZE * this.height && y >= percentToCoordinateY(
                it.mapPosition.y,
                this.height
            ) - SENSOR_BORDER_SIZE * this.height
        }
        return sensor
    }

}