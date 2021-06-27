package com.hosseinkurd.arrowstepper.component

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.hosseinkurd.arrowstepper.component.enums.ShitState
import com.hosseinkurd.arrowstepper.component.interfaces.OnStateChangedListener

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
    var obliqueHorizontalGap = 24.75f
    var colorExpanded: ColorStateList? = null
    var colorCollapsed: ColorStateList? = null
    var state: ShitState = ShitState.SHIT_COLLAPSED

    fun addChild(view: View) {
        view.apply {
            setPadding(
                (obliqueHorizontalGap * 1.2).toInt(),
                0,
                (obliqueHorizontalGap * 1.2).toInt(),
                0
            )
            id = View.generateViewId()
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
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
        invalidate()
    }

    fun expandState() {
        state = ShitState.SHIT_EXPANDED
        onStateChangedListener?.onStateChanged(state)
        invalidate()
    }

    fun toggleState() {
        if (state == ShitState.SHIT_COLLAPSED) {
            expandState()
        } else {
            collapseState()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        println("ShitStepper >> ShitView >> onDraw ... $state , $width")
        drawShape(canvas)
        super.onDraw(canvas)
    }

    private fun drawShape(canvas: Canvas?) {
        if (canvas == null) {
            return
        }
        val paint = Paint()
        val path = Path()
        paint.apply {
            color = if (state == ShitState.SHIT_COLLAPSED) {
                /*if (colorCollapsed != null) {
                    return colorCollapsed
                }*/
                Color.argb(100, 190, 220, 220)
            } else {
                /*if (colorExpanded != null) {
                    return colorExpanded
                }*/
                Color.argb(100, 0, 220, 220)
            }
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        path.apply {
            moveTo(0f, 0f)
            lineTo(width - obliqueHorizontalGap, 0F)
            lineTo(width.toFloat(), (height * 0.5).toFloat())
            lineTo(width - obliqueHorizontalGap, height.toFloat())
            lineTo(0f, height.toFloat())
            lineTo(obliqueHorizontalGap, (height * 0.5).toFloat())
        }
        canvas.drawPath(path, paint)
    }

}