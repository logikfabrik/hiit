package se.logikfabrik.hiit.widgets

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.sin

class Circle(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var w = 0
    private var h = 0

    private var outerRadius: Float? = null
    private var innerRadius: Float? = null

    private val paint: Paint
    // private lateinit var rect: RectF

    var angle = 90f

    companion object {
        private val START_ANGLE_POINT = 270f
    }

    init {
        paint = Paint().apply {
            isAntiAlias = true
            color = Color.RED
        }

        // rect = RectF(0F,0F, outerRadius * 2, outerRadius *2)
    }
/*
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if(outerRadius == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            return;
        }

        super.onMeasure(
            MeasureSpec.makeMeasureSpec((outerRadius!! * 2).roundToInt(), MeasureSpec.AT_MOST),
            MeasureSpec.makeMeasureSpec((outerRadius!! * 2).roundToInt(), MeasureSpec.AT_MOST));
    }
*/

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var min = minOf(width, height)
        outerRadius = (min / 2).toFloat()

        innerRadius = (0.8 * outerRadius!!).toFloat()

        Log.i(null, "Radius: ")
        Log.i(null, outerRadius.toString())
        Log.i(null, innerRadius.toString())

        // rect = RectF(0F,0F, outerRadius!! * 2, outerRadius!! *2)

        canvas.drawRect(RectF(0F, 0F, width.toFloat(), height.toFloat()), Paint().apply { color = Color.GREEN })
        drawArcSegment(canvas, (width / 2).toFloat(), (height / 2).toFloat(), innerRadius!!, outerRadius!!, 90F, 360F, paint, null)
        drawArcSegment(canvas, (width / 2).toFloat(), (height / 2).toFloat(), (innerRadius!! * 0.9).toFloat(), innerRadius!!, 90F, 360F, Paint().apply { color = Color.WHITE }, null)

        // canvas.drawArc(rect, START_ANGLE_POINT, angle, false, paint)
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
        fill: Paint?,
        stroke: Paint?
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

        val start: Double = toRadians(startAngle.toDouble())

        segmentPath.moveTo(
            (cx + rInn * cos(start)).toFloat(),
            (cy + rInn * sin(start)).toFloat()
        )

        segmentPath.lineTo(
            (cx + rOut * cos(start)).toFloat(),
            (cy + rOut * sin(start)).toFloat()
        )

        segmentPath.arcTo(outerRect, startAngle, sweepAngle)

        val end: Double = toRadians((startAngle + sweepAngle).toDouble())

        segmentPath.lineTo(
            (cx + rInn * cos(end)).toFloat(),
            (cy + rInn * sin(end)).toFloat()
        )

        segmentPath.arcTo(innerRect, startAngle + sweepAngle, -sweepAngle)

        if (fill != null) {
            canvas.drawPath(segmentPath, fill)
        }

        if (stroke != null) {
            canvas.drawPath(segmentPath, stroke)
        }
    }
}
