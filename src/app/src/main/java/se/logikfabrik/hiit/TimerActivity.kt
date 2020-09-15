package se.logikfabrik.hiit

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import se.logikfabrik.hiit.widgets.Dash

class TimerActivity : AppCompatActivity() {
    private var dash: Dash? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dash = Dash(this)

        setContentView(
            LinearLayout(this).apply {
                addView(dash)
            }
        )

            dash?.start(10, 5, 3)
    }
}
