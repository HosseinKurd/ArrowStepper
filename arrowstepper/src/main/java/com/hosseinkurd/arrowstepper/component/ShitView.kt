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
import com.hosseinkurd.arrowstepper.component.interfaces.OnStateChangedListener
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
    var obliqueHorizontalGap = 24.75f
    private var state: ShitState = ShitState.SHIT_COLLAPSED
    private val paint = Paint()
    private val path = Path()

    fun addChild(view: View) {
        view.apply {
            setPadding(obliqueHorizontalGap.toInt(), 0, obliqueHorizontalGap.toInt(), 0)
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
        println("ShitStepper >> ShitView >> onDraw ... ${obliqueHorizontalGap}")
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
            lineTo(width - obliqueHorizontalGap, 0F)
            lineTo(width.toFloat(), (height * 0.5).toFloat())
            lineTo(width - obliqueHorizontalGap, height.toFloat())
            lineTo(0f, height.toFloat())
            lineTo(obliqueHorizontalGap, (height * 0.5).toFloat())
        }
        canvas!!.drawPath(path, paint)
    }

}