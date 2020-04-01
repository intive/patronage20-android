package com.intive.patronage.smarthome.feature.temperature.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.intive.patronage.smarthome.R

class GraphView(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    private var distanceBetweenLines: Int = 1
    private var distanceBetweenTextValues: Int = 1
    private var elementsWidth: Float = 8f

    private val data = mutableListOf<Point>()
    private lateinit var canvas: Canvas
    private var minValue: Int = 0
    private var maxValue: Int = 0
    private var degree: Float = 0f
    private var centerLineY: Float = 0f
    private var centerValue: Int = 0

    private var path = Path()
    private val connectionPoints1 = mutableListOf<PointF>()
    private val connectionPoints2 = mutableListOf<PointF>()

    fun setData(newData: List<Point>, distanceBetweenLines: Int, distanceBetweenTextValues: Int, elementsWidth: Float) {
        data.clear()
        connectionPoints1.clear()
        connectionPoints2.clear()
        data.addAll(newData)

        this.distanceBetweenLines = distanceBetweenLines
        this.distanceBetweenTextValues = distanceBetweenTextValues
        this.elementsWidth = elementsWidth

        minValue = newData.minBy { it.y }?.y ?: 0
        minValue -= 2
        maxValue = newData.maxBy { it.y }?.y ?: 0
        maxValue += 2
        centerValue = (maxValue - minValue) / 2 + minValue

        invalidate()
    }

    private val point = Paint().apply {
        color = Color.WHITE
        isAntiAlias = true
    }

    private val fillPathPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.transparentAccentColor)
    }

    private val gridLinePaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.colorAccentLightDark)
        strokeWidth = elementsWidth / 2
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(canvas != null) {
            this.canvas = canvas

            degree = height.toFloat() / (maxValue - minValue)
            centerLineY = degree * (maxValue - centerValue)

            drawHorizontalGrid()
            drawTextValues()
            drawCurvedLines()
            fillBackgroundUnderLines()
            drawPoints()
        }
    }

    private fun drawCurvedLines() {
        val borderPathPaint = Paint().apply {
            isAntiAlias = true
            strokeWidth = elementsWidth
            style = Paint.Style.STROKE
            color = ContextCompat.getColor(context, R.color.colorAccent)
        }

        data.forEachIndexed { index, point ->
            var startX = (point.x.toFloat() - 1) * ((width - degree) / (data.size - 1)) + degree
            val startY = (maxValue - point.y.toFloat()) * degree

            if (index < data.size - 1) {
                val nextPoint = data[index + 1]
                var endX = (nextPoint.x.toFloat() - 1) * ((width - degree) / (data.size - 1)) + degree
                val endY = (maxValue - nextPoint.y.toFloat()) * degree

                val firstConnectionPoint = PointF(((endX + startX) / 2), startY)
                val secondConnectionPoint = PointF(((endX + startX) / 2), endY)
                connectionPoints1.add(firstConnectionPoint)
                connectionPoints2.add(secondConnectionPoint)

                path.reset()

                if (index == 0) startX += elementsWidth
                path.moveTo(startX, startY)

                if (index == data.size - 2) endX -= elementsWidth
                path.cubicTo(
                    firstConnectionPoint.x, firstConnectionPoint.y,
                    secondConnectionPoint.x, secondConnectionPoint.y,
                    endX, endY
                )

                canvas.drawPath(path, borderPathPaint)
            }
        }
    }

    private fun fillBackgroundUnderLines() {
        val fillPath = Path()

        fillPath.reset()
        fillPath.moveTo(degree + elementsWidth, height.toFloat())
        fillPath.lineTo((data[0].x.toFloat() - 1) * ((width - degree) / (data.size - 1)) + degree + elementsWidth, (maxValue - data[0].y.toFloat()) * degree)

        for (i in 1 until data.size) {
            if (i == data.size - 1) {
                fillPath.cubicTo(
                    connectionPoints1[i - 1].x, connectionPoints1[i - 1].y,
                    connectionPoints2[i - 1].x, connectionPoints2[i - 1].y,
                    (data[i].x.toFloat() - 1) * ((width - degree) / (data.size - 1)) + degree - elementsWidth, (maxValue - data[i].y.toFloat()) * degree
                )
            } else {
                fillPath.cubicTo(
                    connectionPoints1[i - 1].x, connectionPoints1[i - 1].y,
                    connectionPoints2[i - 1].x, connectionPoints2[i - 1].y,
                    (data[i].x.toFloat() - 1) * ((width - degree) / (data.size - 1)) + degree, (maxValue - data[i].y.toFloat()) * degree
                )
            }
        }

        fillPath.lineTo(width.toFloat() - elementsWidth, height.toFloat())

        canvas.drawPath(fillPath, fillPathPaint)
    }

    private fun drawPoints() {
        var distance = degree

        data.forEachIndexed { index, point ->
            if (index == 0) distance += elementsWidth
            else if (index == data.size - 1) distance -= elementsWidth

            canvas.drawCircle(
                (point.x.toFloat() - 1) * ((width - degree) / (data.size - 1)) + distance,
                (maxValue - point.y.toFloat()) * degree,
                elementsWidth,
                this.point
            )

            distance = degree
        }
    }

    private fun drawTextValues() {
        val text = Paint().apply {
            color = Color.WHITE
            isAntiAlias = true
            textSize = degree / 2
        }

        canvas.drawText(centerValue.toString(), 0f, centerLineY, text)

        var i = 1
        while ((centerLineY - (distanceBetweenTextValues * i * degree)) > 0) {
            canvas.drawText(
                (centerValue + distanceBetweenTextValues * i).toString(),
                0f,
                (centerLineY - (distanceBetweenTextValues * i * degree)),
                text
            )
            i++
        }

        i = 1
        while ((centerLineY + (distanceBetweenTextValues * i * degree)) < height.toFloat()) {
            canvas.drawText(
                (centerValue - distanceBetweenTextValues * i).toString(),
                0f,
                (centerLineY + (distanceBetweenTextValues * i * degree)),
                text
            )
            i++
        }
    }

    private fun drawHorizontalGrid() {
        canvas.drawLine(degree + elementsWidth, centerLineY, width.toFloat() - elementsWidth, centerLineY, gridLinePaint)

        var i = 1
        while ((centerLineY - (distanceBetweenLines * i * degree)) > 0) {
            canvas.drawLine(
                degree + elementsWidth,
                centerLineY - (distanceBetweenLines * i * degree),
                width.toFloat() - elementsWidth,
                centerLineY - (distanceBetweenLines * i * degree),
                gridLinePaint
            )
            i++
        }

        i = 1
        while ((centerLineY + (distanceBetweenLines * i * degree)) < height.toFloat()) {
            canvas.drawLine(
                degree + elementsWidth,
                centerLineY + (distanceBetweenLines * i * degree),
                width.toFloat() - elementsWidth,
                centerLineY + (distanceBetweenLines * i * degree),
                gridLinePaint
            )
            i++
        }
    }
}