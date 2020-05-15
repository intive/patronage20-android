package com.intive.patronage.smarthome.feature.light.view

import android.content.Context
import android.graphics.*
import android.view.View
import androidx.core.content.ContextCompat
import com.intive.patronage.smarthome.R

private const val RADIUS = 360f

class ColorPicker(context: Context): View(context) {
    private val colors = intArrayOf(Color.RED, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED)
    private val positions = floatArrayOf(0.000f, 0.166f, 0.333f, 0.499f, 0.666f, 0.833f, 0.999f)

    private val colorWheelPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        shader = SweepGradient(RADIUS, RADIUS, colors, positions)
    }

    private val whiteOverlayPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        shader = RadialGradient(RADIUS, RADIUS, RADIUS, Color.WHITE, ContextCompat.getColor(context, R.color.transparent), Shader.TileMode.CLAMP)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawCircle(RADIUS, RADIUS, RADIUS, colorWheelPaint)
        canvas?.drawCircle(RADIUS, RADIUS, RADIUS, whiteOverlayPaint)
    }
}