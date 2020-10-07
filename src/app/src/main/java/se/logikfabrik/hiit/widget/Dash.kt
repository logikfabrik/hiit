package se.logikfabrik.hiit.widget

import android.content.Context
import android.os.CountDownTimer
import android.os.Parcel
import android.os.Parcelable
import android.widget.RelativeLayout

class Dash(context: Context) : RelativeLayout(context) {

    private var workTime = 0
    private var restTime = 0
    private var numberOfSets = 0
    private var totalTime = 0
    private var totalTimeElapsed = 0
    private var currentTimeAngleDirection = Direction.WIDENING

    fun start(workTime: Int, restTime: Int, numberOfSets: Int) {

        this.workTime = workTime.coerceAtLeast(0)
        this.restTime = restTime.coerceAtLeast(0)
        this.numberOfSets = numberOfSets.coerceAtLeast(0)

        // Only rest between work, and not at the very end
        totalTime = ((this.workTime + this.restTime) * this.numberOfSets) - this.restTime

        val stopped = totalTime == totalTimeElapsed

        dial.totalTime = 0
        dial.totalTimeElapsed = 0

        dial.currentTime = 0
        dial.currentTimeElapsed = 0

        counter.currentTime = if (!stopped) {
            this.workTime
        } else {
            0
        }
        counter.currentTimeElapsed = 0

        counter.numberOfSets = this.numberOfSets
        counter.numberOfSetsElapsed = if (stopped) {
            this.numberOfSets - 1
        } else {
            0
        }

        if (stopped) {
            return
        }

        countDownTimer = object : CountDownTimer((totalTime - totalTimeElapsed) * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                totalTimeElapsed = totalTime - (millisUntilFinished / 1000).toInt()

                var numberOfSetsElapsed =
                    totalTimeElapsed / (this@Dash.workTime + this@Dash.restTime)

                if (totalTimeElapsed % (this@Dash.workTime + this@Dash.restTime) == 0) {
                    numberOfSetsElapsed--
                }

                var currentTimeElapsed =
                    totalTimeElapsed - (numberOfSetsElapsed * (this@Dash.workTime + this@Dash.restTime))

                val currentTime: Int
                val text: String

                if (currentTimeElapsed <= this@Dash.workTime) {
                    text = "Work"
                    currentTime = this@Dash.workTime
                } else {
                    text = "Rest"
                    currentTime = this@Dash.restTime
                    currentTimeElapsed = (currentTimeElapsed - this@Dash.workTime)
                }

                dial.totalTime = totalTime
                dial.totalTimeElapsed = totalTimeElapsed
                dial.totalTimeAngleDirection = Direction.NARROWING

                if (currentTimeElapsed == 1) {
                    currentTimeAngleDirection =
                        if (currentTimeAngleDirection == Direction.NARROWING) Direction.WIDENING else Direction.NARROWING
                }

                dial.currentTime = currentTime
                dial.currentTimeElapsed = currentTimeElapsed
                dial.currentTimeAngleDirection = currentTimeAngleDirection

                counter.text = text

                counter.currentTime = currentTime
                counter.currentTimeElapsed = currentTimeElapsed

                counter.numberOfSetsElapsed = numberOfSetsElapsed

                emitter.emit()
            }

            override fun onFinish() {
                counter.text = "Done"
            }
        }.start()
    }

    fun resume() {
        countDownTimer?.cancel()

        start(workTime, restTime, numberOfSets)
    }

    fun stop() {
        countDownTimer?.cancel()

        totalTimeElapsed = 0
    }

    private val emitter: Emitter

    private val dial: Dial

    private val counter: Counter

    private var countDownTimer: CountDownTimer? = null

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

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        countDownTimer?.cancel()
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState() ?: return null

        val savedState = SavedState(superState)

        savedState.workTime = workTime
        savedState.restTime = restTime
        savedState.numberOfSets = numberOfSets
        savedState.totalTimeElapsed = totalTimeElapsed
        savedState.currentTimeAngleDirection = currentTimeAngleDirection

        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val savedState = state as SavedState

        super.onRestoreInstanceState(savedState.superState)

        workTime = savedState.workTime
        restTime = savedState.restTime
        numberOfSets = savedState.numberOfSets
        totalTimeElapsed = savedState.totalTimeElapsed
        currentTimeAngleDirection = savedState.currentTimeAngleDirection

        resume()
    }

    internal class SavedState : BaseSavedState {
        var workTime = 0
        var restTime = 0
        var numberOfSets = 0
        var totalTimeElapsed = 0
        var currentTimeAngleDirection = Direction.WIDENING

        constructor(source: Parcel) : super(source) {
            workTime = source.readInt()
            restTime = source.readInt()
            numberOfSets = source.readInt()
            totalTimeElapsed = source.readInt()
            currentTimeAngleDirection = Direction.values()[source.readInt()]
        }

        constructor(superState: Parcelable) : super(superState)

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)

            out.writeInt(workTime)
            out.writeInt(restTime)
            out.writeInt(numberOfSets)
            out.writeInt(totalTimeElapsed)
            out.writeInt(currentTimeAngleDirection.ordinal)
        }

        companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel): SavedState {
                    return SavedState(source)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}
