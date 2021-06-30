package com.hosseinkurd.component.arrowstepper

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import com.hosseinkurd.component.arrowstepper.enums.ShitState
import com.hosseinkurd.component.arrowstepper.interfaces.OnShitStepperItemClickListener

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

    var onShitStepperItemClickListener: OnShitStepperItemClickListener? = null
    var collapsedAnimation: Animation? = null
    var expandedAnimation: Animation? = null
    var singleExpand: Boolean = true
    var removeFirstShitStartAngle: Boolean = true
    var isOnClickDisabled: Boolean = false
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
            if (typedArray.hasValue(R.styleable.ShitStepper_shitStepperRemoveFirstShitStartAngle)) {
                removeFirstShitStartAngle =
                    typedArray.getBoolean(
                        R.styleable.ShitStepper_shitStepperRemoveFirstShitStartAngle,
                        true
                    )
            }
            if (typedArray.hasValue(R.styleable.ShitStepper_shitStepperDisableOnClick)) {
                isOnClickDisabled =
                    typedArray.getBoolean(
                        R.styleable.ShitStepper_shitStepperDisableOnClick,
                        false
                    )
            }
            typedArray.recycle()
        }
    }

    fun selectNext() {
        val linearLayoutShitHolder: LinearLayout =
            mainView.findViewById(R.id.linearLayoutShitHolder)
        val currentSelectedIndex: Int = currentSelectedIndex()
        if (currentSelectedIndex == -1 || currentSelectedIndex == linearLayoutShitHolder.childCount - 1) {
            return
        }
        expandChildOnlyAt(currentSelectedIndex + 1)
    }

    fun selectPrevious() {
        val currentSelectedIndex: Int = currentSelectedIndex()
        if (currentSelectedIndex == -1 || currentSelectedIndex == 0) {
            return
        }
        expandChildOnlyAt(currentSelectedIndex - 1)
    }

    fun addShitStepperItems(shitStepperItems: MutableList<ShitStepperItem>) {
        shitStepperItems.forEach { shitView ->
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
                    shitStepperItem = shitView
                )
            )
        }
        Handler(Looper.getMainLooper()).postDelayed({
            val horizontalScrollViewShitHolder: HorizontalScrollView =
                mainView.findViewById(R.id.horizontalScrollViewShitHolder)
            horizontalScrollViewShitHolder.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
        }, 120)
    }

    fun addShit() {
        val linearLayoutShitHolder: LinearLayout =
            mainView.findViewById(R.id.linearLayoutShitHolder)
        linearLayoutShitHolder.addView(
            getNewChild(
                parent = linearLayoutShitHolder,
                shitStepperItem = ShitStepperItem(context)
            )
        )
    }

    fun addShitAt(index: Int) {
        val linearLayoutShitHolder: LinearLayout =
            mainView.findViewById(R.id.linearLayoutShitHolder)
        linearLayoutShitHolder.addView(
            getNewChild(
                parent = linearLayoutShitHolder,
                shitStepperItem = ShitStepperItem(context)
            ), index
        )
    }

    fun expandChildOnlyAt(index: Int) {
        val linearLayoutShitHolder: LinearLayout =
            mainView.findViewById(R.id.linearLayoutShitHolder)
        if (index >= linearLayoutShitHolder.childCount) {
            return
        }
        for (i in 0..linearLayoutShitHolder.childCount) {
            if (linearLayoutShitHolder.getChildAt(i) is ShitStepperItem) {
                (linearLayoutShitHolder.getChildAt(i) as ShitStepperItem).apply {
                    if (i == index) {
                        onShitStepperItemClickListener?.onShitStepperItemClicked(this, index)
                        if (expandedAnimation != null) startAnimation(expandedAnimation)
                        expandState()
                    } else if (state == ShitState.SHIT_EXPANDED) {
                        if (collapsedAnimation != null) startAnimation(collapsedAnimation)
                        collapseState()
                    }
                }
            }
        }
    }

    fun getExpandedIndexes(): MutableList<Int> {
        val expandedIndexes = mutableListOf<Int>()
        val linearLayoutShitHolder: LinearLayout =
            mainView.findViewById(R.id.linearLayoutShitHolder)
        for (i in 0..linearLayoutShitHolder.childCount) {
            if (linearLayoutShitHolder.getChildAt(i) is ShitStepperItem) {
                (linearLayoutShitHolder.getChildAt(i) as ShitStepperItem).apply {
                    if (state == ShitState.SHIT_EXPANDED) expandedIndexes.add(i)
                }
            }
        }
        return expandedIndexes
    }

    fun scrollToFirstExpanded() {
        val expandedIndexes = getExpandedIndexes()
        if (expandedIndexes.isEmpty()) {
            return
        }
        val horizontalScrollViewShitHolder: HorizontalScrollView =
            mainView.findViewById(R.id.horizontalScrollViewShitHolder)
        horizontalScrollViewShitHolder.post {
            val linearLayoutShitHolder: LinearLayout =
                mainView.findViewById(R.id.linearLayoutShitHolder)
            horizontalScrollViewShitHolder.scrollTo(
                linearLayoutShitHolder.getChildAt(
                    expandedIndexes[0]
                ).left, 0
            )
        }
    }

    fun smoothScrollToFirstExpanded() {
        val expandedIndexes = getExpandedIndexes()
        if (expandedIndexes.isEmpty()) {
            return
        }
        val horizontalScrollViewShitHolder: HorizontalScrollView =
            mainView.findViewById(R.id.horizontalScrollViewShitHolder)
        horizontalScrollViewShitHolder.post {
            val linearLayoutShitHolder: LinearLayout =
                mainView.findViewById(R.id.linearLayoutShitHolder)
            horizontalScrollViewShitHolder.smoothScrollTo(
                linearLayoutShitHolder.getChildAt(
                    expandedIndexes[0]
                ).left, 0
            )
        }
    }

    fun scrollToLastExpanded() {
        val expandedIndexes = getExpandedIndexes()
        if (expandedIndexes.isEmpty()) {
            return
        }
        val horizontalScrollViewShitHolder: HorizontalScrollView =
            mainView.findViewById(R.id.horizontalScrollViewShitHolder)
        horizontalScrollViewShitHolder.post {
            val linearLayoutShitHolder: LinearLayout =
                mainView.findViewById(R.id.linearLayoutShitHolder)
            horizontalScrollViewShitHolder.scrollTo(
                linearLayoutShitHolder.getChildAt(
                    expandedIndexes[expandedIndexes.size - 1]
                ).left, 0
            )
        }
    }

    fun smoothScrollToLastExpanded() {
        val expandedIndexes = getExpandedIndexes()
        if (expandedIndexes.isEmpty()) {
            return
        }
        val horizontalScrollViewShitHolder: HorizontalScrollView =
            mainView.findViewById(R.id.horizontalScrollViewShitHolder)
        horizontalScrollViewShitHolder.post {
            val linearLayoutShitHolder: LinearLayout =
                mainView.findViewById(R.id.linearLayoutShitHolder)
            horizontalScrollViewShitHolder.smoothScrollTo(
                linearLayoutShitHolder.getChildAt(
                    expandedIndexes[expandedIndexes.size - 1]
                ).left, 0
            )
        }
    }

    private fun getNewChild(parent: LinearLayout, shitStepperItem: ShitStepperItem): View {
        val parentChildIndex: Int = parent.childCount
        shitStepperItem.obliqueHorizontalGap = obliqueHorizontalGap
        shitStepperItem.colorExpanded = colorExpanded
        shitStepperItem.colorCollapsed = colorCollapsed
        val paddingOffsetVertical = paddingTop + paddingBottom
        val mLayoutParams =
            LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, height - paddingOffsetVertical)
        if (parentChildIndex > 0) {
            mLayoutParams.marginStart = (obliqueHorizontalGap * -0.8).toInt()
        } else {
            if (removeFirstShitStartAngle) {
                shitStepperItem.removeStartAngle = removeFirstShitStartAngle
            }
        }
        shitStepperItem.apply {
            layoutParams = mLayoutParams
            id = View.generateViewId()
            setOnClickListener {
                if (isOnClickDisabled) {
                    return@setOnClickListener
                }
                onShitStepperItemClickListener?.onShitStepperItemClicked(
                    shitStepperItem,
                    parentChildIndex
                )
                if (shitStepperItem.state == ShitState.SHIT_COLLAPSED && collapsedAnimation != null) {
                    shitStepperItem.startAnimation(collapsedAnimation)
                } else if (shitStepperItem.state == ShitState.SHIT_EXPANDED && expandedAnimation != null) {
                    shitStepperItem.startAnimation(expandedAnimation)
                    for (i in 0..parent.childCount) {
                        if (i == parentChildIndex) continue
                        if (parent.getChildAt(i) is ShitStepperItem) {
                            (parent.getChildAt(i) as ShitStepperItem).apply {
                                if (state == ShitState.SHIT_EXPANDED) collapseState()
                            }
                        }
                    }
                }
            }
            setWillNotDraw(false)
        }
        return shitStepperItem
    }

    private fun currentSelectedIndex(): Int {
        val linearLayoutShitHolder: LinearLayout =
            mainView.findViewById(R.id.linearLayoutShitHolder)
        for (i in 0..linearLayoutShitHolder.childCount) {
            if (linearLayoutShitHolder.getChildAt(i) is ShitStepperItem) {
                (linearLayoutShitHolder.getChildAt(i) as ShitStepperItem).apply {
                    if (state == ShitState.SHIT_EXPANDED) return i
                }
            }
        }
        return -1
    }

}