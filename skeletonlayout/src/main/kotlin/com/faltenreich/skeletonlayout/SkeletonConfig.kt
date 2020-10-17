package com.faltenreich.skeletonlayout

import android.content.Context
import androidx.core.content.ContextCompat
import com.faltenreich.skeletonlayout.mask.SkeletonShimmerDirection

data class SkeletonConfig(
    override var maskColor: Int,
    override var maskCornerRadius: Float,
    override var showShimmer: Boolean,
    override var shimmerColor: Int,
    override var shimmerDurationInMillis: Long,
    override var shimmerDirection: SkeletonShimmerDirection,
    override var shimmerAngle: Int,
) : SkeletonStyle {

    companion object {

        fun default(context: Context): SkeletonConfig {
            return SkeletonConfig(
                maskColor = ContextCompat.getColor(context, SkeletonLayout.DEFAULT_MASK_COLOR),
                maskCornerRadius = SkeletonLayout.DEFAULT_MASK_CORNER_RADIUS,
                showShimmer = SkeletonLayout.DEFAULT_SHIMMER_SHOW,
                shimmerColor = ContextCompat.getColor(context, SkeletonLayout.DEFAULT_SHIMMER_COLOR),
                shimmerDurationInMillis = SkeletonLayout.DEFAULT_SHIMMER_DURATION_IN_MILLIS,
                shimmerDirection = SkeletonLayout.DEFAULT_SHIMMER_DIRECTION,
                shimmerAngle = SkeletonLayout.DEFAULT_SHIMMER_ANGLE
            )
        }
    }
}