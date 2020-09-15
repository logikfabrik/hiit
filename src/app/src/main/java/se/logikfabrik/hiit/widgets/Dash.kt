package se.logikfabrik.hiit.widgets

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.widget.RelativeLayout

class Dash(context: Context) : RelativeLayout(context) {

    private var workTime = 0
    private var restTime = 0
    private var numberOfSets = 0
    private var totalTime = 0

    fun start(workTime: Int, restTime: Int, numberOfSets: Int) {

        this.workTime = workTime.coerceAtLeast(0)
        this.restTime = restTime.coerceAtLeast(0)
        this.numberOfSets = numberOfSets.coerceAtLeast(0)

        // Only rest between work, and not at the very end
        totalTime = ((this.workTime + this.restTime) * this.numberOfSets) - this.restTime

        timer.currentTime = this.workTime
        timer.currentTimeElapsed = 0

        timer.numberOfSets = this.numberOfSets
        timer.numberOfSetsElapsed = 0

        // Reset (cancel)
        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(totalTime * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val totalTimeElapsed = totalTime - (millisUntilFinished / 1000).toInt()

                dial.totalTime = totalTime
                dial.totalTimeElapsed = totalTimeElapsed

                Log.i(null, "Total time: $totalTime")
                Log.i(null, "Total time elapsed: $totalTimeElapsed")

                var numberOfSetsElapsed =
                    totalTimeElapsed / (this@Dash.workTime + this@Dash.restTime)

                if (totalTimeElapsed % (this@Dash.workTime + this@Dash.restTime) == 0) {
                    numberOfSetsElapsed--
                }

                Log.i(null, "Number of sets: $(this@Dash.numberOfSets)")
                Log.i(null, "Number of sets elapsed: $numberOfSetsElapsed")

                var currentTimeElapsed =
                    totalTimeElapsed - (numberOfSetsElapsed * (this@Dash.workTime + this@Dash.restTime))

                val currentTime: Int

                if (currentTimeElapsed <= this@Dash.workTime) {
                    currentTime = this@Dash.workTime

                } else {
                    currentTime = this@Dash.restTime
                    currentTimeElapsed = (currentTimeElapsed - this@Dash.workTime)
                }

                Log.i(null, "Current time: $currentTime")
                Log.i(null, "Current time elapsed: $currentTimeElapsed")



                Log.i(null, "--------------------")

                timer.currentTime = currentTime
                timer.currentTimeElapsed = currentTimeElapsed

                //timer.numberOfSets = this@Dash.numberOfSets
                timer.numberOfSetsElapsed = numberOfSetsElapsed

                emitter.emit()
            }

            override fun onFinish() {
            }
        }.start()
    }

    fun stop() {
        countDownTimer?.cancel()
    }

    private val emitter: Emitter

    private val dial: Dial

    private val timer: Timer

    private var countDownTimer: CountDownTimer? = null

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
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        countDownTimer?.cancel()
    }
}
