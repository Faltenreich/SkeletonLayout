package com.faltenreich.skeletonlayout

import com.faltenreich.skeletonlayout.mask.SkeletonShimmerDirection

interface SkeletonStyle {

    /**
     * Color of the mask that fills the original view bounds
     */
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
    var shimmerColor: Int

    /**
     * Duration in milliseconds for one shimmer animation interval
     */
    var shimmerDurationInMillis: Long

    /**
     * Direction of animated shimmer
     */
    var shimmerDirection: SkeletonShimmerDirection

    /**
     * Angle in degrees for animated shimmer
     */
    var shimmerAngle: Int
}