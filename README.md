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
        findViewById<Button>(R.id.buttonSubmit).setOnClickListener {
            val shits = mutableListOf<ShitView>().apply {
                add(getShitView())
            }
            shitStepper.addShits(shits)
            shitStepper.toggleChildAt(0)
        }
        findViewById<Button>(R.id.buttonSubmit).setOnClickListener {
            val shits = mutableListOf<ShitView>().apply {
                add(getShitView())
            }
            shitStepper.addShits(shits)
            shitStepper.toggleChildAt(0)
        }
        findViewById<Button>(R.id.buttonPrev).setOnClickListener {
             // Select previous Item if exists
            shitStepper.selectPrevious()
        }
        findViewById<Button>(R.id.buttonNext).setOnClickListener {
             // Select next Item if exists
             shitStepper.selectNext()
        }
        // Disable manually select by clicking on item
        shitStepper.isOnClickDisabled = true

# XML:

    <com.hosseinkurd.component.arrowstepper.ShitStepper
            android:id="@+id/shitStepper"
            android:layout_width="0dp"
            android:layout_height="100dp"
            app:shitStepperDisableOnClick="true"
            app:shitStepperObliqueHorizontalGap="12dp"
            app:shitStepperRemoveFirstShitStartAngle="true"
            app:shitStepperSingleExpand="true" />

# Change Colors:

add and modify colors at colors.xml to what you expect

    <color name="shit_background_expanded">#3AA33A</color>
    <color name="shit_background_collapsed">#C2CDC2</color>

Special thanks [Hamidreza Amuzadeh](https://github.com/HamidrezaAmz "Hamidreza Amoozadeh")

Enjoy ;)
