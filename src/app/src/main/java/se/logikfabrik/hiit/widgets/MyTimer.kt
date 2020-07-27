package se.logikfabrik.hiit.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import se.logikfabrik.hiit.R
import kotlin.math.cos
import kotlin.math.sin

class MyTimer(context: Context, attrs: AttributeSet) : View(context, attrs) {
    var currentTime: Int = 0
        set(value) {
            require(value >= currentTimeElapsed) { "Current time must be greater than or equal to current time elapsed." }

            if (field == value) {
                return
            }

            field = value

            invalidate()
        }

    var currentTimeElapsed: Int = 0
        set(value) {
            require(value <= currentTime) { "Current time elapsed must be less than or equal to current time." }

            if (field == value) {
                return
            }

            field = value

            invalidate()
        }

    var totalTime: Int = 0
        set(value) {
            require(value >= totalTimeElapsed) { "Total time must be greater than or equal to total time elapsed." }

            if (field == value) {
                return
            }

            field = value

            invalidate()
        }

    var totalTimeElapsed: Int = 0
        set(value) {
            require(value <= totalTime) { "Total time elapsed must be less than or equal to total time." }

            if (field == value) {
                return
            }

            field = value

            invalidate()
        }

    private val dialPaint = Paint().apply {
        isAntiAlias = true
        color = resources.getColor(R.color.greyDark, null)
    }

    private val currentTimePaint = Paint().apply {
        isAntiAlias = true
        color = resources.getColor(R.color.purpleDark, null)
    }

    private val totalTimePaint = Paint().apply {
        isAntiAlias = true
        color = resources.getColor(R.color.orangeDark, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cx = width / 2.toFloat()
        val cy = height / 2.toFloat()

        canvas.rotate(-90F, cx, cy)

        val totalTimeOuterRadius = minOf(width, height) / 2.toFloat()

        val totalTimeInnerRadius = totalTimeOuterRadius * 0.95.toFloat()

        val currentTimeOuterRadius = totalTimeInnerRadius

        val currentTimeInnerRadius = currentTimeOuterRadius * 0.8.toFloat()

        // Draw arc for dial
        drawArcSegment(
            canvas,
            cx,
            cy,
            totalTimeOuterRadius,
            currentTimeInnerRadius,
            CIRCLE_LIMIT,
            dialPaint
        )

        // Draw arc for total time
        val totalTimeAngle = (1 - totalTimeElapsed / totalTime.toFloat()) * CIRCLE_LIMIT

        drawArcSegment(
            canvas,
            cx,
            cy,
            totalTimeOuterRadius,
            totalTimeInnerRadius,
            totalTimeAngle,
            totalTimePaint
        )

        // Draw arc for current time
        val currentTimeAngle = (1 - currentTimeElapsed / currentTime.toFloat()) * CIRCLE_LIMIT

        drawArcSegment(
            canvas,
            cx,
            cy,
            currentTimeOuterRadius,
            currentTimeInnerRadius,
            currentTimeAngle,
            currentTimePaint
        )
    }

    private val CIRCLE_LIMIT = 359.9999f

    private fun drawArcSegment(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        rOut: Float,
        rInn: Float,
        sweepAngle: Float,
        fill: Paint
    ) {
        var sweepAngle = sweepAngle

        if (sweepAngle > CIRCLE_LIMIT) {
            sweepAngle = CIRCLE_LIMIT
        }

        if (sweepAngle < -CIRCLE_LIMIT) {
            sweepAngle = -CIRCLE_LIMIT
        }

        val outerOval = RectF(cx - rOut, cy - rOut, cx + rOut, cy + rOut)

        val innerOval = RectF(cx - rInn, cy - rInn, cx + rInn, cy + rInn)

        val segmentPath = Path().also {
            // https://stackoverflow.com/questions/3874424/android-looking-for-a-drawarc-method-with-inner-outer-radius
            it.moveTo(
                (cx + rInn * cos(0.0)).toFloat(),
                (cy + rInn * sin(0.0)).toFloat()
            )

            it.arcTo(outerOval, 0F, sweepAngle)

            it.arcTo(innerOval, sweepAngle, -sweepAngle, false)

            it.close()
        }

        canvas.drawPath(segmentPath, fill)
    }
}
