package se.logikfabrik.hiit.widgets


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import se.logikfabrik.hiit.R

class MyTimePicker : LinearLayout {

    var minMinValue: Int = 0
    var maxMinValue: Int = 0

    var minSecValue: Int = 0
    var maxSecValue: Int = 0

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        inflater.inflate(R.layout.my_time_picker, this, true)
    }

}