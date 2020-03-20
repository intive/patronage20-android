package com.intive.patronage.smarthome.feature.blind.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.intive.patronage.smarthome.R

class BlindView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private lateinit var canvas: Canvas
    var position: Float = 0F
    var onePercent: Float = 0F
    private var maxPosition: Float = 0F
    private val canvasX = 65
    private var canvasY: Float = 0F
    private val frameSize: Float = 15F

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasY = h.toFloat() / 10
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            this.canvas = canvas

            maxPosition = height.toFloat() - 2 * canvasY - frameSize
            onePercent = maxPosition / 100

            drawWindow()
            drawBlind()
        }
    }

    private fun drawWindow() {
        val window = Rect()
        setRectangle(window, height - canvasY)

        val glass = Paint()
        setFillPaint(glass, R.color.glass)

        val frame = Paint()
        with(frame) {
            this.style = Paint.Style.STROKE
            this.strokeWidth = 2 * frameSize
            this.color = Color.WHITE
            this.isAntiAlias = true
            this.isDither = true
        }

        this.canvas.drawRect(window, frame)
        this.canvas.drawRect(window, glass)
    }

    private fun drawBlind() {
        val blind = Rect()
        setRectangle(blind, canvasY + position)

        val blindPaint = Paint()
        setFillPaint(blindPaint, R.color.colorAccent)

        val blindEnd = Rect()
        setRectangle(blindEnd, canvasY + position + frameSize)

        val blindEndPaint = Paint()
        setFillPaint(blindEndPaint, R.color.backgroundColor)

        this.canvas.drawRect(blindEnd, blindEndPaint)
        this.canvas.drawRect(blind, blindPaint)
    }

    private fun setRectangle(rect: Rect, height: Float) {
        with(rect) {
            this.left = canvasX
            this.top = canvasY.toInt()
            this.right = width - canvasX
            this.bottom = (height).toInt()
        }
    }

    private fun setFillPaint(paint: Paint, color: Int) {
        with(paint) {
            this.style = Paint.Style.FILL
            this.color = resources.getColor(color)
            this.isAntiAlias = true
            this.isDither = true
        }
    }

    fun blindUp() {
        if (position >= onePercent) position -= onePercent
        else position = 0F
    }

    fun blindDown() {
        if (position < (maxPosition - onePercent)) position += onePercent
        else position = maxPosition
    }

    fun changePosition(currentY: Float): Int {
        if (currentY - canvasY - frameSize in 0.0..maxPosition.toDouble()) {
            position = currentY - canvasY - frameSize
        } else if (currentY - canvasY - frameSize < 0) {
            position = 0F
        } else {
            position = maxPosition
        }
        return (position / onePercent).toInt()
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}
