package com.hosseinkurd.app.arrowstepper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.hosseinkurd.arrowstepper.component.ShitStepper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val shitStepper : ShitStepper = findViewById(R.id.shitStepper)
        findViewById<Button>(R.id.buttonSubmit).setOnClickListener{
            shitStepper.addShit()
        }


    }
}