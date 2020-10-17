package com.faltenreich.skeletonlayout

import com.faltenreich.skeletonlayout.mask.ShimmerDirection

/**
 * Toggle state and change properties through this interface after a Skeleton has been applied to a View
 */
interface Skeleton {

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
    var shimmerDirection: ShimmerDirection

    /**
     * Angle in degrees for animated shimmer
     */
    var shimmerAngle: Int

    /**
     * Display the original layout and hide the skeleton
     */
    fun showOriginal()

    /**
     * Display a skeleton and hide the original layout
     */
    fun showSkeleton()

    /**
     * @return True if the original layout is hidden by the skeleton
     */
    fun isSkeleton(): Boolean
}