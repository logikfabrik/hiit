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
    private var outerRadius: Float? = null
    private var innerRadius: Float? = null

    var currentTime: Int = 0
    var currentTimeElapsed: Int = 0

    var totalTime: Int = 0
    var totalTimeElapsed: Int = 0

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

        var min = minOf(width, height).toFloat()

        var outerRadius = min / 2

        var innerRadius = (0.95 * outerRadius).toFloat()

        //canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), outerRadius!!, dialPaint)
        canvas.rotate(-90F, (width / 2).toFloat(), (height / 2).toFloat())
        drawArcSegment(
            canvas,
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (innerRadius!! * 0.8).toFloat(),
            outerRadius!!,
            0F,
            CIRCLE_LIMIT,
            dialPaint

        )

        // Draw arc for total time


        drawArcSegment(
            canvas,
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            innerRadius!!,
            outerRadius!!,
            0F,
            60F,
            totalTimePaint

        )

        // Draw arc for current time


        drawArcSegment(
            canvas,
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            (innerRadius!! * 0.8).toFloat(),
            innerRadius!!,
            0F,
            150F,
            currentTimePaint

        )


    }

    private val CIRCLE_LIMIT = 359.9999f


    fun drawArcSegment(
        canvas: Canvas,
        cx: Float,
        cy: Float,
        rInn: Float,
        rOut: Float,
        startAngle: Float,
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



        val outerRect = RectF(cx - rOut, cy - rOut, cx + rOut, cy + rOut)

        val innerRect = RectF(cx - rInn, cy - rInn, cx + rInn, cy + rInn)

        val segmentPath = Path()

        val start: Double = Math.toRadians(startAngle.toDouble())
// https://stackoverflow.com/questions/3874424/android-looking-for-a-drawarc-method-with-inner-outer-radius
        segmentPath.moveTo(
            (cx + rInn * cos(start)).toFloat(),
            (cy + rInn * sin(start)).toFloat()
        )
        segmentPath.arcTo(outerRect, startAngle, sweepAngle)

        segmentPath.arcTo(innerRect, startAngle + sweepAngle, -sweepAngle, false)

        segmentPath.close()


            canvas.drawPath(segmentPath, fill)

    }
}
