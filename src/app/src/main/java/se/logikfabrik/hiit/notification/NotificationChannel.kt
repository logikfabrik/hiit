package se.logikfabrik.hiit.notification

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi

private const val NOTIFICATION_CHANNEL_GROUP_ID = "WORKOUT_NOTIFICATION_CHANNEL_GROUP"
private const val NOTIFICATION_CHANNEL_GROUP_NAME = "Workout"

const val NOTIFICATION_CHANNEL_ID = "RUNNING_TIMER_NOTIFICATION_CHANNEL"
private const val NOTIFICATION_CHANNEL_NAME = "Running timer"

@RequiresApi(Build.VERSION_CODES.O)
fun createNotificationChannel(notificationManager: NotificationManager) {
    val group =
        NotificationChannelGroup(NOTIFICATION_CHANNEL_GROUP_ID, NOTIFICATION_CHANNEL_GROUP_NAME)

    notificationManager.createNotificationChannelGroup(group)

    val channel =
        NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )

    channel.group = NOTIFICATION_CHANNEL_GROUP_ID
    channel.setShowBadge(false)

    notificationManager.createNotificationChannel(channel)
}