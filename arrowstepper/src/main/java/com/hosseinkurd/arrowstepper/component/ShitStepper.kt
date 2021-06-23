package com.hosseinkurd.arrowstepper.component

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import com.hosseinkurd.arrowstepper.R
import com.hosseinkurd.arrowstepper.component.`interface`.OnShitClickListener

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
    private val mainView: View = inflate(context, R.layout.stepper_view, this)
    private val childWidth: Int = 100

    fun addShits(shits: MutableList<ShitView>) {
        shits.forEach { shitView ->
            val linearLayoutShitHolder: LinearLayout =
                mainView.findViewById(R.id.linearLayoutShitHolder)
            println("child count : ${linearLayoutShitHolder.childCount}")
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
        val paddingOffsetVertical = paddingTop + paddingBottom
        val childWidthInDP = getInDP(childWidth)
        val mLayoutParams =
            LinearLayout.LayoutParams(childWidthInDP, height - paddingOffsetVertical)
        if (childCount > 0) {
            mLayoutParams.marginStart = (childWidthInDP * -0.05).toInt()
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
        println("ShitStepper >> new child id : ${shitView.id}")
        return shitView
    }

    private fun getInDP(number: Int): Int {
        return (number * context.resources.displayMetrics.density + 0.5f).toInt()
    }
}