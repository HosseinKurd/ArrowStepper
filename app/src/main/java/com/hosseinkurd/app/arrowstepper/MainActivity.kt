package com.hosseinkurd.app.arrowstepper

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hosseinkurd.arrowstepper.component.ShitStepper
import com.hosseinkurd.arrowstepper.component.ShitView
import com.hosseinkurd.arrowstepper.component.enums.ShitState
import com.hosseinkurd.arrowstepper.component.interfaces.OnShitClickListener
import com.hosseinkurd.arrowstepper.component.interfaces.OnStateChangedListener

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
                println("ShitStepper >> Selected child id : ${shitView?.id} , position : $position")
            }
        }
        findViewById<Button>(R.id.buttonSubmit).setOnClickListener {
            val shits = mutableListOf<ShitView>().apply {
                add(getShitView())
            }
            shitStepper.addShits(shits)
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
                            "Show me on expanded"
                        } else "Show me on collapsed"
                    }
                }
            })
        }
    }
}