package se.logikfabrik.hiit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var startButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById(R.id.start_button)

        startButton?.setOnClickListener {
            val intent = Intent(this, TimerActivity::class.java)

            startActivity(intent)
        }
    }
}
