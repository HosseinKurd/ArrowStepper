package com.hosseinkurd.component.arrowstepper.interfaces

import com.hosseinkurd.component.arrowstepper.enums.ShitState

interface OnStateChangedListener {
    fun onStateChanged(shitState : ShitState)
}