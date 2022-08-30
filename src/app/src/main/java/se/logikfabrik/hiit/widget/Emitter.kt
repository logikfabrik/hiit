package se.logikfabrik.hiit.widget

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.RelativeLayout
import java.util.*

private const val MAX_PULSE_COUNT = 4

// The emitter emits a pulse each time its emit function is called.
class Emitter(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    private class Pulse(context: Context) : View(context) {
        private var radius = 0F
        private var centerX = 0F
        private var centerY = 0F

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

        override fun onSizeChanged(newWidth: Int, newHeight: Int, oldWidth: Int, oldHeight: Int) {
            super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight)

            radius = (minOf(newWidth, newHeight) - paint.strokeWidth) / 2.2F
            centerX = newWidth / 2F
            centerY = newHeight / 2F
        }

        override fun onDraw(canvas: Canvas) {
            canvas.drawCircle(centerX, centerY, radius, paint)
        }
    }

    private val animators: LinkedList<ObjectAnimator>

    init {
        // Create MAX_PULSE_COUNT pulses and add them to the layout.
        val pulses = (0..MAX_PULSE_COUNT).map {
            Pulse(context)
        }

        pulses.forEach { pulse ->
            addView(pulse)
        }

        // Create an animator for each pulse.
        animators = LinkedList(pulses.map { pulse -> createAnimator(pulse) })
    }

    private fun createAnimator(pulse: Pulse): ObjectAnimator {
        return ObjectAnimator.ofPropertyValuesHolder(
            pulse,
            PropertyValuesHolder.ofFloat(View.SCALE_X, 1F, 2F),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1F, 2F),
            PropertyValuesHolder.ofFloat(View.ALPHA, 1F, 0F)
        ).apply {
            duration = MAX_PULSE_COUNT * 1000L
            interpolator = DecelerateInterpolator()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        // Cancel all animators.
        animators.forEach { animator -> animator.cancel() }
    }

    fun emit() {
        // Start/restart one animator at a time, over and over again.
        val animator = animators.poll() ?: return

        animator.start()

        animators.add(animator)
    }
}
