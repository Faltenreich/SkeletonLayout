@file:JvmName("SkeletonLayoutUtils")

package com.faltenreich.skeletonlayout

import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.skeletonlayout.mask.SkeletonShimmerDirection
import androidx.viewpager2.widget.ViewPager2
import com.faltenreich.skeletonlayout.recyclerview.SkeletonRecyclerView
import com.faltenreich.skeletonlayout.viewpager2.SkeletonViewPager2

private const val LIST_ITEM_COUNT_DEFAULT = 3

/**
 * Creates a new Skeleton that wraps the given View in a SkeletonLayout
 *
 * @receiver View that shall be wrapped in a SkeletonLayout
 * @param maskColor Color of the mask that fills the original view bounds
 * @param cornerRadius x- and y-radius of the oval used to round the mask corners
 * @param showShimmer Animate shimmer if set to true
 * @param shimmerColor Color of the animated shimmer
 * @param shimmerDurationInMillis Duration in milliseconds for one shimmer animation interval
 * @param shimmerDirection Direction of animated shimmer
 * @param shimmerAngle Angle in degrees for animated shimmer
 */
fun View.createSkeleton(config: SkeletonConfig): Skeleton {
    // If this View already has a parent, we need to replace it there with the SkeletonLayout
    val parent = (parent as? ViewGroup)
    val index = parent?.indexOfChild(this) ?: 0
    val params = layoutParams

    parent?.removeView(this)

    val skeleton = SkeletonLayout(this, config)

    if (params != null) {
        skeleton.layoutParams = params
    }
    parent?.addView(skeleton, index)

    return skeleton
}

/**
 * Applies a new Skeleton to the given RecyclerView and wraps its ViewHolders' itemViews in SkeletonLayouts
 *
 * @receiver RecyclerView whose itemViews shall be wrapped in SkeletonLayouts
 * @param listItemLayoutResId Layout resource of the itemView that gets masked
 * @param maskColor Color of the mask that fills the original view bounds
 * @param cornerRadius x- and y-radius of the oval used to round the mask corners
 * @param showShimmer Animate shimmer if set to true
 * @param shimmerColor Color of the animated shimmer
 * @param shimmerDurationInMillis Duration in milliseconds for one shimmer animation interval
 * @param shimmerDirection Direction of animated shimmer
 * @param shimmerAngle Angle in degrees for animated shimmer
 */
@JvmOverloads
fun RecyclerView.applySkeleton(
    @LayoutRes listItemLayoutResId: Int,
    itemCount: Int = LIST_ITEM_COUNT_DEFAULT,
    @ColorInt maskColor: Int = ContextCompat.getColor(context, SkeletonLayout.DEFAULT_MASK_COLOR),
    cornerRadius: Float = SkeletonLayout.DEFAULT_MASK_CORNER_RADIUS,
    showShimmer: Boolean = SkeletonLayout.DEFAULT_SHIMMER_SHOW,
    @ColorInt shimmerColor: Int = ContextCompat.getColor(context, SkeletonLayout.DEFAULT_SHIMMER_COLOR),
    shimmerDurationInMillis: Long = SkeletonLayout.DEFAULT_SHIMMER_DURATION_IN_MILLIS,
    shimmerDirection: SkeletonShimmerDirection = SkeletonLayout.DEFAULT_SHIMMER_DIRECTION,
    shimmerAngle: Int = SkeletonLayout.DEFAULT_SHIMMER_ANGLE
): Skeleton = SkeletonRecyclerView(this, listItemLayoutResId, itemCount, maskColor, cornerRadius, showShimmer, shimmerColor, shimmerDurationInMillis, shimmerDirection, shimmerAngle)

/**
 * Applies a new Skeleton to the given [ViewPager2] and wraps its ViewHolders' itemViews in SkeletonLayouts
 *
 * @receiver [ViewPager2] whose itemViews shall be wrapped in SkeletonLayouts
 * @param listItemLayoutResId Layout resource of the itemView that gets masked
 * @param maskColor Color of the mask that fills the original view bounds
 * @param cornerRadius x- and y-radius of the oval used to round the mask corners
 * @param showShimmer Animate left-to-right shimmer, if set to true
 * @param shimmerColor Color of the animated shimmer
 * @param shimmerDurationInMillis Duration in milliseconds for one shimmer animation interval
 * @param shimmerDirection Direction of animated shimmer
 * @param shimmerAngle Angle in degrees for animated shimmer
 */
@JvmOverloads
fun ViewPager2.applySkeleton(
    @LayoutRes listItemLayoutResId: Int,
    itemCount: Int = LIST_ITEM_COUNT_DEFAULT,
    @ColorInt maskColor: Int = ContextCompat.getColor(context, SkeletonLayout.DEFAULT_MASK_COLOR),
    cornerRadius: Float = SkeletonLayout.DEFAULT_MASK_CORNER_RADIUS,
    showShimmer: Boolean = SkeletonLayout.DEFAULT_SHIMMER_SHOW,
    @ColorInt shimmerColor: Int = ContextCompat.getColor(context, SkeletonLayout.DEFAULT_SHIMMER_COLOR),
    shimmerDurationInMillis: Long = SkeletonLayout.DEFAULT_SHIMMER_DURATION_IN_MILLIS,
    shimmerDirection: SkeletonShimmerDirection = SkeletonLayout.DEFAULT_SHIMMER_DIRECTION,
    shimmerAngle: Int = SkeletonLayout.DEFAULT_SHIMMER_ANGLE
): Skeleton = SkeletonViewPager2(this, listItemLayoutResId, itemCount, maskColor, cornerRadius, showShimmer, shimmerColor, shimmerDurationInMillis, shimmerDirection, shimmerAngle)
