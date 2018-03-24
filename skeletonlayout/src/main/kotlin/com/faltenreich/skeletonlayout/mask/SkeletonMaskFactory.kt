package com.faltenreich.skeletonlayout.mask

import android.support.annotation.ColorInt
import android.view.View

internal object SkeletonMaskFactory {

    fun createMask(view: View, @ColorInt maskColor: Int, showShimmer: Boolean, @ColorInt shimmerColor: Int, shimmerDurationInMillis: Long): SkeletonMask =
            when (showShimmer) {
                true -> SkeletonMaskShimmer(view, maskColor, shimmerColor, shimmerDurationInMillis)
                false -> SkeletonMaskSolid(view, maskColor)
            }
}