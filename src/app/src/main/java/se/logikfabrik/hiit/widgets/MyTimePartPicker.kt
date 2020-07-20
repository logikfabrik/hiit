package se.logikfabrik.hiit.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.NumberPicker
import se.logikfabrik.hiit.R

open class MyTimePartPicker : NumberPicker {

    override fun setMinValue(minValue: Int) {
        super.setMinValue(minValue)

        refreshValues()
    }

    override fun setMaxValue(maxValue: Int) {
        super.setMaxValue(maxValue)

        refreshValues()
    }

    override fun setValue(value: Int) {
        super.setValue(value)

        refreshValues()
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setXmlAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setXmlAttributes(attrs, defStyleAttr)
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
            minValue = maxOf(attributes.getInt(R.styleable.picker_minValue, 0), 0)
            maxValue = maxOf(attributes.getInt(R.styleable.picker_maxValue, 60), 0)
            value = maxOf(attributes.getInt(R.styleable.picker_defaultValue, 0), 0)
        } finally {
            attributes.recycle()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        refreshValues()
    }

    private fun refreshValues() {
        if (minValue >= maxValue) {
            return
        }

        val range = IntRange(minValue, maxValue)

        val values = range.map { value -> if (value < 10) "0$value" else "$value"; }.toTypedArray()

        displayedValues = values
    }
}
