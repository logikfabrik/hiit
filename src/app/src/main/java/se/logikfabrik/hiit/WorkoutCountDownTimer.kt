package se.logikfabrik.hiit

import android.os.CountDownTimer
import android.util.Log
import org.greenrobot.eventbus.EventBus

class WorkoutCountDownTimer(private val workout: Workout) {
    private var countDownTimer: CountDownTimer? = null
    private val countDown = CountDown()

    var state: State? = null;

    data class Workout(
        val workTime: Int,
        val restTime: Int,
        val numberOfSets: Int,
        val totalTime: Int = ((workTime + restTime) * numberOfSets) - restTime,
    )

    data class CountDown(
        var totalTimeElapsed: Int = 0,
    )

    class WorkoutCountDownEvent(val workout: Workout, val countDown: CountDown) {
        val numberOfSetsElapsed: Int
        val setTime: Int
        val setTimeElapsed: Int

        init {
            var numberOfSetsElapsed =
                countDown.totalTimeElapsed / (workout.workTime + workout.restTime)

            if (countDown.totalTimeElapsed % (workout.workTime + workout.restTime) == 0) {
                numberOfSetsElapsed--
            }

            this.numberOfSetsElapsed = numberOfSetsElapsed

            val setTime: Int
            var setTimeElapsed =
                countDown.totalTimeElapsed - (numberOfSetsElapsed * (workout.workTime + workout.restTime))

            if (setTimeElapsed <= workout.workTime) {
                setTime = workout.workTime
            } else {
                setTime = workout.restTime
                setTimeElapsed -= workout.workTime
            }

            this.setTime = setTime
            this.setTimeElapsed = setTimeElapsed
        }
    }

    class WorkoutStateEvent(val workout: Workout, val state: State)

    enum class State {
        STARTED,
        PAUSED,
        STOPPED,
    }

    fun start() {
        val finished = workout.totalTime == countDown.totalTimeElapsed

        if (finished) {
            return
        }

        countDownTimer?.cancel()

        countDownTimer = object :
            CountDownTimer((workout.totalTime - countDown.totalTimeElapsed) * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countDown.totalTimeElapsed =
                    workout.totalTime - (millisUntilFinished / 1000).toInt()

                EventBus.getDefault().post(WorkoutCountDownEvent(workout, countDown))
            }

            override fun onFinish() {
                state = State.STOPPED
            }
        }.start()

        state = State.STARTED
    }

    fun pause() {
        if (countDownTimer == null) {
            return
        }

        countDownTimer?.cancel()

        state = State.PAUSED
    }

    fun stop() {
        if (countDownTimer == null) {
            return
        }

        countDownTimer?.cancel()

        countDown.totalTimeElapsed = 0

        state = State.STOPPED

        // EventBus.getDefault().post(WorkoutStopEvent(workout))
    }
}