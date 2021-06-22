package com.hosseinkurd.arrowstepper.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class ShitItem @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(
    context,
    attributeSet,
    defStyleAttr
) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        /*val x: Int = (width * 0.09).toInt()
        val y: Int = (height * 0.5).toInt()

        val paint = Paint()
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true

        val p1 = Point(0, 0)
        val p2 = Point(x, y)
        val p3 = Point(0, height)

        val path = Path()
        path.fillType = Path.FillType.EVEN_ODD
        path.moveTo(p1.x.toFloat(), p1.y.toFloat())
        path.lineTo(p2.x.toFloat(), p2.y.toFloat())
        path.lineTo(p3.x.toFloat(), p3.y.toFloat())
        path.close()*/

        val paint = Paint().apply {
            color = Color.argb(100, 0, 220, 220)
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo((width - (width * 0.09)).toFloat(), 0F)
            lineTo(width.toFloat(), (height * 0.5).toFloat())
            lineTo((width - (width * 0.09)).toFloat(), height.toFloat())
            lineTo(0f, height.toFloat())
            lineTo((width * 0.09).toFloat(), (height * 0.5).toFloat())
        }

        canvas!!.drawPath(path, paint)

    }
}