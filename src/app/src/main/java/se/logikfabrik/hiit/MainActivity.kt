package se.logikfabrik.hiit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import se.logikfabrik.hiit.widgets.MyTimePicker

class MainActivity : AppCompatActivity() {

    private var workMyTimePicker: MyTimePicker? = null
    private var restMyTimePicker: MyTimePicker? = null
    private var setsNumberPicker: NumberPicker? = null

    private var startButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        startButton = findViewById(R.id.start_button)

        startButton?.setOnClickListener {
            val intent = Intent(this, TimerActivity::class.java)

            startActivity(intent)
        }
    }

    private fun setValues() {
        val totalTime = ((workMyTimePicker?.value ?: 0) + (restMyTimePicker?.value ?: 0)) * (setsNumberPicker?.value ?: 0)

        Log.i(null, totalTime.toString())
    }
}
