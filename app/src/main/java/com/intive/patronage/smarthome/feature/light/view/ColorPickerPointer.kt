package com.intive.patronage.smarthome.feature.light.view

import android.graphics.*
import android.graphics.drawable.Drawable

class ColorPickerPointer(private val halfOfPointerWidth: Float) : Drawable() {

    var x: Float = 0f
    var y: Float = 0f

    override fun draw(canvas: Canvas) {
        val pointerPaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = halfOfPointerWidth / 4
            color = Color.BLACK
            isAntiAlias = true
        }
        canvas.drawCircle(x, y, halfOfPointerWidth, pointerPaint)
    }

    override fun setAlpha(p0: Int) {}

    override fun getOpacity() = PixelFormat.OPAQUE

    override fun setColorFilter(p0: ColorFilter?) {}
}