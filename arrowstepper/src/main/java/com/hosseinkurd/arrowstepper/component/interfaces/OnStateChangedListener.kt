package com.hosseinkurd.arrowstepper.component.interfaces

import com.hosseinkurd.arrowstepper.component.enums.ShitState

interface OnStateChangedListener {
    fun onStateChanged(shitState : ShitState)
}