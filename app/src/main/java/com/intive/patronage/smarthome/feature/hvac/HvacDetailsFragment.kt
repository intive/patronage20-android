package com.intive.patronage.smarthome.feature.hvac

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.os.Bundle
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.intive.patronage.smarthome.R
import kotlinx.android.synthetic.main.fragent_hvac_details.*

class HvacDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val toolbar = (activity as AppCompatActivity).supportActionBar as ActionBar
        toolbar.title = resources.getString(R.string.hvac_details_appbar)
        toolbar.setDisplayHomeAsUpEnabled(true)

        return inflater.inflate(R.layout.fragent_hvac_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val hvacCircle = havacCircle
        temp_up.setOnClickListener { hvacCircle.teperaturaDodanie() }
        temp_down.setOnClickListener { hvacCircle.temperaturaMinus() }
        hist_up.setOnClickListener { hvacCircle.histUp() }
        hist_down.setOnClickListener { hvacCircle.histDown() }
    }


}


class HvacCircle @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var temperaturaFloat: Float = 22.5F
    private var hysteresis = 60

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

        Log.d("testowanie", "radius Temp = $radius , padding = $padding, withd = $width, height = $height")

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


    private fun drawCircleHysteresis(canvas: Canvas?, hysteresis: Int) {
        val histLabel = "Hysteresis"
        val histValueString = "$hysteresis %"
        val radius = if (width / 2 < height) width / 2 - padding * 2 else height / 2 - padding * 2

        // Log.d("testowanie","radius heig = $radius , padding = $padding, withd = $width, height = $height")

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


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


        Log.d("testowanie", "draw withd = $width, height = $height")
        drawCircleTemp(canvas, temperaturaFloat)
        drawCircleHysteresis(canvas, hysteresis)

    }

    fun teperaturaDodanie() {
        temperaturaFloat += 1
        postInvalidate()
    }

    fun temperaturaMinus() {
        temperaturaFloat -= 1
        postInvalidate()
    }

    fun histUp() {
        hysteresis += 1
        postInvalidate()
    }

    fun histDown() {
        hysteresis -= 1
        postInvalidate()
    }
}