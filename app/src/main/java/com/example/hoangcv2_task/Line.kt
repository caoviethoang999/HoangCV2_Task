package com.example.hoangcv2_task

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

class Line {
    private var paint: Paint = Paint()

    private var center: PointF = PointF()

    private var currentRadius = 0f

    private var point1: PointF? = null
    private var point2: PointF? = null

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

    fun setWidth(width: Float) {
        paint.strokeWidth = width
    }

    fun setPoint1(point1: PointF?) {
        this.point1 = point1
    }

    fun setPoint2(point2: PointF?) {
        this.point2 = point2
    }

    fun getPoint1(): PointF? {
        return point1
    }

    fun getPoint2(): PointF? {
        return point2
    }

    fun draw(canvas: Canvas) {
        canvas.drawLine(point1!!.x, point1!!.y, point2!!.x, point2!!.y, paint);
    }
}