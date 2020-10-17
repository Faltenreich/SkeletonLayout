package com.faltenreich.skeletonlayout

import android.content.Context
import androidx.core.content.ContextCompat
import com.faltenreich.skeletonlayout.mask.SkeletonShimmerDirection
import kotlin.properties.Delegates

class SkeletonConfig(
    maskColor: Int,
    maskCornerRadius: Float,
    showShimmer: Boolean,
    shimmerColor: Int,
    shimmerDurationInMillis: Long,
    shimmerDirection: SkeletonShimmerDirection,
    shimmerAngle: Int,
) : SkeletonStyle {

    override var maskColor: Int by Delegates.observable(maskColor) { _, _, _ -> onValueChanged() }
    override var maskCornerRadius: Float by Delegates.observable(maskCornerRadius) { _, _, _ -> onValueChanged() }
    override var showShimmer: Boolean by Delegates.observable(showShimmer) { _, _, _ -> onValueChanged() }
    override var shimmerColor: Int by Delegates.observable(shimmerColor) { _, _, _ -> onValueChanged() }
    override var shimmerDurationInMillis: Long by Delegates.observable(shimmerDurationInMillis) { _, _, _ -> onValueChanged() }
    override var shimmerDirection: SkeletonShimmerDirection by Delegates.observable(shimmerDirection) { _, _, _ -> onValueChanged() }
    override var shimmerAngle: Int by Delegates.observable(shimmerAngle) { _, _, _ -> onValueChanged() }

    private val valueObservers = mutableListOf<(() -> Unit)>()
    
    private fun onValueChanged() {
        valueObservers.forEach { it.invoke() }
    }

    fun addValueObserver(onValueChanged: () -> Unit) {
        valueObservers.add(onValueChanged)
    }

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