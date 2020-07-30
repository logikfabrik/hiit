package se.logikfabrik.hiit.widgets

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.util.AttributeSet
import android.util.Log
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
        background = ShapeDrawable(android.graphics.drawable.shapes.RectShape())

        animatorSet = AnimatorSet().apply {
            interpolator = DecelerateInterpolator()

            playTogether(
                (0..4).map {
                    val pulse = Pulse(context)

                    Log.i(null, it.toString())

                    addView(pulse)

                    createAnimator(pulse).apply {
                        startDelay = it * 1000L
                    }
                }
            )
        }
    }

    private fun createAnimator(pulse: Pulse): AnimatorSet {
        return AnimatorSet().apply {
            duration = 5000

            playTogether(
                listOf(
                    ObjectAnimator.ofFloat(pulse, View.SCALE_X, 1F, 1.5F).apply {
                        repeatMode = ObjectAnimator.RESTART
                        repeatCount = ObjectAnimator.INFINITE
                    },
                    ObjectAnimator.ofFloat(pulse, View.SCALE_Y, 1F, 1.5F).apply {
                        repeatMode = ObjectAnimator.RESTART
                        repeatCount = ObjectAnimator.INFINITE
                    },
                    ObjectAnimator.ofFloat(pulse, View.ALPHA, 1F, 0F).apply {
                        repeatMode = ObjectAnimator.RESTART
                        repeatCount = ObjectAnimator.INFINITE
                    }
                )
            )
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
