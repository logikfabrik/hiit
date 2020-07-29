package se.logikfabrik.hiit.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import se.logikfabrik.hiit.R

class MyTimer (context: Context, attrs: AttributeSet) : View(context, attrs) {
    var currentTime = 0F
        set(value) {
            field = value.coerceAtLeast(currentTimeElapsed)

            postInvalidateOnAnimation()
        }

    var currentTimeElapsed = 0F
        set(value) {
            field = value.coerceIn(0F, currentTime)

            postInvalidateOnAnimation()
        }

    var totalTime = 0F
        set(value) {
            field = value.coerceAtLeast(totalTimeElapsed)

            postInvalidateOnAnimation()
        }

    var totalTimeElapsed = 0F
        set(value) {
            field = value.coerceIn(0F, totalTime)

            postInvalidateOnAnimation()
        }

    private val _dialPaint = Paint().apply {
        isAntiAlias = true
        color = resources.getColor(R.color.greyDark, null)
        style = Paint.Style.STROKE
        strokeWidth = 50F
    }

    private val _currentTimePaint = Paint().apply {
        isAntiAlias = true
        color = resources.getColor(R.color.purpleDark, null)
        style = Paint.Style.STROKE
        strokeWidth = 40F
    }

    private val _totalTimePaint = Paint().apply {
        isAntiAlias = true
        color = resources.getColor(R.color.orangeDark, null)
        style = Paint.Style.STROKE
        strokeWidth = 10F
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val r = minOf(width, height) / 2.toFloat()
        val cx = width / 2.toFloat()
        val cy = height / 2.toFloat()

        canvas.rotate(-90F, cx, cy)

        val dialRect = RectF(cx - r, cy - r, cx + r, cy + r)

        dialRect.inset(_dialPaint.strokeWidth / 2, _dialPaint.strokeWidth / 2)

        canvas.drawOval(dialRect, _dialPaint)

        drawArcForTotalTime(canvas, dialRect)

        drawArcForCurrentTime(canvas, dialRect)
    }

    private fun drawArcForCurrentTime(canvas: Canvas, rect: RectF) {
        val currentTimeRect = RectF(rect)

        val currentTimeWidth = (_dialPaint.strokeWidth / 2) - (_currentTimePaint.strokeWidth / 2)

        currentTimeRect.inset(currentTimeWidth, currentTimeWidth)

        val currentTimePath = Path()

        val currentTimeAngle = (1 - currentTimeElapsed / currentTime) * 360

        currentTimePath.arcTo(currentTimeRect, 0F, currentTimeAngle, false)

        canvas.drawPath(currentTimePath, _currentTimePaint)
    }

    private fun drawArcForTotalTime(canvas: Canvas, rect: RectF) {
        val totalTimeRect = RectF(rect)

        val totalTimeWidth = (_dialPaint.strokeWidth / 2) - (_totalTimePaint.strokeWidth / 2)

        totalTimeRect.inset(-1 * totalTimeWidth, -1 * totalTimeWidth)

        val totalTimePath = Path()

        val totalTimeAngle = (1 - totalTimeElapsed / totalTime) * 360

        totalTimePath.arcTo(totalTimeRect, 0F, totalTimeAngle, false)

        canvas.drawPath(totalTimePath, _totalTimePaint)
    }
}
