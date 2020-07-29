package se.logikfabrik.hiit.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import se.logikfabrik.hiit.R

class Pulse(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var _startR = 0F
    private var _endR = 0F
    private var _cx = 0F
    private var _cy = 0F

    private val _pulseGap = 30F

    private val _pulsePaint = Paint().apply {
        isAntiAlias = true
        color = resources.getColor(R.color.greyDark, null)
        style = Paint.Style.STROKE
        strokeWidth = 3F
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        _cx = minOf(w, h) / 2.toFloat()
        _cy = _cx

        _endR = maxOf(w, h).toFloat()
        _startR = _endR / _pulseGap
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var currentR = _startR + 0F

        while (currentR < _endR) {
            canvas.drawCircle(_cx, _cy, currentR, _pulsePaint)
            currentR += _pulseGap
        }
    }
}
