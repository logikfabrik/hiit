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

// The dial animates its arcs as its properties for time, time elapsed, and arc scale are changed.
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

    var totalTimeArcScale = ArcScale.SHRINKING
        set(value) {
            field = value

            postInvalidateOnAnimation()
        }

    var setTime = 0
        set(value) {
            field = value.coerceAtLeast(0)

            postInvalidateOnAnimation()
        }

    var setTimeElapsed = 0
        set(value) {
            val v = value.coerceAtLeast(0)

            if (v > field) {
                setTimeAnimator.setFloatValues(field.toFloat(), v.toFloat())
                setTimeAnimator.start()
            } else {
                setTimeAnimator.setFloatValues(0F, v.toFloat())
                setTimeAnimator.start()
            }

            field = v
        }

    var setTimeArcScale = ArcScale.SHRINKING
        set(value) {
            field = value

            postInvalidateOnAnimation()
        }

    private var centerX = 0F
    private var centerY = 0F

    private var dialRect = RectF()
    private var totalTimeRect = RectF()
    private var setTimeRect = RectF()

    private val totalTimeAnimator: ValueAnimator = ValueAnimator.ofFloat().apply {
        repeatCount = 0
        duration = 1000
        interpolator = LinearInterpolator()
    }
    private val setTimeAnimator: ValueAnimator = ValueAnimator.ofFloat().apply {
        repeatCount = 0
        duration = 1000
        interpolator = DecelerateInterpolator()
    }

    private var totalTimeElapsedValue = 0F
    private var setTimeElapsedValue = 0F

    init {
        totalTimeAnimator.addUpdateListener { animation ->
            totalTimeElapsedValue = animation.animatedValue as Float

            postInvalidateOnAnimation()
        }

        setTimeAnimator.addUpdateListener { animation ->
            setTimeElapsedValue = animation.animatedValue as Float

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

        totalTimeRect = RectF(baseRect).apply {
            inset(
                totalTimePaint.strokeWidth / 2,
                totalTimePaint.strokeWidth / 2
            )
        }

        setTimeRect = RectF(baseRect).apply {
            inset(
                totalTimePaint.strokeWidth + (setTimePaint.strokeWidth / 2),
                totalTimePaint.strokeWidth + (setTimePaint.strokeWidth / 2)
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
            totalTimeRect,
            getArcAngle(totalTimeElapsedValue, totalTime, totalTimeArcScale),
            totalTimePaint
        )
    }

    private fun drawSetTimeArc(canvas: Canvas) {
        drawArc(
            canvas,
            setTimeRect,
            getArcAngle(setTimeElapsedValue, setTime, setTimeArcScale),
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

    private fun getArcAngle(timeElapsed: Float, time: Int, arcScale: ArcScale): Float {
        val angle =
            ((if (arcScale != ArcScale.SHRINKING) 0 else 1) - timeElapsed / time.toFloat()) * 360

        if (arcScale == ArcScale.GROWING && angle == -360F) {
            return 360F
        }

        return angle
    }
}
