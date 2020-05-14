package com.intive.patronage.smarthome.feature.light.view

import android.graphics.*
import android.graphics.drawable.Drawable

class BrightnessBarPointer(private val viewPadding: Float) : Drawable() {

    var x: Float = 0f
    var height: Float = 0f

    override fun draw(canvas: Canvas) {
        val pointerPaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = viewPadding / 4
            color = Color.BLACK
            isAntiAlias = true
        }
        val pointer = RectF(x - viewPadding, 0f, x + viewPadding, height)
        canvas.drawRoundRect(pointer, viewPadding / 4, viewPadding / 4, pointerPaint)
    }

    override fun setAlpha(p0: Int) {}

    override fun getOpacity() = PixelFormat.OPAQUE

    override fun setColorFilter(p0: ColorFilter?) {}
}