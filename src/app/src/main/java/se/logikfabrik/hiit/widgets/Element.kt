package se.logikfabrik.hiit.widgets

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import se.logikfabrik.hiit.R
import java.util.Collections.rotate

class Element(context: Context, attrs: AttributeSet) : View(context, attrs) {
    /*
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        canvas.drawRect(RectF(0F, 0F, width.toFloat(), height.toFloat()), Paint().apply { color = Color.GREEN })

    }
    */

    private var animator: ValueAnimator? = null

    override fun onFinishInflate() {
        super.onFinishInflate()

        val min = minOf(width, height)
    }

    fun doStuff() {
        animator!!.start()
    }

    var radius = 0

    var rotate = 0

    var scale = 0F

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val min = (minOf(width, height) * 0.9).toFloat()

        // canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), Paint().apply { color = Color.RED })

        // canvas.scale(scale, scale, (width / 2).toFloat(), (height/ 2).toFloat())

        canvas.rotate(rotate.toFloat(), (width / 2).toFloat(), (height / 2).toFloat())

        canvas.drawRoundRect(
            (width - min),
            (height - min),
            min,
            min,
            radius.toFloat(),
            radius.toFloat(),
            Paint().apply { color = Color.GREEN }
        )

        if (animator != null) {
            return
        }

        val propertyRadius: PropertyValuesHolder =
            PropertyValuesHolder.ofInt("PROPERTY_RADIUS", 0, (min / 2).toInt())
        val propertyRotate: PropertyValuesHolder =
            PropertyValuesHolder.ofInt("PROPERTY_ROTATE", 0, 180)

        val propertyScale: PropertyValuesHolder =
            PropertyValuesHolder.ofFloat("PROPERTY_SCALE", 1F, 0.2F)

        animator = ValueAnimator()
        animator?.interpolator = AccelerateDecelerateInterpolator()
        animator?.setValues(propertyRadius, propertyRotate, propertyScale)
        animator?.duration = getResources().getInteger(android.R.integer.config_mediumAnimTime).toLong()
        animator?.repeatCount = 0

        animator?.addUpdateListener { animation ->
            radius = animation.getAnimatedValue("PROPERTY_RADIUS") as Int
            rotate = animation.getAnimatedValue("PROPERTY_ROTATE") as Int
            // scale = animation.getAnimatedValue("PROPERTY_SCALE") as Float

            invalidate()
        }
        // animator?.start()
    }
}
