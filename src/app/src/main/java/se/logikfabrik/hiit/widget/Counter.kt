package se.logikfabrik.hiit.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextSwitcher
import android.widget.TextView

class Counter(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private val textTextSwitcher: TextSwitcher
    private val setTimeTextSwitcher: TextSwitcher
    private val numberOfSetsTextSwitcher: TextSwitcher

    var text = ""
        set(value) {
            field = value

            updateText()
        }

    var stageTimeMillis = 0L
        set(value) {
            field = value.coerceAtLeast(0)

            updateStageTime()
        }

    var elapsedStageTimeMillis = 0L
        set(value) {
            field = value.coerceAtLeast(0)

            updateStageTime()
        }

    var numberOfSets = 1L
        set(value) {
            require(value >= 1)

            field = value

            updateNumberOfSets()
        }

    var elapsedNumberOfSets = 0L
        set(value) {
            require(value >= 0)

            field = value

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

    private fun updateStageTime() {
        val time = stageTimeMillis - elapsedStageTimeMillis
        val minutes = (time / SECOND_IN_MILLIS) / 60
        val seconds = (time / SECOND_IN_MILLIS) % 60

        setTimeTextSwitcher.setText("%02d:%02d".format(minutes, seconds))
    }

    private fun updateNumberOfSets() {
        val elapsedNumberOfSets = this.elapsedNumberOfSets + 1

        numberOfSetsTextSwitcher.setText("$elapsedNumberOfSets/$numberOfSets")
    }
}
