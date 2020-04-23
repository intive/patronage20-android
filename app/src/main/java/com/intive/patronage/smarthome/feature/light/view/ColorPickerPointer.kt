package com.intive.patronage.smarthome.feature.light.view

import android.graphics.*
import android.graphics.drawable.Drawable

class ColorPickerPointer : Drawable() {

    var x: Float = 0f
    var y: Float = 0f

    override fun draw(canvas: Canvas) {
        val pointerPaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = 6f
            color = Color.BLACK
            isAntiAlias = true
        }
        canvas.drawCircle(x, y, 24f, pointerPaint)
    }

    override fun setAlpha(p0: Int) {}

    override fun getOpacity() = PixelFormat.OPAQUE

    override fun setColorFilter(p0: ColorFilter?) {}
}