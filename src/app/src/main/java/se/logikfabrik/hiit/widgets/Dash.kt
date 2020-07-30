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
    }
}
