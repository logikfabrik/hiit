package se.logikfabrik.hiit

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import se.logikfabrik.hiit.core.NotificationService
import se.logikfabrik.hiit.core.TickEvent
import se.logikfabrik.hiit.core.TimerService
import se.logikfabrik.hiit.models.Workout
import se.logikfabrik.hiit.models.getElapsedStageTimeMillis
import se.logikfabrik.hiit.models.getElapsedNumberOfSets
import se.logikfabrik.hiit.models.getStageTimeMillis
import se.logikfabrik.hiit.widget.Counter
import se.logikfabrik.hiit.widget.Dial
import se.logikfabrik.hiit.widget.Emitter
import se.logikfabrik.hiit.widget.SECOND_IN_MILLIS

class TimerActivity : AppCompatActivity() {
    private var service: TimerService? = null
    private lateinit var emitter: Emitter
    private lateinit var dial: Dial
    private lateinit var counter: Counter

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            service = (binder as TimerService.ServiceBinder).getService()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            service = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        EventBus.getDefault().register(this)

        setContentView(R.layout.activity_timer)

        emitter = findViewById(R.id.emitter)
        dial = findViewById(R.id.dial)
        counter = findViewById(R.id.counter)
    }

    override fun onDestroy() {
        super.onDestroy()

        EventBus.getDefault().unregister(this)
    }

    override fun onStart() {
        super.onStart()

        val intent = createIntent(this, TimerService::class.java, ACTION_START_SERVICE)

        intent.also { intent ->
            startService(intent)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()

        unbindService(connection)
    }

    override fun onPause() {
        super.onPause()

        startNotificationService()
    }

    override fun onResume() {
        super.onResume()

        stopNotificationService()
    }

    private fun startNotificationService() {
        val intent = createIntent(this, NotificationService::class.java, ACTION_START_SERVICE)

        intent.also { intent ->
            startService(intent)
        }
    }

    private fun stopNotificationService() {
        val intent = createIntent(this, NotificationService::class.java, ACTION_STOP_SERVICE)

        intent.also { intent ->
            startService(intent)
        }
    }

    private fun updateDial(elapsedTimeMillis: Long) {
        val workout = Workout(10000, 3000, 15)

        dial.timeMillis = workout.timeMillis
        dial.elapsedTimeMillis = elapsedTimeMillis
        dial.stageTimeMillis = workout.getStageTimeMillis(elapsedTimeMillis)
        dial.elapsedStageTimeMillis = workout.getElapsedStageTimeMillis(elapsedTimeMillis)

        if (elapsedTimeMillis % SECOND_IN_MILLIS == 0L) {
            emitter.emit()
        }

        counter.stageTimeMillis = workout.getStageTimeMillis(elapsedTimeMillis)
        counter.elapsedStageTimeMillis = workout.getElapsedStageTimeMillis(elapsedTimeMillis)
        counter.numberOfSets = workout.numberOfSets
        counter.elapsedNumberOfSets = workout.getElapsedNumberOfSets(elapsedTimeMillis)
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

    fun onCloseButtonClick(button: View) {
        finish()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTick(event: TickEvent) {
        updateDial(event.elapsedTimeMillis)
    }
}