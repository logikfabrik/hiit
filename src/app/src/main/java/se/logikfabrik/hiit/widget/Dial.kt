package se.logikfabrik.hiit.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator

// The dial animates its arcs as its properties for time, time elapsed, and arc scale are changed.
class Dial(context: Context, attrs: AttributeSet) : View(context, attrs) {
    var timeMillis = 0L
        set(value) {
            require(value >= 0)

            field = value

            postInvalidateOnAnimation()
        }

    var elapsedTimeMillis = 0L
        set(value) {
            require(value >= 0)

            if (value > field) {
                timeAnimator.setFloatValues(field.toFloat(), value.toFloat())
            } else {
                timeAnimator.setFloatValues(0F, value.toFloat())
            }

            timeAnimator.start()

            field = value
        }

    var stageTimeMillis = 0L
        set(value) {
            require(value >= 0)

            field = value

            postInvalidateOnAnimation()
        }

    var elapsedStageTimeMillis = 0L
        set(value) {
            require(value >= 0)

            if (value > field) {
                stageTimeAnimator.setFloatValues(field.toFloat(), value.toFloat())
            } else {
                stageTimeAnimator.setFloatValues(0F, value.toFloat())
            }

            stageTimeAnimator.start()

            field = value
        }

    private var centerX = 0F
    private var centerY = 0F

    private var dialRect = RectF()
    private var timeRect = RectF()
    private var stageTimeRect = RectF()

    private val timeAnimator: ValueAnimator = ValueAnimator.ofFloat().apply {
        repeatCount = 0
        duration = 1000
        interpolator = LinearInterpolator()
    }
    private val stageTimeAnimator: ValueAnimator = ValueAnimator.ofFloat().apply {
        repeatCount = 0
        duration = 1000
        interpolator = DecelerateInterpolator()
    }

    private var elapsedTimeValue = 0F
    private var elapsedStageTimeValue = 0F

    init {
        timeAnimator.addUpdateListener { animation ->
            elapsedTimeValue = animation.animatedValue as Float

            postInvalidateOnAnimation()
        }

        stageTimeAnimator.addUpdateListener { animation ->
            elapsedStageTimeValue = animation.animatedValue as Float

            postInvalidateOnAnimation()
        }
    }

    companion object {
        private val dialPaint = Paint().apply {
            isAntiAlias = true
            color = 0XFF711C9B.toInt()
            style = Paint.Style.STROKE
            strokeWidth = 70F
        }

        private val timePaint = Paint().apply {
            isAntiAlias = true
            color = 0XFFFF610f.toInt()
            style = Paint.Style.STROKE
            strokeWidth = 20F
        }

        private val setTimePaint = Paint().apply {
            isAntiAlias = true
            color = 0XFF333333.toInt()
            style = Paint.Style.STROKE
            strokeWidth = 50F
        }
    }

    override fun onSizeChanged(newWidth: Int, newHeight: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight)

        centerX = newWidth / 2F
        centerY = newHeight / 2F

        val radius = minOf(newWidth, newHeight) / 2.2F

        val baseRect = RectF(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )

        dialRect = RectF(baseRect).apply {
            inset(
                dialPaint.strokeWidth / 2,
                dialPaint.strokeWidth / 2
            )
        }

        timeRect = RectF(baseRect).apply {
            inset(
                timePaint.strokeWidth / 2,
                timePaint.strokeWidth / 2
            )
        }

        stageTimeRect = RectF(baseRect).apply {
            inset(
                timePaint.strokeWidth + (setTimePaint.strokeWidth / 2),
                timePaint.strokeWidth + (setTimePaint.strokeWidth / 2)
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()

        canvas.rotate(-90F, centerX, centerY)

        drawDialOval(canvas)
        drawTotalTimeArc(canvas)
        drawSetTimeArc(canvas)

        canvas.restore()
    }

    private fun drawDialOval(canvas: Canvas) {
        canvas.drawOval(dialRect, dialPaint)
    }

    private fun drawTotalTimeArc(canvas: Canvas) {
        drawArc(
            canvas,
            timeRect,
            getArcAngle(elapsedTimeValue, timeMillis),
            timePaint
        )
    }

    private fun drawSetTimeArc(canvas: Canvas) {
        drawArc(
            canvas,
            stageTimeRect,
            getArcAngle(elapsedStageTimeValue, stageTimeMillis),
            setTimePaint
        )
    }

    private fun drawArc(
        canvas: Canvas,
        rect: RectF,
        angle: Float,
        paint: Paint
    ) {
        val startAngle = 0F

        if (angle == 360F) {
            canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint)
        } else {
            canvas.drawPath(Path().apply { arcTo(rect, startAngle, angle, false) }, paint)
        }
    }

    private fun getArcAngle(elapsedTimeMillis: Float, timeMillis: Long): Float {
        return elapsedTimeMillis / timeMillis.toFloat() * 360
    }
}
