package com.hosseinkurd.app.arrowstepper

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
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
            override fun onShitStepperItemClicked(shitStepperItem: ShitStepperItem?, position: Int) {
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
                add(
                    ShitStepperItem(this@MainActivity).apply {
                        addChild(TextView(context).also {
                            it.text = "1"
                            it.setTextColor(resources.getColour(R.color.black))
                            it.minWidth = 300
                            it.gravity = Gravity.CENTER
                            onStateChangedListener = object : OnStateChangedListener {
                                override fun onStateChanged(shitState: ShitState) {
                                    it.text = if (shitState == ShitState.SHIT_EXPANDED) {
                                        "1 گام اول"
                                    } else "1"
                                }
                            }
                        })
                    }
                )
                add(
                    ShitStepperItem(this@MainActivity).apply {
                        addChild(TextView(context).also {
                            it.text = "2"
                            it.setTextColor(resources.getColour(R.color.black))
                            it.minWidth = 300
                            it.gravity = Gravity.CENTER
                            onStateChangedListener = object : OnStateChangedListener {
                                override fun onStateChanged(shitState: ShitState) {
                                    it.text = if (shitState == ShitState.SHIT_EXPANDED) {
                                        "2 گام دوم"
                                    } else "2"
                                }
                            }
                        })
                    }
                )
                add(
                    ShitStepperItem(this@MainActivity).apply {
                        addChild(TextView(context).also {
                            it.text = "3"
                            it.setTextColor(resources.getColour(R.color.black))
                            it.minWidth = 300
                            it.gravity = Gravity.CENTER
                            onStateChangedListener = object : OnStateChangedListener {
                                override fun onStateChanged(shitState: ShitState) {
                                    it.text = if (shitState == ShitState.SHIT_EXPANDED) {
                                        "3 گام سوم"
                                    } else "3"
                                }
                            }
                        })
                    }
                )
                add(
                    ShitStepperItem(this@MainActivity).apply {
                        addChild(TextView(context).also {
                            it.text = "4"
                            it.setTextColor(resources.getColour(R.color.black))
                            it.minWidth = 300
                            it.gravity = Gravity.CENTER
                            onStateChangedListener = object : OnStateChangedListener {
                                override fun onStateChanged(shitState: ShitState) {
                                    if (shitState == ShitState.SHIT_EXPANDED) {
                                        it.text = "4 گام چهارم"
                                    } else {
                                        it.text = "4"
                                        it.setTextColor(resources.getColour(R.color.black))
                                    }
                                }
                            }
                        })
                    }
                )
            }
            shitStepper.addShitStepperItems(shitStepperItems)
            shitStepper.toggleChildOnlyAt(0)
        } else if (v?.id == R.id.buttonPrev) {
            shitStepper.selectPrevious()
        } else if (v?.id == R.id.buttonNext) {
            shitStepper.selectNext()
        }
    }

    fun Resources.getColour(@ColorRes id: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getColor(id, null)
        } else {
            getColor(id)
        }
    }
}