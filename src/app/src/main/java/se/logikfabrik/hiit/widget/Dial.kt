package se.logikfabrik.hiit.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator

// The dial animates its current time and total time arcs as time elapses.
class Dial(context: Context) : View(context) {
    var totalTime = 0
        set(value) {
            field = value.coerceAtLeast(0)

            postInvalidateOnAnimation()
        }

    var totalTimeElapsed = 0
        set(value) {
            val v = value.coerceAtLeast(0)

            if (v > field) {
                totalTimeAnimator.setFloatValues(field.toFloat(), v.toFloat())
                totalTimeAnimator.start()
            } else {
                totalTimeAnimator.setFloatValues(0F, v.toFloat())
                totalTimeAnimator.start()
            }

            field = v
        }

    var totalTimeAngleDirection = Direction.NARROWING
        set(value) {
            field = value

            postInvalidateOnAnimation()
        }

    var currentTime = 0
        set(value) {
            field = value.coerceAtLeast(0)

            postInvalidateOnAnimation()
        }

    var currentTimeElapsed = 0
        set(value) {
            val v = value.coerceAtLeast(0)

            if (v > field) {
                currentTimeAnimator.setFloatValues(field.toFloat(), v.toFloat())
                currentTimeAnimator.start()
            } else {
                currentTimeAnimator.setFloatValues(0F, v.toFloat())
                currentTimeAnimator.start()
            }

            field = v
        }

    var currentTimeAngleDirection = Direction.NARROWING
        set(value) {
            field = value

            postInvalidateOnAnimation()
        }

    private var centerX = 0F
    private var centerY = 0F

    private var dialRect = RectF()
    private var totalTimeRect = RectF()
    private var currentTimeRect = RectF()

    private val totalTimeAnimator: ValueAnimator = ValueAnimator.ofFloat().apply {
        repeatCount = 0
        duration = 1000
        interpolator = LinearInterpolator()
    }
    private val currentTimeAnimator: ValueAnimator = ValueAnimator.ofFloat().apply {
        repeatCount = 0
        duration = 1000
        interpolator = DecelerateInterpolator()
    }

    private var totalTimeElapsedValue = 0F
    private var currentTimeElapsedValue = 0F

    init {
        totalTimeAnimator.addUpdateListener { animation ->
            totalTimeElapsedValue = animation.animatedValue as Float

            postInvalidateOnAnimation()
        }

        currentTimeAnimator.addUpdateListener { animation ->
            currentTimeElapsedValue = animation.animatedValue as Float

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

        private val totalTimePaint = Paint().apply {
            isAntiAlias = true
            color = 0XFFFF610f.toInt()
            style = Paint.Style.STROKE
            strokeWidth = 20F
        }

        private val currentTimePaint = Paint().apply {
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

        totalTimeRect = RectF(baseRect).apply {
            inset(
                totalTimePaint.strokeWidth / 2,
                totalTimePaint.strokeWidth / 2
            )
        }

        currentTimeRect = RectF(baseRect).apply {
            inset(
                totalTimePaint.strokeWidth + (currentTimePaint.strokeWidth / 2),
                totalTimePaint.strokeWidth + (currentTimePaint.strokeWidth / 2)
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()

        canvas.rotate(-90F, centerX, centerY)

        drawDialOval(canvas)
        drawTotalTimeArc(canvas)
        drawCurrentTimeArc(canvas)

        canvas.restore()
    }

    private fun drawDialOval(canvas: Canvas) {
        canvas.drawOval(dialRect, dialPaint)
    }

    private fun drawTotalTimeArc(canvas: Canvas) {
        drawTimeArc(
            canvas,
            totalTimeRect,
            getAngle(totalTimeElapsedValue, totalTime, totalTimeAngleDirection),
            totalTimePaint
        )
    }

    private fun drawCurrentTimeArc(canvas: Canvas) {
        drawTimeArc(
            canvas,
            currentTimeRect,
            getAngle(currentTimeElapsedValue, currentTime, currentTimeAngleDirection),
            currentTimePaint
        )
    }

    private fun drawTimeArc(
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

    private fun getAngle(timeElapsed: Float, time: Int, direction: Direction): Float {
        val angle =
            ((if (direction != Direction.NARROWING) 0 else 1) - timeElapsed / time.toFloat()) * 360

        if (direction == Direction.WIDENING && angle == -360F) {
            return 360F
        }

        return angle
    }
}
