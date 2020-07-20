package se.logikfabrik.hiit.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import se.logikfabrik.hiit.R

class MyTimePicker : LinearLayout {

    var defaultValue: Int = 0
        get() {
            return field
        }
        set(value) {
            field = value

            minDefaultValue = field / 60
            secDefaultValue = field % 60

            refreshValues()
        }

    private var minDefaultValue: Int = 0
    private var secDefaultValue: Int = 0

    private var minTimePartPicker: MyTimePartPicker? = null
    private var secTimePartPicker: MyTimePartPicker? = null

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
            R.styleable.picker,
            defStyleAttr,
            defStyleRes
        )

        try {
            defaultValue =
                maxOf(attributes.getInt(R.styleable.picker_defaultValue, 0), 0)
        } finally {
            attributes.recycle()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        refreshValues()
    }

    private fun refreshValues() {
        minTimePartPicker = minTimePartPicker ?: findViewById(R.id.min_time_part_picker)
        minTimePartPicker?.value = minDefaultValue

        secTimePartPicker = secTimePartPicker ?: findViewById(R.id.sec_time_part_picker)
        secTimePartPicker?.value = secDefaultValue
    }
}
