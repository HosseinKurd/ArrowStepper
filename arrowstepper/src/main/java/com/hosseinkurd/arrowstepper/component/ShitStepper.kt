package com.hosseinkurd.arrowstepper.component

import android.content.Context
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
    }

    fun addShit() {
        val linearLayoutShitHolder: LinearLayout =
            mainView.findViewById(R.id.linearLayoutShitHolder)
        linearLayoutShitHolder.addView(
            getNewChild(
                childCount = linearLayoutShitHolder.childCount,
                shitItem = ShitView(context)
            )
        )
    }

    private fun getNewChild(childCount: Int, shitItem: ShitView): View {
        val childWidthInDP = getInDP(childWidth)
        val layoutParams = LinearLayout.LayoutParams(childWidthInDP, LayoutParams.MATCH_PARENT)
        if (childCount > 0) {
            layoutParams.marginStart = (childWidthInDP * -0.05).toInt()
        }
        shitItem.layoutParams = layoutParams
        shitItem.id = View.generateViewId()
        shitItem.setOnClickListener {
            onShitClickListener?.onShitClicked(shitItem, childCount)
        }
        println("ShitStepper >> new child id : ${shitItem.id}")
        return shitItem
    }

    private fun getInDP(number: Int): Int {
        return (number * context.resources.displayMetrics.density + 0.5f).toInt()
    }
}