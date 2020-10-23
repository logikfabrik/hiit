package se.logikfabrik.hiit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import se.logikfabrik.hiit.widget.Dash

class TimerActivity : AppCompatActivity() {
    private var dash: Dash? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_timer)

        dash = findViewById(R.id.dash)

        if (savedInstanceState == null) {
            dash?.start(10, 5, 3)
        }
    }
}
