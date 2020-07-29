package se.logikfabrik.hiit.widgets

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import se.logikfabrik.hiit.R
import kotlin.math.roundToInt

class Pulse(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var startR = 0F
    private var endR = 0F

    private var cx = 0F
    private var cy = 0F

    private val pulseDistance = 40F
    private var pulseOffset = 0F
    private var pulseCount = 0
    private val pulseAnimator: ValueAnimator
    private val pulsePaint: Paint

    init {
        pulseAnimator = ValueAnimator.ofFloat(0F, pulseDistance).apply {
            addUpdateListener { animator ->
                pulseOffset = animator.animatedValue as Float

                postInvalidateOnAnimation()
            }
            duration = 1000
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
        }

        pulsePaint = Paint().apply {
            isAntiAlias = true
            color = resources.getColor(R.color.greyDark, null)
            style = Paint.Style.STROKE
            strokeWidth = 5F
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        pulseAnimator.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        pulseAnimator.cancel()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        cx = minOf(w, h) / 2.toFloat()
        cy = cx

        endR = maxOf(w, h).toFloat()
        startR = minOf(w, h) / 2.toFloat()

        pulseCount = ((endR - startR) / pulseDistance).roundToInt()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (i in 0..pulseCount) {
            val alpha = 255 - ((i / pulseCount.toFloat()) * 255).roundToInt()

            pulsePaint.alpha = alpha

            canvas.drawCircle(cx, cy, startR + (pulseDistance * i) + pulseOffset, pulsePaint)
        }
    }
}
