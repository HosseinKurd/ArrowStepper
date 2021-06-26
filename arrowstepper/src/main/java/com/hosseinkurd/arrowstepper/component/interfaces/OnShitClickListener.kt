package com.hosseinkurd.arrowstepper.component.interfaces

import com.hosseinkurd.arrowstepper.component.ShitView

interface OnShitClickListener {
    fun onShitClicked(shitView: ShitView?, position: Int)
}