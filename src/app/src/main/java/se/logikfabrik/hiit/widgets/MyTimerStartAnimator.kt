package se.logikfabrik.hiit.widgets

import android.animation.ValueAnimator

class MyTimerStartAnimator {

    private val _animator: ValueAnimator = ValueAnimator()

    constructor(timer: Dial) {
        /*
        val currentTimeHolder = PropertyValuesHolder.ofFloat(
            "CURRENT_TIME_ELAPSED",
            timer.currentTime,
            timer.currentTimeElapsed
        )
        val totalTimeHolder = PropertyValuesHolder.ofFloat(
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
            timer.currentTimeElapsed = animator.getAnimatedValue("CURRENT_TIME_ELAPSED") as Float
            timer.totalTimeElapsed = animator.getAnimatedValue("TOTAL_TIME_ELAPSED") as Float
        }

         */
    }

    fun start() {
        // _animator.start()
    }
}
