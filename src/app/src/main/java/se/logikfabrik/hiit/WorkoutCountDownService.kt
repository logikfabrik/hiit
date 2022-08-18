package se.logikfabrik.hiit

import android.app.*
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import se.logikfabrik.hiit.notification.NOTIFICATION_ID
import se.logikfabrik.hiit.notification.getNotificationBuilder
import se.logikfabrik.hiit.notification.showNotification
import se.logikfabrik.hiit.widget.ArcScale

class WorkoutCountDownService : Service() {

    private var timer: WorkoutCountDownTimer? = null
    private var notification: Notification? = null
    private val binder = WorkoutCountDownServiceBinder(this)

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    inner class WorkoutCountDownServiceBinder(private val service: WorkoutCountDownService) :
        Binder() {
        fun getService(): WorkoutCountDownService = service
    }

    override fun onCreate() {
        super.onCreate()

        Toast.makeText(applicationContext, "Service started", Toast.LENGTH_SHORT).show()

        // TODO: Create separate service for notification
        val builder = getNotificationBuilder(this)

        this.notification = builder.build()

        startForeground(NOTIFICATION_ID, notification)

        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        Toast.makeText(applicationContext, "Service destroyed", Toast.LENGTH_SHORT).show()

        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onWorkoutTick(event: WorkoutCountDownTimer.WorkoutCountDownEvent) {
        val builder = getNotificationBuilder(this);

        builder.setContentTitle(event.countDown.totalTimeElapsed.toString())

        showNotification(this, builder.build())
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