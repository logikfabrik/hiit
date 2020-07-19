package com.example.hiit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val minPicker = this.findViewById<MyNumberPicker>(R.id.minMyNumberPicker);

        val range = IntRange(minPicker.minValue, minPicker.maxValue);

        val tmp = range.map { value -> if(value < 10) "0$value" else "$value"; }.toTypedArray();

        minPicker.displayedValues = tmp;
    }
}