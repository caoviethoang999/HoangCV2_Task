package com.example.hoangcv2_task

import android.view.View
import android.graphics.PointF
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet


class CustomWormProgressBar:View,InvalidateListener {
    private var circlesSize:Int=DEF_COUNT
    private lateinit var circles:Array<Circle?>
    private var radius = 0f
    private val transformations: IntArray = intArrayOf(-2, -1, 0, 1, 2)
    private var center=PointF()
    private var mDotColor = Color.parseColor("#fd583f")

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
            attrs, R.styleable.ToolDotProgress, 0, 0
        )
        try {
            mDotColor = a.getColor(R.styleable.ToolDotProgress_color, mDotColor)
            circlesSize = a.getInteger(R.styleable.ToolDotProgress_count, circlesSize)
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
        circles= arrayOfNulls<Circle>(circlesSize)
        //size of layout
        radius = width / 15f - width / 80f
        for (i in 0 until circlesSize) {
            circles[i] = Circle()
            circles[i]?.setColor(mDotColor)
            circles[i]?.setCurrentRadius(radius)
            circles[i]?.setCenter(center.x, center.y)
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
        for (i in 0 until circlesSize) {
            val translateAnimator = ValueAnimator.ofFloat(center.y, height / 4f, height * 3 / 4f, center.y)
            translateAnimator.duration = 1000
            translateAnimator.startDelay = (i * 120).toLong()
            translateAnimator.repeatCount = ValueAnimator.INFINITE
            translateAnimator.addUpdateListener { animation ->
                circles[i]?.setCenter(center.x, animation.animatedValue as Float)
                reDraw()
            }
            translateAnimator.start()
        }
    }
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        for (i in 0 until circlesSize) {
            canvas.save()
            //space between circle
            canvas.translate(3 * radius * transformations[i], 0F)
            circles[i]?.draw(canvas)
            canvas.restore()
        }
    }

    override fun reDraw() {
        invalidate()
    }

    companion object {
        const val MIN_COUNT = 1
        const val DEF_COUNT = 5
        const val MAX_COUNT = 100
        const val MIN_TIMEOUT = 100
        const val DEF_TIMEOUT = 500
        const val MAX_TIMEOUT = 3000
    }
}