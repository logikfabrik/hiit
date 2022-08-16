package se.logikfabrik.hiit

import android.app.Service
import android.content.Intent
import android.os.*

class WorkoutCountDownService : Service() {

    private var timer: WorkoutCountDownTimer? = null
    private val binder = WorkoutCountDownServiceBinder(this)

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    inner class WorkoutCountDownServiceBinder(private val service: WorkoutCountDownService) :
        Binder() {
        fun getService(): WorkoutCountDownService = service
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    fun startNew(workout: WorkoutCountDownTimer.Workout) {
        timer?.stop()

        timer = WorkoutCountDownTimer(workout)
        timer?.start()
    }

    fun start() {
        timer?.start()
    }

    fun pause() {
        timer?.pause()
    }

    fun stop() {
        timer?.stop()
    }
}