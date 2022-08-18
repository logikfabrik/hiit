package se.logikfabrik.hiit

import android.app.Application
import android.app.NotificationManager
import android.os.Build
import se.logikfabrik.hiit.notification.createNotificationChannel

class TimerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(manager)
    }
}