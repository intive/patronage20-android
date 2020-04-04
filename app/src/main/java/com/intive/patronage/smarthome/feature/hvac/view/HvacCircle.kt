package com.intive.patronage.smarthome.feature.hvac.view

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import androidx.core.content.ContextCompat
import com.intive.patronage.smarthome.R
import kotlin.math.*


class HvacCircle(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val rangeOffset = 0
    private val range = 35 - rangeOffset
    var temperatureFloat: Float = 0F
    var hysteresis = 0
    private var mWidth = 0
    private var mHeight = 0
    private var radius = 0
    private var coldSweepAngle = 0f
    private var hotSweepAngle = 0f
    private val startAngle = 140f
    private val sweepAngle = 260f
    private val tempOffset = 40f
    private var oneDegree = sweepAngle / range
    var minTemperature = 0
    var maxTemperature = 0
    private var coldCircleRadius = 20f
    private var hotCircleRadius = 20f
    private var coldTouched = false
    private var hotTouched = false
    private lateinit var coldPoint: Point
    private lateinit var hotPoint: Point
    private lateinit var coldCircle: RectF
    private lateinit var hotCircle: RectF
    private var touchRadius = 100.0
    private var heightOffset = 0
    private var widthOffset = 0
    private var metrics: DisplayMetrics = resources.displayMetrics
    private val paint = Paint().apply {
        isAntiAlias = true
        color = ContextCompat.getColor(context!!, R.color.colorAccent)
        style = Paint.Style.STROKE
        strokeWidth = 20f
    }
    private val coldPaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLUE
        strokeWidth = 20f
    }
    private val hotPaint = Paint().apply {
        isAntiAlias = true
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 20f
    }
    private val paintBackground = Paint().apply {
        isAntiAlias = true
        color = ContextCompat.getColor(context!!, R.color.splashScreenProgressbar)
        style = Paint.Style.STROKE
        strokeWidth = 20f
    }
    private val textPaint = TextPaint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
        textSize = 70f
    }
    private val textLabelPaint = TextPaint().apply {
        isAntiAlias = true
        color = ContextCompat.getColor(context!!, R.color.colorAccent)
        style = Paint.Style.FILL_AND_STROKE
        textSize = 50f
    }

    private lateinit var centerOfTemperatureCircle: Point
    private lateinit var centerOfHysteresisCircle: Point

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setTextSizeFromDPI()
        if (width < height) {
            radius = width / 2 + 20 - widthOffset
            mWidth = width
            mHeight = height
            centerOfTemperatureCircle = Point().apply {
                x = width / 2
                y = height / 4
            }
            centerOfHysteresisCircle = Point().apply {
                x = width / 2
                y = height - (height / 4)
            }

        } else {
            radius = height / 2 + 150 - heightOffset
            mWidth = height
            mHeight = width
            centerOfTemperatureCircle = Point().apply {
                x = width / 4
                y = height / 2
            }
            centerOfHysteresisCircle = Point().apply {
                x = (width - (width / 4))
                y = height / 2
            }

        }
        postInvalidate()
    }

    private fun drawCircleTemp(canvas: Canvas?, tempInt: Float) {
        val tempLabel = resources.getString(R.string.hvac_temperature_label)
        val tempString = "$tempInt C°"

        val tempCircle = RectF().apply {
            top = centerOfTemperatureCircle.y - radius / 2f
            bottom = centerOfTemperatureCircle.y + radius / 2f
            left = centerOfTemperatureCircle.x - radius / 2f
            right = centerOfTemperatureCircle.x + radius / 2f

        }
        coldCircle = RectF(tempCircle)
        hotCircle = RectF(tempCircle)

        coldPoint = getPoint(coldCircle.centerX(), coldCircle.centerY(), radius / 2, startAngle + coldSweepAngle)
        hotPoint = getPoint(hotCircle.centerX(), hotCircle.centerY(), radius / 2, tempOffset + (hotSweepAngle * (-1)))
        coldSweepAngle = minTemperature.toFloat() / 10 * oneDegree
        hotSweepAngle = (range * 10 - maxTemperature).toFloat() / 10 * oneDegree

        catchTemperature()
        canvas?.drawArc(tempCircle, startAngle, sweepAngle, false, paint)
        canvas?.drawArc(coldCircle, startAngle, coldSweepAngle, false, coldPaint.apply { style = Paint.Style.STROKE })
        canvas?.drawArc(hotCircle, tempOffset, (hotSweepAngle * (-1)), false,
            hotPaint.apply { style = Paint.Style.STROKE })

        canvas?.drawCircle(
            coldPoint.x.toFloat(),
            coldPoint.y.toFloat(),
            coldCircleRadius,
            coldPaint.apply {
                textSize = 20f
                style = Paint.Style.FILL
            })
        canvas?.drawCircle(
            hotPoint.x.toFloat(),
            hotPoint.y.toFloat(),
            hotCircleRadius,
            hotPaint.apply { style = Paint.Style.FILL })


        canvas?.drawText(tempString,
            centerOfTemperatureCircle.x - textPaint.measureText(tempString) / 2,
            centerOfTemperatureCircle.y + textPaint.textSize / 2,
            textPaint.apply { color = ContextCompat.getColor(context!!, R.color.colorAccent) }
        )

        val offsetLength: Float = ((90f / 360f) * 2 * Math.PI * (radius / 2f)).toFloat()
        canvas?.drawTextOnPath(
            tempLabel,
            Path().apply { addArc(tempCircle, 135f, -90f) },
            offsetLength / 2 - textLabelPaint.measureText(tempLabel) / 2,
            textLabelPaint.textSize / 2,
            textLabelPaint
        )
    }

    private fun drawMinTemperatureLabel(canvas: Canvas?) {
        val text = (minTemperature.toFloat() / 10).toString()
        val minTempLabel = "Min Temp"
        val tempString = "$text C°"
        if (width < height) {
            canvas?.drawText(
                tempString,
                textPaint.measureText(minTempLabel) / 2 - textPaint.measureText(tempString) / 2,
                height / 2 + textPaint.textSize,
                textPaint
            )
            canvas?.drawText(
                minTempLabel,
                0F,
                height / 2 - textPaint.textSize / 2,
                textPaint
            )
        } else {
            canvas?.drawText(
                tempString,
                width / 2f - textPaint.measureText(tempString) / 2,
                height.toFloat(),
                textPaint
            )
            canvas?.drawText(
                minTempLabel,
                width / 2f - textPaint.measureText(minTempLabel) / 2,
                height - textPaint.textSize,
                textPaint
            )
        }

    }

    private fun drawMaxTemperatureLabel(canvas: Canvas?) {
        val text = ((maxTemperature).toFloat() / 10).toString()
        val tempLabel = "Max Temp"
        val tempString = "$text C°"
        if (width < height) {
            canvas?.drawText(
                tempString,
                width.toFloat() - textPaint.measureText(tempLabel) / 2 - textPaint.measureText(tempString) / 2,
                height / 2 + textPaint.textSize,
                textPaint
            )
            canvas?.drawText(
                tempLabel,
                width.toFloat() - textPaint.measureText(tempLabel),
                height / 2 - textPaint.textSize / 2,
                textPaint
            )
        } else {
            canvas?.drawText(
                tempString,
                width / 2f - textPaint.measureText(tempString) / 2,
                textPaint.textSize * 2,
                textPaint
            )
            canvas?.drawText(
                tempLabel,
                width / 2f - textPaint.measureText(tempLabel) / 2,
                textPaint.textSize,
                textPaint
            )
        }
    }

    private fun drawCircleHysteresis(canvas: Canvas?, hysteresis: Int) {

        val histLabel = resources.getString(R.string.hvac_hysteresis_label)
        val histValueString = "$hysteresis %"

        val circle = RectF().apply {
            top = centerOfHysteresisCircle.y - radius / 2f
            bottom = centerOfHysteresisCircle.y + radius / 2f
            left = centerOfHysteresisCircle.x - radius / 2f
            right = centerOfHysteresisCircle.x + radius / 2f
        }

        val sweepAngle = hysteresis * 2.7F


        canvas?.drawArc(circle, 180f, 270f, false, paintBackground)
        canvas?.drawArc(circle, 180f, sweepAngle, false, paint)
        canvas?.drawText(
            histValueString,
            centerOfHysteresisCircle.x - textPaint.measureText(histValueString) / 2,
            centerOfHysteresisCircle.y + textPaint.textSize / 2,
            textPaint
        )
        val offsetLength: Float = ((90f / 360f) * 2 * Math.PI * (radius / 2f)).toFloat()
        canvas?.drawTextOnPath(
            histLabel,
            Path().apply { addArc(circle, 180f, -90f) },
            offsetLength / 2 - textLabelPaint.measureText(histLabel) / 2,
            textLabelPaint.textSize / 2,
            textLabelPaint
        )
    }

    private fun getPoint(x: Float, y: Float, radius: Int, angle: Float): Point {
        val angleInRadian = angle * (Math.PI / 180)
        val pointX = (x + radius * cos(angleInRadian)).roundToInt()
        val pointY = (y + radius * sin(angleInRadian)).roundToInt()
        return Point(pointX, pointY)
    }

    fun onTouch(eventX: Float, eventY: Float) {

        val cdx = (eventX - coldPoint.x.toDouble()).pow(2.0)
        val cdy = (eventY - coldPoint.y.toDouble()).pow(2.0)
        val hdx = (eventX - hotPoint.x.toDouble()).pow(2.0)
        val hdy = (eventY - hotPoint.y.toDouble()).pow(2.0)
        val angEvent =
            angleCounter(eventX.toInt(), eventY.toInt(), centerOfTemperatureCircle.x, centerOfTemperatureCircle.y)
        val eventAngleDegrees = Math.toDegrees(asin(angEvent))

        if (cdx + cdy < touchRadius.pow(2.0) && !hotTouched) {
            coldTouched = true
            coldCircleRadius = 30f
            touchRadius = 300.0
            coldSweepAngle = setColdAngle(eventAngleDegrees.toFloat() + tempOffset, eventX)
            minTemperature = ((coldSweepAngle / oneDegree) * 10).toInt()
            invalidate()
        }
        if (hdx + hdy < touchRadius.pow(2.0) && !coldTouched) {
            hotTouched = true
            hotCircleRadius = 30f
            touchRadius = 300.0
            hotSweepAngle = setHotAngle(eventAngleDegrees.toFloat() + tempOffset, eventX)
            maxTemperature = range * 10 - ((hotSweepAngle / oneDegree) * 10).toInt()
            invalidate()
        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hysteresis = hysteresisCalculation()
        setTextSizeFromDPI()
        drawCircleTemp(canvas, temperatureFloat)
        drawCircleHysteresis(canvas, hysteresis)
        drawMinTemperatureLabel(canvas)
        drawMaxTemperatureLabel(canvas)
    }

    fun reset() {
        coldTouched = false
        hotTouched = false
        hotCircleRadius = 20f
        touchRadius = 100.0
        coldCircleRadius = 20f
        invalidate()
    }

    private fun angleCounter(pointX: Int, pointY: Int, centerX: Int, centerY: Int): Double {
        val distanceX = pointX - centerX
        val distanceY = centerY - pointY
        val r = (distanceX.toDouble()).pow(2.0) + distanceY.toDouble().pow(2.0)
        return distanceY / sqrt(r)

    }

    private fun setHotAngle(angle: Float, evenX: Float): Float {
        var value = 0f
        when {
            angle in 0.1..130.0 && evenX >= centerOfTemperatureCircle.x -> {
                value = angle
            }
            evenX < centerOfTemperatureCircle.x -> {
                value = 129.0f
            }
            angle < 0.1 -> {
                value = 0.1f
            }
        }
        return value
    }

    private fun setColdAngle(angle: Float, evenX: Float): Float {
        var value = 0f
        when {
            angle in 0.1..129.5 && evenX <= centerOfTemperatureCircle.x -> {
                value = angle
            }
            evenX > centerOfTemperatureCircle.x -> {
                value = 129.0f
            }
            angle < 0.1 -> {
                value = 0.1f
            }
        }
        return value
    }

    private fun hysteresisCalculation(): Int {
        var value = 0
        val maxValue = maxTemperature - minTemperature
        val currentValue = ((temperatureFloat * 10).toInt()) - minTemperature
        val percent = if (maxValue > 0) {
            currentValue * 100 / maxValue
        } else 100

        when {
            percent in 100 downTo 1 -> {
                value = percent
            }
            percent > 100 -> {
                value = 100
            }
            percent < 0 -> {
                value = 0
            }
        }
        return value
    }

    private fun setTextSizeFromDPI() {
        when (metrics.densityDpi) {
            in 1..160 -> {
                textPaint.textSize = 12f
                textLabelPaint.textSize = 15f
                coldPaint.strokeWidth = 6f
                hotPaint.strokeWidth = 6f
                paintBackground.strokeWidth = 6f
                paint.strokeWidth = 6f
                hotCircleRadius = 6f
                coldCircleRadius = 6f
                touchRadius = 20.0
                heightOffset = 110
                widthOffset = 30
            }
            in 161..240 -> {
                textPaint.textSize = 25f
                textLabelPaint.textSize = 25f
                coldPaint.strokeWidth = 10f
                hotPaint.strokeWidth = 10f
                paintBackground.strokeWidth = 10f
                paint.strokeWidth = 10f
                hotCircleRadius = 10f
                coldCircleRadius = 10f
                touchRadius = 50.0
                heightOffset = 60
            }
            320 -> {
                textPaint.textSize = 45f
                textLabelPaint.textSize = 40f
            }
        }
    }

    private fun catchTemperature() {
        if (coldSweepAngle < 1) {
            coldSweepAngle = 1F
        }
        if (hotSweepAngle < 1) {
            hotSweepAngle = 1f
        }
    }


}