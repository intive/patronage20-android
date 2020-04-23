package com.intive.patronage.smarthome.feature.light.view

import android.graphics.*
import android.graphics.drawable.Drawable

class BrightnessBarPointer : Drawable() {

    var x: Float = 0f
    var height: Float = 0f

    override fun draw(canvas: Canvas) {
        val pointerPaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = 6f
            color = Color.BLACK
            isAntiAlias = true
        }
        val pointer = RectF(x - 24f, 0f, x + 24f, height)
        canvas.drawRoundRect(pointer, 6f, 6f, pointerPaint)
    }

    override fun setAlpha(p0: Int) {}

    override fun getOpacity() = PixelFormat.OPAQUE

    override fun setColorFilter(p0: ColorFilter?) {}
}