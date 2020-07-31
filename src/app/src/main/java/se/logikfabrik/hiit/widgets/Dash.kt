package se.logikfabrik.hiit.widgets

import android.content.Context
import android.widget.RelativeLayout

class Dash(context: Context) : RelativeLayout(context) {
    fun setTotalTime(totalTime: Int) {
        dial.totalTime = totalTime
    }

    fun setTotalTimeElapsed(totalTimeElapsed: Int) {
        dial.totalTimeElapsed = totalTimeElapsed
    }

    fun setCurrentTime(currentTime: Int) {
        dial.currentTime = currentTime
        timer.currentTime = currentTime
    }

    fun setCurrentTimeElapsed(currentTimeElapsed: Int) {
        dial.currentTimeElapsed = currentTimeElapsed
        timer.currentTimeElapsed = currentTimeElapsed
    }

    fun setNumberOfSets(numberOfSets: Int) {
        timer.numberOfSets = numberOfSets
    }

    fun setNumberOfSetsElapsed(numberOfSetsElapsed: Int) {
        timer.numberOfSetsElapsed = numberOfSetsElapsed
    }

    private val emitter: Emitter
    private val dial: Dial
    private val timer: Timer

    init {
        setBackgroundColor(0xFF8D23C1.toInt())

        emitter = Emitter(context)
        dial = Dial(context)
        timer = Timer(context)

        addView(emitter)
        addView(dial)
        addView(
            timer,
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                addRule(CENTER_IN_PARENT, TRUE)
            }
        )

        // TODO: Set from parent
        setTotalTime(100)
        setTotalTimeElapsed(65)
        setCurrentTime(100)
        setCurrentTimeElapsed(15)
        setNumberOfSets(3)
        setNumberOfSetsElapsed(2)
    }
}
