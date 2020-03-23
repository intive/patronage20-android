package com.intive.patronage.smarthome.feature.hvac

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.intive.patronage.smarthome.R
import org.koin.ext.getScopeName


class HvacCircle(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var temperatureFloat: Float = 0F
    var hysteresis = 60F
    private val paint = Paint().apply {
        isAntiAlias = true
        color = resources.getColor(R.color.colorAccent)
        style = Paint.Style.STROKE
        strokeWidth = 20f
    }
    private val paintBackground = Paint().apply {
        isAntiAlias = true
        color = resources.getColor(R.color.colorPrimary)
        style = Paint.Style.STROKE
        strokeWidth = 20f
    }
    private val textPaint = TextPaint().apply {
        isAntiAlias = true
        color = resources.getColor(R.color.colorAccent)
        style = Paint.Style.STROKE
        textSize = 70f
    }
    private val textLabelPaint = TextPaint().apply {
        isAntiAlias = true
        color = resources.getColor(R.color.colorAccent)
        style = Paint.Style.STROKE
        textSize = 50f
    }
    private val padding = 20f

    private fun drawCircleTemp(canvas: Canvas?, tempInt: Float) {
        val tempLabel = "Temperatura"
        val tempString = "$tempInt C°"

        val radius = if (width / 2 < height) width / 2f - padding * 2 else height / 2f - padding * 2

        val circle = RectF().apply {
            top = height / 2 - radius / 2
            left = width / 4 - radius / 2
            right = left + radius
            bottom = top + radius
        }

        val sweepAngle = 45 + tempInt * 4.5F


        canvas?.drawArc(circle, 180f, 270f, false, paintBackground)
        canvas?.drawArc(circle, 180f, sweepAngle, false, paint)
        canvas?.drawText(
            tempString,
            width / 4f - textPaint.measureText(tempString) / 2,
            height / 2f + textPaint.textSize / 2,
            textPaint
        )
        canvas?.drawTextOnPath(
            tempLabel,
            Path().apply { addArc(circle, 180f, -90f) },
            20f,
            20f,
            textLabelPaint
        )
    }

    private fun drawCircleHysteresis(canvas: Canvas?, hysteresis: Float) {
        val histLabel = "Hysteresis"
        val histValueString = "$hysteresis %"
        val radius = if (width / 2 < height) width / 2 - padding * 2 else height / 2 - padding * 2

        val circle = RectF().apply {
            top = height / 2 - radius / 2
            left = width * 0.75f - radius / 2f
            right = left + radius
            bottom = top + radius
        }

        val sweepAngle = hysteresis * 2.7F

        canvas?.drawArc(circle, 180f, 270f, false, paintBackground)
        canvas?.drawArc(circle, 180f, sweepAngle, false, paint)
        canvas?.drawText(
            histValueString,
            width * 0.75f - textPaint.measureText(histValueString) / 2,
            height / 2f + textPaint.textSize / 2,
            textPaint
        )
        canvas?.drawTextOnPath(
            histLabel,
            Path().apply { addArc(circle, 180f, -90f) },
            60f,
            20f,
            textLabelPaint
        )

    }

    fun changeTemperature (temp:Float){
        temperatureFloat = temp
        postInvalidate()

    }

    override fun onDraw(canvas: Canvas?) {

        drawCircleTemp(canvas, temperatureFloat)
        drawCircleHysteresis(canvas, hysteresis)
    }

}