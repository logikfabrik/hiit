package se.logikfabrik.hiit.core

import org.greenrobot.eventbus.EventBus

private const val TIMER_PERIOD_MILLIS = 100L

class Timer {
    private var timer: java.util.Timer? = null
    private var elapsedTimeMillis = 0L

    fun start() {
        timer?.cancel()

        timer = java.util.Timer()
        timer?.scheduleAtFixedRate(object : java.util.TimerTask() {
            override fun run() {
                elapsedTimeMillis += TIMER_PERIOD_MILLIS

                EventBus.getDefault().postSticky(TickEvent(elapsedTimeMillis))
            }
        }, 0, TIMER_PERIOD_MILLIS)

        EventBus.getDefault().postSticky(TickEvent(elapsedTimeMillis))
    }

    fun pause() {
        timer?.cancel()
    }

    fun stop() {
        timer?.cancel()

        elapsedTimeMillis = 0
    }
}