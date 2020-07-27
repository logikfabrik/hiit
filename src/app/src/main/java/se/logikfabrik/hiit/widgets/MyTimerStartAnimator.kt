package se.logikfabrik.hiit.widgets

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.res.Resources
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

class MyTimerStartAnimator {

    private val _animator: ValueAnimator = ValueAnimator()

    constructor(timer: MyTimer) {
        val currentTimeHolder = PropertyValuesHolder.ofInt(
            "CURRENT_TIME_ELAPSED",
            timer.currentTime,
            timer.currentTimeElapsed
        )
        val totalTimeHolder = PropertyValuesHolder.ofInt(
            "TOTAL_TIME_ELAPSED",
            timer.totalTime,
            timer.totalTimeElapsed
        )

        _animator.interpolator = FastOutSlowInInterpolator()
        _animator.setValues(currentTimeHolder, totalTimeHolder)
        _animator.duration =
            Resources.getSystem().getInteger(android.R.integer.config_longAnimTime).toLong()
        _animator.repeatCount = 0

        _animator.addUpdateListener { animator ->
            timer.currentTimeElapsed = animator.getAnimatedValue("CURRENT_TIME_ELAPSED") as Int
            timer.totalTimeElapsed = animator.getAnimatedValue("TOTAL_TIME_ELAPSED") as Int
        }
    }

    fun start() {
        _animator.start()
    }
}
