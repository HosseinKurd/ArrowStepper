package com.hosseinkurd.component.arrowstepper

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.hosseinkurd.component.arrowstepper.enums.ShitState
import com.hosseinkurd.component.arrowstepper.interfaces.OnStateChangedListener

class ShitStepperItem @JvmOverloads constructor(
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
    var removeStartAngle = false

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
                getColour(R.color.shit_background_collapsed)
            } else {
                getColour(R.color.shit_background_expanded)
            }
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        if (isRtl) {
            path.apply {
                moveTo(width.toFloat(), 0f)
                lineTo(obliqueHorizontalGap, 0F)
                lineTo(0f, (height * 0.5).toFloat())
                lineTo(obliqueHorizontalGap, height.toFloat())
                lineTo(width.toFloat(), height.toFloat())
                if (removeStartAngle.not())
                    lineTo(width - obliqueHorizontalGap, (height * 0.5).toFloat())
            }
        } else {
            path.apply {
                moveTo(0f, 0f)
                lineTo(width - obliqueHorizontalGap, 0F)
                lineTo(width.toFloat(), (height * 0.5).toFloat())
                lineTo(width - obliqueHorizontalGap, height.toFloat())
                lineTo(0f, height.toFloat())
                if (removeStartAngle.not())
                    lineTo(obliqueHorizontalGap, (height * 0.5).toFloat())
            }
        }
        canvas.drawPath(path, paint)
    }

    private fun getColour(resId: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.resources.getColor(resId, null)
        } else {
            context.resources.getColor(resId)
        }
    }
}