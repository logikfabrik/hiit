package se.logikfabrik.hiit

import android.content.Context
import android.content.Intent

const val ACTION_START_SERVICE = "app.intent.action.START_SERVICE"
const val ACTION_STOP_SERVICE = "app.intent.action.STOP_SERVICE"

fun createIntent(packageContext: Context?, cls: Class<*>?, action: String): Intent {
    val intent = Intent(packageContext, cls)

    action.also { intent.action = it }

    return intent
}