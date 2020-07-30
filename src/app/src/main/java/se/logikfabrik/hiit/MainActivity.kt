package se.logikfabrik.hiit

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import se.logikfabrik.hiit.widgets.Dial
import se.logikfabrik.hiit.widgets.Element
import se.logikfabrik.hiit.widgets.MyTimePicker
import se.logikfabrik.hiit.widgets.MyTimerStartAnimator

class MainActivity : AppCompatActivity() {

    private var timerMyTimer: Dial? = null

    private var workMyTimePicker: MyTimePicker? = null
    private var restMyTimePicker: MyTimePicker? = null
    private var setsNumberPicker: NumberPicker? = null

    private var startButton: Element? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.setBackgroundColor(resources.getColor(R.color.purpleLight, null))

        timerMyTimer = findViewById(R.id.timer_myTimer)

        timerMyTimer?.currentTime = 100
        timerMyTimer?.currentTimeElapsed = 50

        timerMyTimer?.totalTime = 200
        timerMyTimer?.totalTimeElapsed = 50

        val animator = MyTimerStartAnimator(timerMyTimer!!)

        animator.start()

        workMyTimePicker = findViewById(R.id.work_myTimePicker)
        workMyTimePicker?.value = 40

        restMyTimePicker = findViewById(R.id.rest_myTimePicker)
        restMyTimePicker?.value = 15

        arrayOf(workMyTimePicker, restMyTimePicker).forEach { it ->
            it?.propertyChangeSupport?.addPropertyChangeListener {
                if (it.propertyName != "value") {
                    return@addPropertyChangeListener
                }

                setValues()
            }
        }

        setsNumberPicker = findViewById(R.id.sets_numberPicker)
        setsNumberPicker?.minValue = 1
        setsNumberPicker?.maxValue = 99
        setsNumberPicker?.value = 1
        setsNumberPicker?.setOnValueChangedListener { _, _, _ ->
            setValues()
        }

        startButton = findViewById(R.id.shared_element)

        startButton?.setOnClickListener {
            val intent = Intent(this, TimerActivity::class.java)

            val options = ActivityOptions
                .makeSceneTransitionAnimation(this, it, "robot")
            // start the new activity

            (it as Element).doStuff()

            Handler().postDelayed(
                {
                    startActivity(intent, options.toBundle())
                },
                1000
            )

            // startActivity(intent, )
        }
    }

    private fun setValues() {
        val totalTime = (
            (workMyTimePicker?.value ?: 0) + (
                restMyTimePicker?.value
                    ?: 0
                )
            ) * (setsNumberPicker?.value ?: 0)

        Log.i(null, totalTime.toString())
    }
}
