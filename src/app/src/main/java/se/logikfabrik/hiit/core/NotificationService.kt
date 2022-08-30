package se.logikfabrik.hiit.core

import android.app.Service
import android.content.Intent
import android.os.IBinder
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import se.logikfabrik.hiit.ACTION_START_SERVICE
import se.logikfabrik.hiit.ACTION_STOP_SERVICE
import se.logikfabrik.hiit.notification.NOTIFICATION_ID
import se.logikfabrik.hiit.notification.getNotificationBuilder
import se.logikfabrik.hiit.notification.showNotification

class NotificationService : Service() {
    override fun onCreate() {
        super.onCreate()

        EventBus.getDefault().register(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START_SERVICE -> {
                startForeground(NOTIFICATION_ID, getNotificationBuilder(this).build())
            }
            ACTION_STOP_SERVICE -> {
                stopForeground(true)
                stopSelf()
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        EventBus.getDefault().unregister(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @Subscribe
    fun onTick(event: TickEvent) {
        updateNotification(event.elapsedTimeMillis)
    }

    private fun updateNotification(elapsedTimeMillis: Long) {
        val builder = getNotificationBuilder(this);

        builder.setContentTitle(elapsedTimeMillis.toString())

        showNotification(this, builder.build())
    }
}