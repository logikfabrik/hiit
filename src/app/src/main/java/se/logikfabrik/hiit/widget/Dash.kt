package se.logikfabrik.hiit.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import se.logikfabrik.hiit.WorkoutCountDownTimer

class Dash(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    private var currentTimeArcScale = ArcScale.GROWING

    private val emitter: Emitter
    private val dial: Dial
    private val counter: Counter

    init {
        setBackgroundColor(0xFF8D23C1.toInt())

        emitter = Emitter(context)
        dial = Dial(context)
        counter = Counter(context)

        addView(emitter)
        addView(dial)
        addView(
            counter,
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                addRule(CENTER_IN_PARENT, TRUE)
            }
        )
    }

    @Subscribe
    fun onWorkoutTick(event: WorkoutCountDownTimer.WorkoutCountDownEvent) {
        dial.totalTime = event.workout.totalTime
        dial.totalTimeElapsed = event.countDown.totalTimeElapsed
        dial.totalTimeArcScale = ArcScale.SHRINKING

        dial.setTime = event.setTime
        dial.setTimeElapsed = event.setTimeElapsed

        if (event.setTimeElapsed == 1) {
            currentTimeArcScale =
                if (currentTimeArcScale == ArcScale.SHRINKING) ArcScale.GROWING else ArcScale.SHRINKING
        }

        dial.setTimeArcScale = currentTimeArcScale

        counter.setTime = event.setTime
        counter.setTimeElapsed = event.setTimeElapsed
        counter.numberOfSets = event.workout.numberOfSets
        counter.numberOfSetsElapsed = event.numberOfSetsElapsed

        counter.text = if (event.setTimeElapsed <= event.workout.workTime) {
            "Work"
        } else {
            "Rest"
        }

        emitter.emit()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        EventBus.getDefault().register(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        EventBus.getDefault().unregister(this)
    }
}
