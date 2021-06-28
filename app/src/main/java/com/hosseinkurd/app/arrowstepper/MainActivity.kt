package com.hosseinkurd.app.arrowstepper

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hosseinkurd.component.arrowstepper.ShitStepper
import com.hosseinkurd.component.arrowstepper.ShitView
import com.hosseinkurd.component.arrowstepper.enums.ShitState
import com.hosseinkurd.component.arrowstepper.interfaces.OnShitClickListener
import com.hosseinkurd.component.arrowstepper.interfaces.OnStateChangedListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val shitStepper: ShitStepper = findViewById(R.id.shitStepper)
        shitStepper.expandedAnimation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        shitStepper.collapsedAnimation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        shitStepper.onShitClickListener = object : OnShitClickListener {
            override fun onShitClicked(shitView: ShitView?, position: Int) {
                shitView?.apply {
                    toggleState()
                    invalidate()
                }
            }
        }
        findViewById<Button>(R.id.buttonSubmit).setOnClickListener {
            val shits = mutableListOf<ShitView>().apply {
                add(getShitView())
            }
            shitStepper.addShits(shits)
            shitStepper.toggleChildAt(0)
        }
    }

    private fun getShitView(): ShitView {
        return ShitView(this).apply {
            addChild(TextView(context).also {
                it.text = "default text"
                it.setTextColor(Color.BLACK)
                it.gravity = Gravity.CENTER
                onStateChangedListener = object : OnStateChangedListener {
                    override fun onStateChanged(shitState: ShitState) {
                        it.text = if (shitState == ShitState.SHIT_EXPANDED) {
                            "It is expanded"
                        } else "Collapsed"
                    }
                }
            })
        }
    }
}