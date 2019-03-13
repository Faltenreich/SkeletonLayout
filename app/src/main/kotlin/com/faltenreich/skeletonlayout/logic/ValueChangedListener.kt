package com.faltenreich.skeletonlayout.logic

import androidx.annotation.ColorInt

interface ValueChangedListener {
    fun onMaskColorChanged(@ColorInt value: Int)
    fun onMaskCornerRadiusChanged(value: Float)
    fun onShowShimmerChanged(value: Boolean)
    fun onShimmerColorChanged(@ColorInt value: Int)
    fun onShimmerDurationChanged(value: Long)
}