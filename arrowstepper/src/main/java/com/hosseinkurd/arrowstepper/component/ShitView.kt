package com.hosseinkurd.arrowstepper.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.hosseinkurd.arrowstepper.component.enums.ShitState

class ShitView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(
    context,
    attributeSet,
    defStyleAttr
) {
    private var state: ShitState = ShitState.SHIT_COLLAPSED

    fun collapseState() {
        state = ShitState.SHIT_COLLAPSED
    }

    fun expandState() {
        state = ShitState.SHIT_EXPANDED
    }

    fun toggleState() {
        if (state == ShitState.SHIT_COLLAPSED) {
            expandState()
        } else {
            collapseState()
        }
    }

    private val paint = Paint()
    private val path = Path()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.apply {
            color = if (state == ShitState.SHIT_COLLAPSED) {
                Color.argb(100, 190, 220, 220)
            } else Color.argb(100, 0, 220, 220)
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