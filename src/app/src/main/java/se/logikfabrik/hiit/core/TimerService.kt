package se.logikfabrik.hiit.core

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class TimerService : Service() {
    private var timer: Timer? = null
    private val binder = ServiceBinder(this)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    inner class ServiceBinder(private val service: TimerService) : Binder() {
        fun getService(): TimerService = service
    }

    fun start() {
        timer?.stop()

        timer = Timer()
        timer?.start()
    }

    fun pause() {
        timer?.pause()
    }

    fun stop() {
        timer?.stop()
    }
}