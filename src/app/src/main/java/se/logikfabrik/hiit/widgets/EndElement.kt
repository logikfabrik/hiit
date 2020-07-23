package se.logikfabrik.hiit.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import se.logikfabrik.hiit.R

class EndElement(context: Context, attrs: AttributeSet) : View(context, attrs) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val min = (minOf(width, height) * 0.9).toFloat()

        canvas.drawRoundRect(
            (width - min),
            (height - min),
            min,
            min,
            (min / 2).toFloat(),
            (min / 2).toFloat(),
            Paint().apply { color = resources.getColor(R.color.purpleLight, null) }
        )
    }
}
