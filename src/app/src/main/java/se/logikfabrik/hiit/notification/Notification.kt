package se.logikfabrik.hiit.notification

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import se.logikfabrik.hiit.R

const val NOTIFICATION_ID = 1

private const val NOTIFICATION_TITLE = "Timer running..."

fun getNotificationBuilder(context: Context): NotificationCompat.Builder {
    return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.drawable.rectangle)
        .setContentTitle("WORK")
        .setContentText("05:32")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setCategory(NotificationCompat.CATEGORY_WORKOUT)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .setAutoCancel(true)
        .setProgress(100, 32, false)
        .setOngoing(true)
}

fun showNotification(context: Context, notification: Notification) {
    with(NotificationManagerCompat.from(context)) {
        notify(NOTIFICATION_ID, notification)
    }
}


