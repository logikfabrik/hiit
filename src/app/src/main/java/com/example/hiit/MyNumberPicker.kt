package com.example.hiit

import android.content.Context
import android.util.AttributeSet
import android.widget.NumberPicker

class MyNumberPicker : NumberPicker {

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
        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.MyNumberPicker, defStyleAttr, defStyleRes)

        try {
            this.minValue = attributes.getInt(R.styleable.MyNumberPicker_minValue, 0)
            this.maxValue = attributes.getInt(R.styleable.MyNumberPicker_maxValue, 0)
            this.value = attributes.getInt(R.styleable.MyNumberPicker_defaultValue, 0)
        } finally {
            attributes.recycle()
        }
    }

}