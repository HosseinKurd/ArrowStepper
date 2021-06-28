package com.hosseinkurd.component.arrowstepper

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import com.hosseinkurd.component.arrowstepper.enums.ShitState
import com.hosseinkurd.component.arrowstepper.interfaces.OnShitClickListener

class ShitStepper @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayoutCompat(
        context,
        attributeSet,
        defStyleAttr
    ) {

    var onShitClickListener: OnShitClickListener? = null
    var collapsedAnimation: Animation? = null
    var expandedAnimation: Animation? = null
    var singleExpand: Boolean = true
    private var obliqueHorizontalGap = 24.75f
    private var colorExpanded: ColorStateList? = null
    private var colorCollapsed: ColorStateList? = null
    private val mainView: View = inflate(context, R.layout.stepper_view, this)

    init {
        if (attributeSet != null) {
            val typedArray: TypedArray = context.obtainStyledAttributes(
                attributeSet,
                R.styleable.ShitStepper, defStyleAttr, 0
            )
            obliqueHorizontalGap =
                typedArray.getDimension(
                    R.styleable.ShitStepper_shitStepperObliqueHorizontalGap,
                    100F
                )
            if (typedArray.hasValue(R.styleable.ShitStepper_shitStepperSingleExpand)) {
                singleExpand =
                    typedArray.getBoolean(
                        R.styleable.ShitStepper_shitStepperSingleExpand,
                        true
                    )
            }
            typedArray.recycle()
        }
    }

    fun addShits(shits: MutableList<ShitView>) {
        shits.forEach { shitView ->
            if (shitView.childCount > 0) {
                shitView.getChildAt(0)
                    .setPadding(
                        (obliqueHorizontalGap * 1.2).toInt(),
                        0,
                        (obliqueHorizontalGap * 1.2).toInt(),
                        0
                    )
            }
            val linearLayoutShitHolder: LinearLayout =
                mainView.findViewById(R.id.linearLayoutShitHolder)
            linearLayoutShitHolder.addView(
                getNewChild(
                    parent = linearLayoutShitHolder,
                    shitView = shitView
                )
            )
        }
        postInvalidate()
    }

    fun addShit() {
        val linearLayoutShitHolder: LinearLayout =
            mainView.findViewById(R.id.linearLayoutShitHolder)
        linearLayoutShitHolder.addView(
            getNewChild(
                parent = linearLayoutShitHolder,
                shitView = ShitView(context)
            )
        )
    }

    fun addShitAt(index: Int) {
        val linearLayoutShitHolder: LinearLayout =
            mainView.findViewById(R.id.linearLayoutShitHolder)
        linearLayoutShitHolder.addView(
            getNewChild(
                parent = linearLayoutShitHolder,
                shitView = ShitView(context)
            ), index
        )
    }

    private fun getNewChild(parent: LinearLayout, shitView: ShitView): View {
        val parentChildIndex: Int = parent.childCount
        shitView.obliqueHorizontalGap = obliqueHorizontalGap
        shitView.colorExpanded = colorExpanded
        shitView.colorCollapsed = colorCollapsed
        val paddingOffsetVertical = paddingTop + paddingBottom
        val mLayoutParams =
            LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, height - paddingOffsetVertical)
        if (parentChildIndex > 0) {
            mLayoutParams.marginStart = (obliqueHorizontalGap * -0.8).toInt()
        }
        shitView.apply {
            layoutParams = mLayoutParams
            id = View.generateViewId()
            setOnClickListener {
                onShitClickListener?.onShitClicked(shitView, parentChildIndex)
                if (shitView.state == ShitState.SHIT_COLLAPSED && collapsedAnimation != null) {
                    shitView.startAnimation(collapsedAnimation)
                } else if (shitView.state == ShitState.SHIT_EXPANDED && expandedAnimation != null) {
                    shitView.startAnimation(expandedAnimation)
                    for (i in 0..parent.childCount) {
                        if (i == parentChildIndex) continue
                        if (parent.getChildAt(i) is ShitView) {
                            (parent.getChildAt(i) as ShitView).apply {
                                if (state == ShitState.SHIT_EXPANDED) collapseState()
                            }
                        }
                    }
                }
            }
            setWillNotDraw(false)
        }
        return shitView
    }

    fun toggleChildAt(index: Int) {
        val linearLayoutShitHolder: LinearLayout =
            mainView.findViewById(R.id.linearLayoutShitHolder)
        if (index >= linearLayoutShitHolder.childCount) {
            println("toggleChildAt >> $index >= ${linearLayoutShitHolder.childCount}")
            return
        }
        if (linearLayoutShitHolder.getChildAt(index) is ShitView) {
            (linearLayoutShitHolder.getChildAt(index) as ShitView).apply {
                println("toggleChildAt >> Toggle State $index")
                toggleState()
            }
        }
    }
}