package com.hosseinkurd.arrowstepper.component

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import com.hosseinkurd.arrowstepper.R
import com.hosseinkurd.arrowstepper.component.interfaces.OnShitClickListener

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
    private var obliqueHorizontalGap = 24.75f
    private var colorExpanded: ColorStateList? = null
    private var colorCollapsed: ColorStateList? = null
    private val mainView: View = inflate(context, R.layout.stepper_view, this)
    // private val childWidth: Int = 100

    init {
        if (attributeSet != null) {
            val array: TypedArray = context.obtainStyledAttributes(
                attributeSet,
                R.styleable.ShitStepper, defStyleAttr, 0
            )
            obliqueHorizontalGap =
                array.getDimension(R.styleable.ShitStepper_shitStepperObliqueHorizontalGap, 100F)
            println("ShitStepper >> obliqueHorizontalGap : $obliqueHorizontalGap")
            if (array.hasValue(R.styleable.ShitStepper_shitStepperExpandedColor)) {
                colorExpanded =
                    array.getColorStateList(R.styleable.ShitStepper_shitStepperExpandedColor)
            }
            println("ShitStepper >> colorExpanded : $colorExpanded")
            if (array.hasValue(R.styleable.ShitStepper_shitStepperCollapsedColor)) {
                colorCollapsed =
                    array.getColorStateList(R.styleable.ShitStepper_shitStepperCollapsedColor)
            }
            println("ShitStepper >> colorCollapsed : $colorCollapsed")
            array.recycle()
        }
    }

    fun addShits(shits: MutableList<ShitView>) {
        shits.forEach { shitView ->
            if (shitView.childCount > 0) {
                shitView.getChildAt(0)
                    .setPadding(
                        obliqueHorizontalGap.toInt(),
                        0,
                        obliqueHorizontalGap.toInt(),
                        0
                    )
            }
            val linearLayoutShitHolder: LinearLayout =
                mainView.findViewById(R.id.linearLayoutShitHolder)
            linearLayoutShitHolder.addView(getNewChild(linearLayoutShitHolder.childCount, shitView))
        }
        postInvalidate()
    }

    fun addShit() {
        val linearLayoutShitHolder: LinearLayout =
            mainView.findViewById(R.id.linearLayoutShitHolder)
        linearLayoutShitHolder.addView(
            getNewChild(
                childCount = linearLayoutShitHolder.childCount,
                shitView = ShitView(context)
            )
        )
    }

    private fun getNewChild(childCount: Int, shitView: ShitView): View {
        shitView.obliqueHorizontalGap = obliqueHorizontalGap
        shitView.colorExpanded = colorExpanded
        shitView.colorCollapsed = colorCollapsed
        val paddingOffsetVertical = paddingTop + paddingBottom
        // val childWidthInDP = getInDP(childWidth)
        val mLayoutParams =
            LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, height - paddingOffsetVertical)
        if (childCount > 0) {
            mLayoutParams.marginStart = (obliqueHorizontalGap * -0.35).toInt()
            // mLayoutParams.marginStart = (childWidthInDP * 0.05).toInt()
            // mLayoutParams.marginEnd = (childWidthInDP * 0.1).toInt()
        }
        shitView.apply {
            layoutParams = mLayoutParams
            id = View.generateViewId()
            setOnClickListener {
                onShitClickListener?.onShitClicked(shitView, childCount)
            }
            setWillNotDraw(false)
        }
        return shitView
    }

    private fun getInDP(number: Int): Int {
        return (number * context.resources.displayMetrics.density + 0.5f).toInt()
    }
}