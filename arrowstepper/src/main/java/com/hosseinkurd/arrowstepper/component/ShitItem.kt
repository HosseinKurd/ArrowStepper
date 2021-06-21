package com.hosseinkurd.arrowstepper.component

import android.content.Context
import android.graphics.*
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
        val x: Int = (width * 0.9).toInt()
        val y: Int = (height * 0.5).toInt()

        /*val paint = Paint()
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        canvas!!.drawLine(0f, 0f, x, y, paint)*/

        val paint = Paint()
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true

        val p1 = Point(0, 0)
        val p2 = Point(x, y)
        val p3 = Point(x + width, y)

        val path = Path()
        path.fillType = Path.FillType.EVEN_ODD
        path.moveTo(p1.x.toFloat(), p1.y.toFloat())
        path.lineTo(p2.x.toFloat(), p2.y.toFloat())
        path.lineTo(p3.x.toFloat(), p3.y.toFloat())
        path.close()

        canvas!!.drawPath(path, paint)

        super.onDraw(canvas)
    }
}