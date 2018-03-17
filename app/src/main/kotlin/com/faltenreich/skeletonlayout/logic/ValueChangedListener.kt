package com.faltenreich.skeletonlayout.logic

interface ValueChangedListener {

    fun onCornerRadiusChanged(value: Float)
    fun onShowShimmerChanged(value: Boolean)
    fun onShimmerDurationChanged(value: Long)
}