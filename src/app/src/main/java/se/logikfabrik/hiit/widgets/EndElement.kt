package se.logikfabrik.hiit.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class EndElement(context: Context, attrs: AttributeSet) : View(context, attrs) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        val min = (minOf(width, height) * 0.8).toFloat()

/*

        canvas.drawRect(
            0F,
            0F,
            width.toFloat(),
            height.toFloat(),
            Paint().apply { color = Color.RED })
*/

        canvas.drawRoundRect(
            (width - min),
            (height - min),
            min,
            min,
            (min / 2).toFloat(),
            (min / 2).toFloat(),
            Paint().apply { color = Color.GREEN }
        )
    }
}