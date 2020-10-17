package com.faltenreich.skeletonlayout.demo.configuration

import androidx.annotation.ColorInt

interface ConfigurationListener {
    fun onMaskColorChanged(@ColorInt value: Int)
    fun onMaskCornerRadiusChanged(value: Float)
    fun onShowShimmerChanged(value: Boolean)
    fun onShimmerColorChanged(@ColorInt value: Int)
    fun onShimmerDurationChanged(value: Long)
    fun onShimmerDirectionChanged(value: Int)
    fun onShimmerAngleChanged(value: Int)
}