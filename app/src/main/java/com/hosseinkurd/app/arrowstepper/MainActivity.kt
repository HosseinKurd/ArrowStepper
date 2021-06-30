package com.hosseinkurd.app.arrowstepper

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import com.hosseinkurd.component.arrowstepper.ShitStepper
import com.hosseinkurd.component.arrowstepper.ShitStepperItem
import com.hosseinkurd.component.arrowstepper.enums.ShitState
import com.hosseinkurd.component.arrowstepper.interfaces.OnShitStepperItemClickListener
import com.hosseinkurd.component.arrowstepper.interfaces.OnStateChangedListener

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var shitStepper: ShitStepper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shitStepper = findViewById(R.id.shitStepper)
        shitStepper.expandedAnimation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        shitStepper.collapsedAnimation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        shitStepper.onShitStepperItemClickListener = object : OnShitStepperItemClickListener {
            override fun onShitStepperItemClicked(
                shitStepperItem: ShitStepperItem?,
                position: Int
            ) {
                shitStepperItem?.apply {
                    toggleState()
                    invalidate()
                }
            }
        }
        findViewById<Button>(R.id.buttonSubmit).setOnClickListener(this)
        findViewById<Button>(R.id.buttonPrev).setOnClickListener(this)
        findViewById<Button>(R.id.buttonNext).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.buttonSubmit) {
            val shitStepperItems = mutableListOf<ShitStepperItem>().apply {
                for (i in 1..10) {
                    add(
                        ShitStepperItem(this@MainActivity).apply {
                            minWidth = (shitStepper.width * 0.333).toInt()
                            addChild(TextView(context).also {
                                it.text = "$i"
                                it.setTextColor(resources.getColour(R.color.black))
                                it.minWidth = getShitStepperItemMinWidth()
                                it.gravity = Gravity.CENTER
                                onStateChangedListener = object : OnStateChangedListener {
                                    override fun onStateChanged(shitState: ShitState) {
                                        if (shitState == ShitState.SHIT_EXPANDED) {
                                            "$i : عنوان اینجاست".also { result -> it.text = result }
                                            it.setTextColor(resources.getColour(R.color.black))
                                        } else {
                                            it.text = "$i"
                                            it.setTextColor(resources.getColour(R.color.white))
                                        }
                                    }
                                }
                            })
                        }
                    )
                }
            }
            shitStepper.addShitStepperItems(shitStepperItems)
            shitStepper.toggleChildOnlyAt(0)
        } else if (v?.id == R.id.buttonPrev) {
            shitStepper.selectPrevious()
            Toast.makeText(this, " ${shitStepper.getExpandedIndexes()}", Toast.LENGTH_LONG).show()
            shitStepper.smoothScrollToFirstExpanded()
        } else if (v?.id == R.id.buttonNext) {
            shitStepper.selectNext()
            Toast.makeText(this, " ${shitStepper.getExpandedIndexes()}", Toast.LENGTH_LONG).show()
            shitStepper.smoothScrollToFirstExpanded()
        }
    }

    fun Resources.getColour(@ColorRes id: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getColor(id, null)
        } else {
            getColor(id)
        }
    }

    private fun getShitStepperItemMinWidth(): Int {
        return (window.decorView.width * 0.35).toInt()
    }

}