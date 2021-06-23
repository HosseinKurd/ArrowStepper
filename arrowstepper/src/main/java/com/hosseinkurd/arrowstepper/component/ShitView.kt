package com.hosseinkurd.arrowstepper.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.hosseinkurd.arrowstepper.component.`interface`.OnStateChangedListener
import com.hosseinkurd.arrowstepper.component.enums.ShitState

class ShitView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout
    (
    context,
    attributeSet,
    defStyleAttr
) {
    var onStateChangedListener: OnStateChangedListener? = null
    private var state: ShitState = ShitState.SHIT_COLLAPSED
    private val paint = Paint()
    private val path = Path()

    fun addChild(view: View) {
        view.apply {
            setPadding(getGap().toInt(), 0, getGap().toInt(), 0)
            id = View.generateViewId()
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            addConstraintSet(id)
            addView(this)
        }
    }

    private fun addConstraintSet(childId: Int) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.connect(
            childId,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )
        constraintSet.connect(
            childId,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END
        )
        constraintSet.connect(
            childId,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            childId,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.applyTo(this)
    }

    fun collapseState() {
        state = ShitState.SHIT_COLLAPSED
        onStateChangedListener?.onStateChanged(state)
    }

    fun expandState() {
        state = ShitState.SHIT_EXPANDED
        onStateChangedListener?.onStateChanged(state)
    }

    fun toggleState() {
        if (state == ShitState.SHIT_COLLAPSED) {
            expandState()
        } else {
            collapseState()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        // println("ShitStepper >> ShitView >> onDraw ...")
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
            lineTo((width - getGap()).toFloat(), 0F)
            lineTo(width.toFloat(), (height * 0.5).toFloat())
            lineTo((width - (width * 0.09)).toFloat(), height.toFloat())
            lineTo(0f, height.toFloat())
            lineTo(getGap().toFloat(), (height * 0.5).toFloat())
        }
        canvas!!.drawPath(path, paint)
    }

    private fun getGap(): Double {
        return width * 0.09
    }
}