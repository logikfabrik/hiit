package se.logikfabrik.hiit.widgets

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.RelativeLayout

class Emitter(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    private class Pulse(context: Context) : View(context) {

        init {
            alpha = 0F
        }

        companion object {
            private val paint = Paint().apply {
                isAntiAlias = true
                color = 0xFF333333.toInt()
                style = Paint.Style.STROKE
                strokeWidth = 5F
            }
        }

        override fun onDraw(canvas: Canvas) {
            val r = minOf(width, height) / 2.toFloat()

            canvas.drawCircle(width / 2.toFloat(), height / 2.toFloat(), r, paint)
        }
    }

    private val animatorSet: AnimatorSet

    init {
        // Create the pulses and set up their animations
        animatorSet = AnimatorSet().apply {
            interpolator = DecelerateInterpolator()

            playTogether(
                (0..4).map {
                    createAnimator(Pulse(context)).apply {
                        startDelay = it * 1000L
                    }
                }
            )
        }

        // Add the views (pulses) to the layout
        animatorSet.childAnimations.forEach { animator -> addView((animator as ObjectAnimator).target as View) }
    }

    private fun createAnimator(pulse: Pulse): ObjectAnimator {
        return ObjectAnimator.ofPropertyValuesHolder(
            pulse,
            PropertyValuesHolder.ofFloat(View.SCALE_X, 1F, 2F),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1F, 2F),
            PropertyValuesHolder.ofFloat(View.ALPHA, 1F, 0F)
        ).apply {
            duration = 5000
            repeatMode = ObjectAnimator.RESTART
            repeatCount = ObjectAnimator.INFINITE
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        animatorSet.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        animatorSet.cancel()
    }
}
