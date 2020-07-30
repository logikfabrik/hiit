package se.logikfabrik.hiit.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.view.View

class Dial(context: Context) : View(context) {
    var totalTime = 0
        set(value) {
            field = value.coerceAtLeast(totalTimeElapsed)

            postInvalidateOnAnimation()
        }

    var totalTimeElapsed = 0
        set(value) {
            field = value.coerceIn(0, totalTime)

            postInvalidateOnAnimation()
        }

    var currentTime = 0
        set(value) {
            field = value.coerceAtLeast(currentTimeElapsed)

            postInvalidateOnAnimation()
        }

    var currentTimeElapsed = 0
        set(value) {
            field = value.coerceIn(0, currentTime)

            postInvalidateOnAnimation()
        }

    private var cx = 0F
    private var cy = 0F

    private var dialRect = RectF()
    private var totalTimeRect = RectF()
    private var currentTimeRect = RectF()

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

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        cx = w / 2F
        cy = h / 2F

        val r = minOf(w, h) / 2.2F

        val baseRect = RectF(
            cx - r,
            cy - r,
            cx + r,
            cy + r
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

        canvas.rotate(-90F, cx, cy)

        // Draw oval for dial
        drawDialOval(canvas, dialRect, dialPaint)

        // Draw arc for total time
        drawTimeArc(canvas, totalTimeRect, getAngle(totalTimeElapsed, totalTime), totalTimePaint)

        // Draw arc for current time
        drawTimeArc(canvas, currentTimeRect, getAngle(currentTimeElapsed, currentTime), currentTimePaint)
    }

    private fun drawDialOval(canvas: Canvas, rect: RectF, paint: Paint) {
        canvas.drawOval(rect, paint)
    }

    private fun drawTimeArc(canvas: Canvas, rect: RectF, angle: Float, paint: Paint) {
        canvas.drawPath(Path().apply { arcTo(rect, 0F, angle, false) }, paint)
    }

    private fun getAngle(timeElapsed: Int, time: Int): Float {
        return (1 - timeElapsed / time.toFloat()) * 360
    }
}
