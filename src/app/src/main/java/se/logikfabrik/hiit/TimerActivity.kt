package se.logikfabrik.hiit

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class TimerActivity : AppCompatActivity() {

    private var service: WorkoutCountDownService? = null
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            service = (binder as WorkoutCountDownService.WorkoutCountDownServiceBinder).getService()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            service = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
    }

    override fun onStart() {
        super.onStart()

        Intent(this, WorkoutCountDownService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()

        unbindService(connection)
    }

    fun onStartNewButtonClick(button: View) {
        service?.startNew(WorkoutCountDownTimer.Workout(60, 10, 3))
    }

    fun onStartButtonClick(button: View) {
        service?.start()
    }

    fun onPauseButtonClick(button: View) {
        service?.pause()
    }

    fun onStopButtonClick(button: View) {
        service?.stop()
    }
}
