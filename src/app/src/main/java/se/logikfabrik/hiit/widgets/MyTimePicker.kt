package se.logikfabrik.hiit.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import se.logikfabrik.hiit.R

class MyTimePicker : LinearLayout {

    var minDefaultValue: Int = 0

    var secDefaultValue: Int = 0

    private lateinit var minTimePartPicker: MyTimePartPicker
    private lateinit var secTimePartPicker: MyTimePartPicker

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setXmlAttributes(attrs)

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        inflater.inflate(R.layout.my_time_picker, this, true)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setXmlAttributes(attrs, defStyleAttr)

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        inflater.inflate(R.layout.my_time_picker, this, true)
    }

    private fun setXmlAttributes(
        attrs: AttributeSet,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
    ) {
        val attributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.time_picker,
            defStyleAttr,
            defStyleRes
        )

        try {
            minDefaultValue =
                maxOf(attributes.getInt(R.styleable.time_picker_minDefaultValue, 0), 0)
            secDefaultValue =
                maxOf(attributes.getInt(R.styleable.time_picker_secDefaultValue, 0), 0)
        } finally {
            attributes.recycle()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        minTimePartPicker = findViewById<MyTimePartPicker>(R.id.min_time_part_picker)
        minTimePartPicker.value = minDefaultValue

        secTimePartPicker = findViewById<MyTimePartPicker>(R.id.sec_time_part_picker)
        secTimePartPicker.value = secDefaultValue
    }
}
