package se.logikfabrik.hiit.widgets

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextSwitcher
import android.widget.TextView

class Timer(context: Context) : LinearLayout(context) {

    private val textTextSwitcher: TextSwitcher
    private val currentTimeTextSwitcher: TextSwitcher
    private val numberOfSetsTextSwitcher: TextSwitcher

    var text = ""
        set(value) {
            field = value

            updateText()
        }

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

        textTextSwitcher = TextSwitcher(context).apply {
            setFactory(factory)
        }

        currentTimeTextSwitcher = TextSwitcher(context).apply {
            setFactory(factory)
        }

        numberOfSetsTextSwitcher = TextSwitcher(context).apply {
            setFactory(factory)
        }

        addView(textTextSwitcher)
        addView(currentTimeTextSwitcher)
        addView(numberOfSetsTextSwitcher)
    }

    private fun updateText() {
        textTextSwitcher.setText(text)
    }

    private fun updateCurrentTime() {
        val time = currentTime - currentTimeElapsed
        val minutes = time / 60
        val seconds = time % 60

        currentTimeTextSwitcher.setText("%02d:%02d".format(minutes, seconds))
    }

    private fun updateNumberOfSets() {
        val numberOfSetsElapsed = this.numberOfSetsElapsed + 1

        numberOfSetsTextSwitcher.setText("$numberOfSetsElapsed/$numberOfSets")
    }
}
