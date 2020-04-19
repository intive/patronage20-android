package com.intive.patronage.smarthome.feature.light.view

import android.content.Context
import android.graphics.*
import android.view.View
import androidx.core.content.ContextCompat
import com.intive.patronage.smarthome.R

class BrightnessSeekBar(context: Context): View(context) {
    private var red = 0
    private var green = 0
    private var blue = 0

    val brightnessPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.rgb(red, green, blue)
    }

    private val brightnessRect = Rect(0, 0, 300, 100)

    private val blackOverlayPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        shader = LinearGradient(0f, 0f, 300f, 0f, Color.BLACK, ContextCompat.getColor(context, R.color.transparent), Shader.TileMode.CLAMP)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRect(brightnessRect, brightnessPaint)
        canvas?.drawRect(brightnessRect, blackOverlayPaint)
    }
}