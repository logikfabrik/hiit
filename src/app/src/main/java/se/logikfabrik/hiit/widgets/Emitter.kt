package se.logikfabrik.hiit.widgets

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.RelativeLayout
import java.util.LinkedList

class Emitter(context: Context) : RelativeLayout(context) {

    private class Pulse(context: Context) : View(context) {

        private var r = 0F
        private var cx = 0F
        private var cy = 0F

        init {
            alpha = 0F
        }

        companion object {
            private val paint = Paint().apply {
                isAntiAlias = true
                color = 0XFF711C9B.toInt()
                style = Paint.Style.STROKE
                strokeWidth = 6F
            }
        }

        override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
            super.onSizeChanged(w, h, oldw, oldh)

            r = (minOf(w, h) - paint.strokeWidth) / 2.2F
            cx = w / 2F
            cy = h / 2F
        }

        override fun onDraw(canvas: Canvas) {
            canvas.drawCircle(cx, cy, r, paint)
        }
    }

    private val animators: LinkedList<ObjectAnimator>

    init {
        val pulses = (0..4).map {
            Pulse(context)
        }

        pulses.forEach { pulse ->
            addView(pulse)
        }

        animators = LinkedList(pulses.map { pulse -> createAnimator(pulse) })
    }

    private fun createAnimator(pulse: Pulse): ObjectAnimator {
        return ObjectAnimator.ofPropertyValuesHolder(
            pulse,
            PropertyValuesHolder.ofFloat(View.SCALE_X, 1F, 2F),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1F, 2F),
            PropertyValuesHolder.ofFloat(View.ALPHA, 1F, 0F)
        ).apply {
            duration = 5000
            interpolator = DecelerateInterpolator()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        animators.forEach { animator -> animator.cancel() }
    }

    fun emit() {
        val animator = animators.poll() ?: return

        animator.start()

        animators.add(animator)
    }
}
