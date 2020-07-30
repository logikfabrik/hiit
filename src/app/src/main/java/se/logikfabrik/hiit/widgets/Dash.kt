package se.logikfabrik.hiit.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

class Dash(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    init {
        setBackgroundColor(0xFF8D23C1.toInt())

        addView(Emitter(context))
        addView(
            Dial(context).apply {
                totalTime = 100
                totalTimeElapsed = 65

                currentTime = 100
                currentTimeElapsed = 15
            }
        )
        addView(
            Timer(context).apply {
                currentTime = 100
                currentTimeElapsed = 15

                numberOfSets = 3
                numberOfSetsElapsed = 2
            },
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                addRule(CENTER_IN_PARENT, TRUE)
            }
        )
    }
}
