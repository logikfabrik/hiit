package se.logikfabrik.hiit.widgets

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextSwitcher
import android.widget.TextView

class Timer(context: Context) : LinearLayout(context) {

    private val currentTimeSwitcher: TextSwitcher
    private val numberOfSetsTextSwitcher: TextSwitcher

    var currentTime = 0
        set(value) {
            field = value.coerceAtLeast(0)

            updateCurrentTime()
        }

    var currentTimeElapsed = 0
        set(value) {
            field = value.coerceAtLeast(0)

            updateCurrentTime()
        }

    var numberOfSets = 1
        set(value) {
            field = value.coerceAtLeast(1)

            updateNumberOfSets()
        }

    var numberOfSetsElapsed = 0
        set(value) {
            field = value.coerceAtLeast(0)

            updateNumberOfSets()
        }

    init {
        orientation = VERTICAL

        val factory: () -> TextView = {
            TextView(context).apply {
                gravity = Gravity.CENTER
            }
        }

        currentTimeSwitcher = TextSwitcher(context).apply {
            setFactory(factory)
        }

        numberOfSetsTextSwitcher = TextSwitcher(context).apply {
            setFactory(factory)
        }

        addView(currentTimeSwitcher)
        addView(numberOfSetsTextSwitcher)
    }

    private fun updateCurrentTime() {
        val time = currentTime - currentTimeElapsed
        val minutes = time / 60
        val seconds = time % 60

        currentTimeSwitcher.setText("%02d:%02d".format(minutes, seconds))
    }

    private fun updateNumberOfSets() {
        Log.i(null, "Timer numberOfSets: $numberOfSets")
        Log.i(null, "Timer numberOfSetsElapsed: $numberOfSetsElapsed")

        val numberOfSetsElapsed = this.numberOfSetsElapsed + 1

        numberOfSetsTextSwitcher.setText("$numberOfSetsElapsed/$numberOfSets")
    }
}
