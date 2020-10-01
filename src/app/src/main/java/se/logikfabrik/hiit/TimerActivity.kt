package se.logikfabrik.hiit

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import se.logikfabrik.hiit.widgets.Controls
import se.logikfabrik.hiit.widgets.Dash

class TimerActivity : AppCompatActivity() {
    private var dash: Dash? = null
    private var controls: Controls? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dash = Dash(this).apply { id = R.id.dash }
        controls = Controls(this)

        setContentView(
            LinearLayout(this).apply {
                addView(dash)
                addView(controls)
            }
        )

        if (savedInstanceState == null) {
            dash?.start(10, 5, 3)
        }
    }
}
