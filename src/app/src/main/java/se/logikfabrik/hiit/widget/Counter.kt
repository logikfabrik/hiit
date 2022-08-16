package se.logikfabrik.hiit.widget

import android.content.Context
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextSwitcher
import android.widget.TextView

class Counter(context: Context) : LinearLayout(context) {

    private val textTextSwitcher: TextSwitcher
    private val setTimeTextSwitcher: TextSwitcher
    private val numberOfSetsTextSwitcher: TextSwitcher

    var text = ""
        set(value) {
            field = value

            updateText()
        }

    var setTime = 0
        set(value) {
            field = value.coerceAtLeast(0)

            updateSetTime()
        }

    var setTimeElapsed = 0
        set(value) {
            field = value.coerceAtLeast(0)

            updateSetTime()
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

        setTimeTextSwitcher = TextSwitcher(context).apply {
            setFactory(factory)
        }

        numberOfSetsTextSwitcher = TextSwitcher(context).apply {
            setFactory(factory)
        }

        addView(textTextSwitcher)
        addView(setTimeTextSwitcher)
        addView(numberOfSetsTextSwitcher)
    }

    private fun updateText() {
        textTextSwitcher.setText(text)
    }

    private fun updateSetTime() {
        val time = setTime - setTimeElapsed
        val minutes = time / 60
        val seconds = time % 60

        setTimeTextSwitcher.setText("%02d:%02d".format(minutes, seconds))
    }

    private fun updateNumberOfSets() {
        val numberOfSetsElapsed = this.numberOfSetsElapsed + 1

        numberOfSetsTextSwitcher.setText("$numberOfSetsElapsed/$numberOfSets")
    }
}
