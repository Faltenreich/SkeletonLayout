package com.faltenreich.skeletonlayout.mask

import android.view.View
import androidx.annotation.ColorInt

internal object SkeletonMaskFactory {

    fun createMask(view: View, @ColorInt maskColor: Int, showShimmer: Boolean, @ColorInt shimmerColor: Int, shimmerDurationInMillis: Long): SkeletonMask =
            when (showShimmer) {
                true -> SkeletonMaskShimmer(view, maskColor, shimmerColor, shimmerDurationInMillis)
                false -> SkeletonMaskSolid(view, maskColor)
            }
}