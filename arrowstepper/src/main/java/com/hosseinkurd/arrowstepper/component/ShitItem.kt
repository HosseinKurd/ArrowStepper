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

    private val paint = Paint()
    private val path = Path()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.apply {
            color = Color.argb(100, 0, 220, 220)
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        path.apply {
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