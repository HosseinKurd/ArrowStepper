package com.hosseinkurd.arrowstepper.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import com.hosseinkurd.arrowstepper.R

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

    private val mainView: View = inflate(context, R.layout.stepper_view, this)
    private val childWidth: Int = 100


    fun addShit() {
        val linearLayoutShitHolder: LinearLayout =
            mainView.findViewById(R.id.linearLayoutShitHolder)
        linearLayoutShitHolder.addView(getNewChild(linearLayoutShitHolder.childCount == 0))
    }

    private fun getNewChild(isFirstChild: Boolean): View {
        val childWidthInDP = getInDP(childWidth)
        val view = ShitItem(context)
        val layoutParams = LinearLayout.LayoutParams(childWidthInDP, LayoutParams.MATCH_PARENT)
        if (isFirstChild.not()) {
            layoutParams.marginStart = (childWidthInDP * -0.05).toInt()
        }
        view.layoutParams = layoutParams
        view.id = View.generateViewId()
        println("ShitStepper >> new child id : ${view.id}")
        return view
    }

    private fun getInDP(number: Int): Int {
        return (number * context.resources.displayMetrics.density + 0.5f).toInt()
    }
}