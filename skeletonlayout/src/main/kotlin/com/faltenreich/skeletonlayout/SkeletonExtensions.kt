@file:JvmName("SkeletonLayoutUtils")

package com.faltenreich.skeletonlayout

import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.skeletonlayout.recyclerview.SkeletonRecyclerView

private const val LIST_ITEM_COUNT_DEFAULT = 3

/**
 * Creates a new Skeleton that wraps the given View in a SkeletonLayout
 *
 * @receiver View that shall be wrapped in a SkeletonLayout
 * @param maskColor Color of the mask that fills the original view bounds
 * @param cornerRadius x- and y-radius of the oval used to round the mask corners
 * @param showShimmer Animate left-to-right shimmer, if set to true
 * @param shimmerColor Color of the animated shimmer
 * @param shimmerDurationInMillis Duration in milliseconds for one shimmer animation interval
 */
@JvmOverloads
fun View.createSkeleton(
    @ColorInt maskColor: Int = ContextCompat.getColor(context, SkeletonLayout.DEFAULT_MASK_COLOR),
    cornerRadius: Float = SkeletonLayout.DEFAULT_CORNER_RADIUS,
    showShimmer: Boolean = SkeletonLayout.DEFAULT_SHIMMER_SHOW,
    @ColorInt shimmerColor: Int = ContextCompat.getColor(context, SkeletonLayout.DEFAULT_SHIMMER_COLOR),
    shimmerDurationInMillis: Long = SkeletonLayout.DEFAULT_SHIMMER_DURATION_IN_MILLIS
): Skeleton {
    // If this View already has a parent, we need to replace it there with the SkeletonLayout
    val parent = (parent as? ViewGroup)
    val index = parent?.indexOfChild(this) ?: 0
    val params = layoutParams

    parent?.removeView(this)

    val skeleton = SkeletonLayout(this, maskColor, cornerRadius, showShimmer, shimmerColor, shimmerDurationInMillis)

    skeleton.layoutParams = params
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
 * @param showShimmer Animate left-to-right shimmer, if set to true
 * @param shimmerColor Color of the animated shimmer
 * @param shimmerDurationInMillis Duration in milliseconds for one shimmer animation interval
 */
@JvmOverloads
fun RecyclerView.applySkeleton(
    @LayoutRes listItemLayoutResId: Int,
    itemCount: Int = LIST_ITEM_COUNT_DEFAULT,
    @ColorInt maskColor: Int = ContextCompat.getColor(context, SkeletonLayout.DEFAULT_MASK_COLOR),
    cornerRadius: Float = SkeletonLayout.DEFAULT_CORNER_RADIUS,
    showShimmer: Boolean = SkeletonLayout.DEFAULT_SHIMMER_SHOW,
    @ColorInt shimmerColor: Int = ContextCompat.getColor(context, SkeletonLayout.DEFAULT_SHIMMER_COLOR),
    shimmerDurationInMillis: Long = SkeletonLayout.DEFAULT_SHIMMER_DURATION_IN_MILLIS
): Skeleton = SkeletonRecyclerView(this, listItemLayoutResId, itemCount, maskColor, cornerRadius, showShimmer, shimmerColor, shimmerDurationInMillis)