package com.hosseinkurd.arrowstepper.component

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.hosseinkurd.arrowstepper.R
import kotlin.random.Random

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

    fun addShit() {
        val linearLayoutShitHolder: LinearLayout =
            mainView.findViewById(R.id.linearLayoutShitHolder)
        var a = 0
        while (a < linearLayoutShitHolder.childCount) {
            val currentChildId = linearLayoutShitHolder.getChildAt(a).id
            println("ShitStepper >> current child id : $currentChildId")
            a++
        }
        val lastChildId =
            linearLayoutShitHolder.getChildAt(linearLayoutShitHolder.childCount - 1).id
        println("ShitStepper >> last child id : $lastChildId")
        linearLayoutShitHolder.addView(getNewChild())
    }

    private fun getNewChild(): View {
        val view = ShitItem(context)
        val layoutParams = LinearLayout.LayoutParams(getInDP(100), getInDP(50))
        view.layoutParams = layoutParams
        /*view.setBackgroundColor(
            Color.rgb(
                getRandomNumberInRange(),
                getRandomNumberInRange(),
                getRandomNumberInRange()
            )
        )*/
        view.id = View.generateViewId()
        println("ShitStepper >> new child id : ${view.id}")
        return view
    }

    private fun getInDP(number: Int): Int {
        return (number * context.resources.displayMetrics.density + 0.5f).toInt()
    }

    private fun getRandomNumberInRange(): Int {
        val min = 0
        val max = 255
        return Random.nextInt(max - min + 1) + min
    }

    fun findAndSetFirstValidId(): Int {
        var randomId: Int
        do {
            randomId = Random.nextInt()
        } while (findViewById<View>(randomId) != null)
        return randomId
    }
}
