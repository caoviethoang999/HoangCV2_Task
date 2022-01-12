package com.example.hoangcv2_task

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF



class Circle {
    private var paint: Paint= Paint()

    private var center: PointF= PointF()

    private var currentRadius = 0f

    init {
        paint.isAntiAlias = true
    }

    fun setColor(color: Int) {
        paint.color = color
    }

    fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    fun setCenter(x: Float, y: Float) {
        center[x] = y
    }

    fun getCurrentRadius(): Float {
        return currentRadius
    }

    fun setCurrentRadius(radius: Float) {
        currentRadius = radius
    }

    fun draw(canvas: Canvas) {
        canvas.drawCircle(center!!.x, center!!.y, currentRadius, paint)
    }
}