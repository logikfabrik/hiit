package se.logikfabrik.hiit

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import se.logikfabrik.hiit.widgets.Dash

class TimerActivity : AppCompatActivity() {
    private var dash: Dash? = null
    private var currentTimeTimer: CountDownTimer? = null

    private val tmpCurrentTime = 10 * 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dash = Dash(this)

        currentTimeTimer = object : CountDownTimer(tmpCurrentTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                dash?.setCurrentTime((tmpCurrentTime / 1000).toInt())
                dash?.setCurrentTimeElapsed(((tmpCurrentTime - millisUntilFinished) / 1000).toInt())
            }

            override fun onFinish() {
                dash?.setCurrentTimeElapsed((tmpCurrentTime / 1000).toInt())
            }
        }

        setContentView(
            LinearLayout(this).apply {
                addView(dash)
            }
        )

        currentTimeTimer?.start()
    }
}
