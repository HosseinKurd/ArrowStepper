package com.hosseinkurd.arrowstepper.component.`interface`

import com.hosseinkurd.arrowstepper.component.enums.ShitState

interface OnStateChangedListener {
    fun onStateChanged(shitState : ShitState)
}