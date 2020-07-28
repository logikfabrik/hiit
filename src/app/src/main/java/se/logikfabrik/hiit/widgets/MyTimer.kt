package se.logikfabrik.hiit.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import se.logikfabrik.hiit.R

class MyTimer(context: Context, attrs: AttributeSet) : View(context, attrs) {
    var currentTime = 0F
        set(value) {
            require(value >= currentTimeElapsed) { "Current time must be greater than or equal to current time elapsed." }
            require(value >= 0) { "Current time must be greater than or equal to 0." }

            if (field == value) {
                return
            }

            field = value

            invalidate()
        }

    var currentTimeElapsed = 0F
        set(value) {
            require(value <= currentTime) { "Current time elapsed must be less than or equal to current time." }
            require(value >= 0) { "Current time elapsed must be greater than or equal to 0." }

            if (field == value) {
                return
            }

            field = value

            invalidate()
        }

    var totalTime = 0F
        set(value) {
            require(value >= totalTimeElapsed) { "Total time must be greater than or equal to total time elapsed." }
            require(value >= 0) { "Total time must be greater than or equal to 0." }

            if (field == value) {
                return
            }

            field = value

            invalidate()
        }

    var totalTimeElapsed = 0F
        set(value) {
            require(value <= totalTime) { "Total time elapsed must be less than or equal to total time." }
            require(value >= 0) { "Total time elapsed must be greater than or equal to 0." }

            if (field == value) {
                return
            }

            field = value

            invalidate()
        }

    private val dialPaint = Paint().apply {
        isAntiAlias = true
        color = resources.getColor(R.color.greyDark, null)
        style = Paint.Style.STROKE
        strokeWidth = 50F
    }

    private val currentTimePaint = Paint().apply {
        isAntiAlias = true
        color = resources.getColor(R.color.purpleDark, null)
        style = Paint.Style.STROKE
        strokeWidth = 40F
    }

    private val totalTimePaint = Paint().apply {
        isAntiAlias = true
        color = resources.getColor(R.color.orangeDark, null)
        style = Paint.Style.STROKE
        strokeWidth = 10F
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cx = width / 2.toFloat()
        val cy = height / 2.toFloat()

        canvas.rotate(-90F, cx, cy)

        val r = minOf(width, height) / 2.toFloat()

        val dialRect = RectF(cx - r, cy - r, cx + r, cy + r)

        dialRect.inset(dialPaint.strokeWidth / 2, dialPaint.strokeWidth / 2)

        canvas.drawOval(dialRect, dialPaint)

        drawArcForTotalTime(canvas, dialRect)

        drawArcForCurrentTime(canvas, dialRect)
    }

    private fun drawArcForCurrentTime(canvas: Canvas, rect: RectF) {
        val currentTimeRect = RectF(rect)

        val currentTimeWidth = (dialPaint.strokeWidth / 2) - (currentTimePaint.strokeWidth / 2)

        currentTimeRect.inset(currentTimeWidth, currentTimeWidth)

        val currentTimePath = Path()

        val currentTimeAngle = (1 - currentTimeElapsed / currentTime) * 360

        currentTimePath.arcTo(currentTimeRect, 0F, currentTimeAngle, false)

        canvas.drawPath(currentTimePath, currentTimePaint)
    }

    private fun drawArcForTotalTime(canvas: Canvas, rect: RectF) {
        val totalTimeRect = RectF(rect)

        val totalTimeWidth = (dialPaint.strokeWidth / 2) - (totalTimePaint.strokeWidth / 2)

        totalTimeRect.inset(-1 * totalTimeWidth, -1 * totalTimeWidth)

        val totalTimePath = Path()

        val totalTimeAngle = (1 - totalTimeElapsed / totalTime) * 360

        totalTimePath.arcTo(totalTimeRect, 0F, totalTimeAngle, false)

        canvas.drawPath(totalTimePath, totalTimePaint)
    }
}
