package com.faltenreich.skeletonlayout

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.faltenreich.skeletonlayout.mask.SkeletonShimmerDirection
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty

class SkeletonConfig(
    @LayoutRes maskLayout: Int?,
    @ColorInt maskColor: Int,
    maskCornerRadius: Float,
    showShimmer: Boolean,
    @ColorInt shimmerColor: Int,
    shimmerDurationInMillis: Long,
    shimmerDirection: SkeletonShimmerDirection,
    shimmerAngle: Int,
) : SkeletonStyle {

    @get:LayoutRes override var maskLayout: Int? by observable(maskLayout)
    @get:ColorInt override var maskColor: Int by observable(maskColor)
    override var maskCornerRadius: Float by observable(maskCornerRadius)
    override var showShimmer: Boolean by observable(showShimmer)
    @get:ColorInt override var shimmerColor: Int by observable(shimmerColor)
    override var shimmerDurationInMillis: Long by observable(shimmerDurationInMillis)
    override var shimmerDirection: SkeletonShimmerDirection by observable(shimmerDirection)
    override var shimmerAngle: Int by observable(shimmerAngle)

    private val valueObservers = mutableListOf<(() -> Unit)>()
    
    private fun onValueChanged() {
        valueObservers.forEach { it.invoke() }
    }

    fun addValueObserver(onValueChanged: () -> Unit) {
        valueObservers.add(onValueChanged)
    }

    private fun <T> observable(value: T): ReadWriteProperty<Any?, T> {
        return Delegates.observable(value) { _, _, _ -> onValueChanged() }
    }

    companion object {

        /**
         * @param maskLayout Layout resource used for mask when applying skeleton (default: original view)
         */
        @JvmName("defaults")
        @JvmOverloads
        fun default(
            context: Context,
            @LayoutRes maskLayout: Int? = null,
        ): SkeletonConfig {
            return SkeletonConfig(
                maskLayout = maskLayout,
                maskColor = ContextCompat.getColor(context, SkeletonLayout.DEFAULT_MASK_COLOR),
                maskCornerRadius = SkeletonLayout.DEFAULT_MASK_CORNER_RADIUS,
                showShimmer = SkeletonLayout.DEFAULT_SHIMMER_SHOW,
                shimmerColor = ContextCompat.getColor(context, SkeletonLayout.DEFAULT_SHIMMER_COLOR),
                shimmerDurationInMillis = SkeletonLayout.DEFAULT_SHIMMER_DURATION_IN_MILLIS,
                shimmerDirection = SkeletonLayout.DEFAULT_SHIMMER_DIRECTION,
                shimmerAngle = SkeletonLayout.DEFAULT_SHIMMER_ANGLE,
            )
        }
    }
}