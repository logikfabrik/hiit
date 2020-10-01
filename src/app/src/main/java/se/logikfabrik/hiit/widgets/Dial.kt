package se.logikfabrik.hiit.widgets

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator

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

    private var cx = 0F
    private var cy = 0F

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
        duration = 500
        interpolator = DecelerateInterpolator()
    }

    private var totalTimeElapsedValue = 0F
    private var currentTimeElapsedValue = 0F

    private var totalTimeAngleIsGrowing = false
    private var currentTimeAngleIsGrowing = false

    init {
        totalTimeAnimator.addUpdateListener { animation ->
            totalTimeElapsedValue = animation.animatedValue as Float

            if (totalTimeElapsedValue == totalTime.toFloat()) {
                totalTimeAngleIsGrowing = !totalTimeAngleIsGrowing
            }

            postInvalidateOnAnimation()
        }

        currentTimeAnimator.addUpdateListener { animation ->
            currentTimeElapsedValue = animation.animatedValue as Float

            if (currentTimeElapsedValue == currentTime.toFloat()) {
                currentTimeAngleIsGrowing = !currentTimeAngleIsGrowing
            }

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

        canvas.save()

        canvas.rotate(-90F, cx, cy)

        // Draw oval for dial
        drawDialOval(canvas, dialRect, dialPaint)

        drawTimeArc(
            canvas,
            totalTimeRect,
            getAngle(totalTimeElapsedValue, totalTime, totalTimeAngleIsGrowing),
            totalTimePaint
        )

        drawTimeArc(
            canvas,
            currentTimeRect,
            getAngle(currentTimeElapsedValue, currentTime, currentTimeAngleIsGrowing),
            currentTimePaint
        )

        canvas.restore()
    }

    private fun drawDialOval(canvas: Canvas, rect: RectF, paint: Paint) {
        canvas.drawOval(rect, paint)
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

    private fun getAngle(timeElapsed: Float, time: Int, grow: Boolean): Float {
        val angle = ((if (grow) 0 else 1) - timeElapsed / time.toFloat()) * 360

        if (!grow && angle == 0F) {
            return 360F
        }

        return angle
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState() ?: return null

        val savedState = SavedState(superState)

        savedState.totalTimeAngleIsGrowing = totalTimeAngleIsGrowing
        savedState.currentTimeAngleIsGrowing = currentTimeAngleIsGrowing

        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as SavedState

        super.onRestoreInstanceState(savedState.superState)

        totalTimeAngleIsGrowing = savedState.totalTimeAngleIsGrowing
        currentTimeAngleIsGrowing = savedState.currentTimeAngleIsGrowing
    }

    internal class SavedState : BaseSavedState {
        var totalTimeAngleIsGrowing = false
        var currentTimeAngleIsGrowing = false

        @SuppressLint("ParcelClassLoader")
        constructor(source: Parcel) : super(source) {
            totalTimeAngleIsGrowing = source.readValue(null) as Boolean
            currentTimeAngleIsGrowing = source.readValue(null) as Boolean
        }

        constructor(superState: Parcelable) : super(superState)

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)

            out.writeValue(totalTimeAngleIsGrowing)
            out.writeValue(currentTimeAngleIsGrowing)
        }

        companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel): SavedState {
                    return SavedState(source)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}
