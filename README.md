# ArrowStepper

# Usage:

Check this [link](https://jitpack.io/#HosseinKurd/ArrowStepper "jitpack HosseinKurd ArrowStepper") to find Last version

# Gradle:
Step 1. Add the JitPack repository to your build file
Add it to your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.HosseinKurd:ArrowStepper:$version'
	}

# Kotlin sample Code:

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
    // ====================

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

# XML:

    <com.hosseinkurd.arrowstepper.component.ShitStepper
        android:id="@+id/shitStepper"
        android:layout_width="0dp"
        android:layout_height="100dp"
        android:layout_margin="10dp"
        android:padding="10dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:shitStepperExpandedColor="@color/shit_background_enabled"
        app:shitStepperObliqueHorizontalGap="12dp" />


Good Luck
