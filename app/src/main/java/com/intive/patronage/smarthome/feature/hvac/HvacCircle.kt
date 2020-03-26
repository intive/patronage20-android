package com.intive.patronage.smarthome.feature.hvac

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.intive.patronage.smarthome.R
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sin


class HvacCircle(context: Context?, attrs: AttributeSet?) : View(context, attrs){


    var temperatureFloat: Float = 0F
    var hysteresis = 0F
    private var mWidth = 0
    private var mHeight =0
    private var radius =0
    private var coldSweepAngle = 0f
    private var hotSweepAngle = 0f
    private val startAngle = 160f
    var minTemperature = 0
    var maxTemperature = 0
    private var coldCircleRadius = 20f
    private var hotCircleRadius = 20f
    private var coldTouched = false
    private var hotTouched = false
    private lateinit var coldPoint : Point
    private lateinit var hotPoint: Point
    private lateinit var coldCircle : RectF
    private lateinit var hotCircle : RectF
    private var oneDegree = 6.2f
    private val sweepAngle = 220f
    private var touchRadius = 20.0

    private val paint = Paint().apply {
        isAntiAlias = true
        color = resources.getColor(R.color.colorAccent)
        style = Paint.Style.STROKE
        strokeWidth = 20f
    }
    private val coldPaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLUE
        style = Paint.Style.STROKE
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
        color = resources.getColor(R.color.colorPrimary)
        style = Paint.Style.STROKE
        strokeWidth = 20f
    }
    private val textPaint = TextPaint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        textSize = 70f
    }
    private val textLabelPaint = TextPaint().apply {
        isAntiAlias = true
        color = resources.getColor(R.color.colorAccent)
        style = Paint.Style.STROKE
        textSize = 50f
    }

    private lateinit var centerOfTemperatureCircle : Point
    private lateinit var centerOfHysteresisCircle :Point

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (width < height) {
            radius = width/2 +20
            mWidth = width
            mHeight = height
            centerOfTemperatureCircle = Point().apply { x= width/2
            y=height/4 }
            centerOfHysteresisCircle = Point().apply { x=width/2
            y = height - (height/4)}

        } else{
            radius = height/2 + 150
            mWidth = height
            mHeight = width
            centerOfTemperatureCircle = Point().apply{ x = width/4
            y=height/2 }
            centerOfHysteresisCircle = Point().apply { x =(width - (width/4))
            y=height/2}

        }
    }

    private fun drawCircleTemp(canvas: Canvas?, tempInt: Float) {
        val tempLabel = resources.getString(R.string.hvac_temperature_label)
        val tempString = "$tempInt C°"


        val tempCircle = RectF().apply {
            top = centerOfTemperatureCircle.y - radius /2f
            bottom = centerOfTemperatureCircle.y + radius /2f
            left = centerOfTemperatureCircle.x  -radius /2f
            right = centerOfTemperatureCircle.x + radius /2f

        }
        coldCircle = RectF(tempCircle)
        hotCircle = RectF(tempCircle)



        coldPoint = getPoint(coldCircle.centerX(), coldCircle.centerY(), radius/2,startAngle+coldSweepAngle )
        hotPoint = getPoint(hotCircle.centerX(), hotCircle.centerY(), radius/2,20+(hotSweepAngle*(-1)) )
        coldSweepAngle = minTemperature.toFloat()/10*oneDegree
        hotSweepAngle = (350 -maxTemperature).toFloat()/10*oneDegree

        canvas?.drawArc(tempCircle, startAngle, sweepAngle, false, paint)
        canvas?.drawArc(coldCircle,startAngle,coldSweepAngle,false,coldPaint)
        canvas?.drawArc(hotCircle,20f,(hotSweepAngle*(-1)),false,hotPaint)
        canvas?.drawCircle(coldPoint.x.toFloat(), coldPoint.y.toFloat(),coldCircleRadius, coldPaint.apply { textSize =20f })
        canvas?.drawCircle(hotPoint.x.toFloat(), hotPoint.y.toFloat(),hotCircleRadius, hotPaint)


        canvas?.drawText(tempString,
            centerOfTemperatureCircle.x- textPaint.measureText(tempString) / 2,
            centerOfTemperatureCircle.y + textPaint.textSize / 2,
            textPaint.apply { color = resources.getColor(R.color.colorAccent) }
        )

        canvas?.drawTextOnPath(
            tempLabel,
            Path().apply { addArc(tempCircle, 130f, -90f) },
            20f,
            20f,
            textLabelPaint
        )
    }

    private fun drawMinTemperatureLabel (canvas: Canvas?){
        val text = (minTemperature.toFloat()/10).toString()
        val minTempLabel = "Min Temp"
        val tempString = "$text C°"
        if(width<height) {
            canvas?.drawText(
                tempString,
                40F,
                height / 2 + textPaint.textSize,
                textPaint
            )
            canvas?.drawText(
                minTempLabel,
                0F,
                height / 2 - textPaint.textSize / 2,
                textPaint
            )
        }else{
            canvas?.drawText(
                tempString,
                width/2f - textPaint.measureText(tempString) / 2,
                height.toFloat(),
                textPaint
            )
            canvas?.drawText(
                minTempLabel,
                width/2f - textPaint.measureText(minTempLabel) / 2,
                height - textPaint.textSize,
                textPaint
            )
        }

    }

    private fun drawMaxTemperatureLabel(canvas: Canvas?){
        val text = ((maxTemperature).toFloat()/10).toString()
        val tempLabel = "Max Temp"
        val tempString = "$text C°"
        if(width<height) {
            canvas?.drawText(
                tempString,
                width.toFloat() - textPaint.measureText(tempString),
                height / 2 + textPaint.textSize,
                textPaint
            )
            canvas?.drawText(
                tempLabel,
                width.toFloat() - textPaint.measureText(tempLabel),
                height / 2 - textPaint.textSize / 2,
                textPaint
            )
        }else{
            canvas?.drawText(
                tempString,
                width/2f - textPaint.measureText(tempString) / 2,
                textPaint.textSize*2,
                textPaint
            )
            canvas?.drawText(
                tempLabel,
                width/2f - textPaint.measureText(tempLabel) / 2,
                textPaint.textSize,
                textPaint
            )
        }
    }

    private fun drawCircleHysteresis(canvas: Canvas?, hysteresis: Float) {

        val histLabel = resources.getString(R.string.hvac_hysteresis_label)
        val histValueString = "$hysteresis %"

        val circle = RectF().apply {
            top = centerOfHysteresisCircle.y - radius /2f
            bottom = centerOfHysteresisCircle.y + radius /2f
            left = centerOfHysteresisCircle.x  -radius /2f
            right = centerOfHysteresisCircle.x + radius /2f
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
        canvas?.drawTextOnPath(
            histLabel,
            Path().apply { addArc(circle, 180f, -90f) },
            60f,
            20f,
            textLabelPaint
        )
    }

    private fun getPoint (x:Float, y:Float, radius:Int, angle:Float):Point{
        val angleInRadian = angle*(Math.PI/180)
        val pointX = (x + radius * cos(angleInRadian)).roundToInt()
        val pointY = (y + radius * sin(angleInRadian)).roundToInt()
        return Point(pointX, pointY)
    }

    fun onTouch(eventX:Float, eventY:Float){

        /*val x = eventX
        val y = eventY*/
        val cdx = (eventX - coldPoint.x.toDouble()).pow(2.0)
        val cdy = (eventY - coldPoint.y.toDouble()).pow(2.0)
        val hdx = (eventX - hotPoint.x.toDouble()).pow(2.0)
        val hdy = (eventY - hotPoint.y.toDouble()).pow(2.0)


        if(cdx+cdy< touchRadius.pow(2.0) && !hotTouched) {
            coldTouched = true
            coldCircleRadius = 30f
            touchRadius=300.0
            if(coldPoint.x < eventX){
                minTemperature+= 1
            }
            else minTemperature-= 1
            invalidate()
        }
        if(hdx+hdy< touchRadius.pow(2.0) && !coldTouched) {
            hotTouched = true
            hotCircleRadius = 30f
            touchRadius=300.0
            if(hotPoint.x > eventX){
                maxTemperature-= 1
            }
            else maxTemperature+= 1
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawCircleTemp(canvas, temperatureFloat)
        drawCircleHysteresis(canvas, hysteresis)
        drawMinTemperatureLabel(canvas)
        drawMaxTemperatureLabel(canvas)

    }

    fun reset (){
        coldTouched =false
        hotTouched =false
        hotCircleRadius = 20f
        touchRadius =20.0
        coldCircleRadius = 20f
        invalidate()
    }



}