package se.logikfabrik.hiit.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.NumberPicker
import se.logikfabrik.hiit.R
import java.beans.PropertyChangeSupport

class MyTimePicker : LinearLayout {

    val propertyChangeSupport: PropertyChangeSupport = PropertyChangeSupport(this)

    var value: Int
        get() = ((minutesNumberPicker?.value ?: 0) * 60) + (secondsNumberPicker?.value ?: 0)
        set(value) {
            propertyChangeSupport.firePropertyChange("value", null, null)

            minutesNumberPicker?.value = value / 60
            secondsNumberPicker?.value = value % 60
        }

    private var minutesNumberPicker: NumberPicker? = null
    private var secondsNumberPicker: NumberPicker? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        inflater.inflate(R.layout.my_time_picker, this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        minutesNumberPicker = findViewById(R.id.minutes_numberPicker)
        secondsNumberPicker = findViewById(R.id.seconds_numberPicker)

        arrayOf(minutesNumberPicker, secondsNumberPicker).forEach {
            val minValue = 0
            val maxValue = 59

            it?.minValue = minValue
            it?.maxValue = maxValue

            val range = IntRange(minValue, maxValue)

            val values =
                range.map { value -> if (value < 10) "0$value" else "$value"; }.toTypedArray()

            it?.displayedValues = values

            it?.setOnValueChangedListener { _, _, _ ->
                propertyChangeSupport.firePropertyChange("value", null, null)
            }
        }

        minutesNumberPicker?.value = value / 60
        secondsNumberPicker?.value = value % 60
    }
}
