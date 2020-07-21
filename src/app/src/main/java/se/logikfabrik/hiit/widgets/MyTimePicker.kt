package se.logikfabrik.hiit.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.NumberPicker
import se.logikfabrik.hiit.R

class MyTimePicker : LinearLayout {

    private var defaultValue: Int = 0

    private var minutesNumberPicker: NumberPicker? = null
    private var secondsNumberPicker: NumberPicker? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        readXmlAttributes(attrs)

        inflate()
    }

    private fun readXmlAttributes(
        attrs: AttributeSet,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
    ) {
        val attributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.myTimePicker,
            defStyleAttr,
            defStyleRes
        )

        try {
            defaultValue =
                maxOf(attributes.getInt(R.styleable.myTimePicker_defaultValue, 0), 0)
        } finally {
            attributes.recycle()
        }
    }

    private fun inflate() {
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
        }

        minutesNumberPicker?.value = defaultValue / 60
        secondsNumberPicker?.value = defaultValue % 60
    }
}
