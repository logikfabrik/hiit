package com.example.hiit.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.NumberPicker
import com.example.hiit.R

open class MyTimePartPicker : NumberPicker {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        processXmlAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        processXmlAttributes(attrs, defStyleAttr)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        processXmlAttributes(attrs, defStyleAttr, defStyleRes)
    }

    private fun processXmlAttributes(attrs: AttributeSet, defStyleAttr: Int = 0, defStyleRes: Int = 0) {
        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.MyTimePartPicker, defStyleAttr, defStyleRes)

        try {
            minValue = maxOf(attributes.getInt(R.styleable.MyTimePartPicker_minValue, 0), 0)
            maxValue = maxOf(attributes.getInt(R.styleable.MyTimePartPicker_maxValue, 60), 0)
            value = maxOf(attributes.getInt(R.styleable.MyTimePartPicker_defaultValue, 0), 0)
        } finally {
            attributes.recycle()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        if(minValue >= maxValue) {
            return
        }

        val range = IntRange(minValue, maxValue)

        val values = range.map { value -> if(value < 10) "0$value" else "$value"; }.toTypedArray()

        displayedValues = values
    }

}