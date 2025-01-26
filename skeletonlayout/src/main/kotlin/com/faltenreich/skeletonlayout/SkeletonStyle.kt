package com.faltenreich.skeletonlayout

import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import com.faltenreich.skeletonlayout.mask.SkeletonShimmerDirection

interface SkeletonStyle {

    /**
     * Color of the mask that fills the original view bounds
     */
    @get:ColorInt
    var maskColor: Int

    /**
     * x- and y-radius of the oval used to round the mask corners
     */
    var maskCornerRadius: Float

    /**
     * Animate shimmer if set to true
     */
    var showShimmer: Boolean

    /**
     * Color of the animated shimmer
     */
    @get:ColorInt
    var shimmerColor: Int

    /**
     * Duration in milliseconds for one shimmer animation to pass the masked view
     */
    var shimmerDurationInMillis: Long

    /**
     * Direction of the animated shimmer
     */
    var shimmerDirection: SkeletonShimmerDirection

    /**
     * Angle in degrees for animated shimmer
     */
    var shimmerAngle: Int

    /**
     * Custom mask template to customize how skeleton looks like
     */
    @get:LayoutRes
    var maskLayout: Int?
}