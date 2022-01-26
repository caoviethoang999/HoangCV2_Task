package com.example.hoangcv2_task

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.graphics.PointF

import android.graphics.Color
import android.animation.ValueAnimator
import android.graphics.Canvas
import kotlin.math.min


class CustomLineProgressBar:View,InvalidateListener {
    private var lineSize = 8
    private lateinit var lines: Array<Line?>
    private var mLineColor = Color.parseColor("#fd583f")
    private var center=PointF()

    constructor(context: Context?) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        applyAttrs(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        applyAttrs(context, attrs)
    }

    private fun applyAttrs(context: Context, attrs: AttributeSet?) {

        val a = context.theme.obtainStyledAttributes(
            attrs, R.styleable.ToolLineProgress, 0, 0
        )
        try {
            mLineColor = a.getColor(R.styleable.ToolLineProgress_line_color, mLineColor)
            lineSize = a.getInteger(R.styleable.ToolLineProgress_line_count, lineSize)
        } finally {
            a.recycle()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        setSize(width, height)
        initializeObjects()
        setUpAnimation()
    }

    private fun initializeObjects() {
        val size = min(width, height)
        val lineWidth = size / 10f
        lines = arrayOfNulls <Line>(lineSize)
        for (i in 0 until lineSize) {
            lines[i] = Line()
            lines[i]?.setColor(mLineColor)
            lines[i]?.setAlpha(126)
            lines[i]?.setWidth(lineWidth)
            lines[i]?.setPoint1(PointF(center.x, center.y - size / 2f + lineWidth))
            lines[i]?.setPoint2(PointF(center.x, lines[i]?.getPoint1()!!.y + 2 * lineWidth))
        }
    }

    private fun setSize(width: Int, height: Int) {
        this.center = PointF(width / 2.0f, height / 2.0f)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var measuredWidth = resolveSize(150, widthMeasureSpec);
        var measuredHeight = resolveSize(150, heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    private fun setUpAnimation() {
        for (i in 0 until lineSize) {
            val fadeAnimator = ValueAnimator.ofInt(126, 255, 126)
            fadeAnimator.repeatCount = ValueAnimator.INFINITE
            fadeAnimator.duration = 1000
            fadeAnimator.startDelay = (i * 120).toLong()
            fadeAnimator.addUpdateListener { animation ->
                lines[i]!!.setAlpha(animation.animatedValue as Int)
                 reDraw()
            }
            fadeAnimator.start()
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        for (i in 0 until lineSize) {
            canvas.save()
            canvas.rotate((45 * i).toFloat(), center.x, center.y)
            lines[i]!!.draw(canvas)
            canvas.restore()
        }
    }

    override fun reDraw() {
        invalidate()
    }
}