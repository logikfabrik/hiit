package se.logikfabrik.hiit.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

class Dash(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    init {
        addView(Emitter(context))
        addView(
            Dial(context).apply {
                totalTime = 200
                totalTimeElapsed = 50

                currentTime = 100
                currentTimeElapsed = 50
            }
        )
    }
}
