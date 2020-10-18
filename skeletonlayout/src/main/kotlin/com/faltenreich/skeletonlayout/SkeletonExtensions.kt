@file:JvmName("SkeletonLayoutUtils")

package com.faltenreich.skeletonlayout

import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.faltenreich.skeletonlayout.mask.SkeletonShimmerDirection
import com.faltenreich.skeletonlayout.recyclerview.SkeletonRecyclerView
import com.faltenreich.skeletonlayout.viewpager2.SkeletonViewPager2

private const val LIST_ITEM_COUNT_DEFAULT = 3

fun View.createSkeleton(
    config: SkeletonConfig = SkeletonConfig.default(context)
): Skeleton {
    // If this View already has a parent, we need to replace it with the SkeletonLayout
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

fun RecyclerView.applySkeleton(
    @LayoutRes listItemLayoutResId: Int,
    itemCount: Int = LIST_ITEM_COUNT_DEFAULT,
    config: SkeletonConfig = SkeletonConfig.default(context)
): Skeleton = SkeletonRecyclerView(this, listItemLayoutResId, itemCount, config)

fun ViewPager2.applySkeleton(
    @LayoutRes listItemLayoutResId: Int,
    itemCount: Int = LIST_ITEM_COUNT_DEFAULT,
    config: SkeletonConfig = SkeletonConfig.default(context)
): Skeleton = SkeletonViewPager2(this, listItemLayoutResId, itemCount, config)

@Deprecated(
    "Wrapped properties of Skeleton in SkeletonConfig to reduce parameter count. Will be removed with upcoming major release.",
    replaceWith = ReplaceWith(
        "createSkeleton(SkeletonConfig(maskColor, cornerRadius, showShimmer, shimmerColor, shimmerDurationInMillis, shimmerDirection, shimmerAngle)",
        "com.faltenreich.skeletonlayout.SkeletonConfig"
    )
)
@JvmOverloads
fun View.createSkeleton(
    @ColorInt maskColor: Int = ContextCompat.getColor(context, SkeletonLayout.DEFAULT_MASK_COLOR),
    cornerRadius: Float = SkeletonLayout.DEFAULT_MASK_CORNER_RADIUS,
    showShimmer: Boolean = SkeletonLayout.DEFAULT_SHIMMER_SHOW,
    @ColorInt shimmerColor: Int = ContextCompat.getColor(context, SkeletonLayout.DEFAULT_SHIMMER_COLOR),
    shimmerDurationInMillis: Long = SkeletonLayout.DEFAULT_SHIMMER_DURATION_IN_MILLIS,
    shimmerDirection: SkeletonShimmerDirection = SkeletonLayout.DEFAULT_SHIMMER_DIRECTION,
    shimmerAngle: Int = SkeletonLayout.DEFAULT_SHIMMER_ANGLE
): Skeleton {
    return createSkeleton(SkeletonConfig(maskColor, cornerRadius, showShimmer, shimmerColor, shimmerDurationInMillis, shimmerDirection, shimmerAngle))
}

@Deprecated(
    "Wrapped properties of Skeleton in SkeletonConfig to reduce parameter count. Will be removed with upcoming major release.",
    replaceWith = ReplaceWith(
        "applySkeleton(listItemLayoutResId, itemCount, SkeletonConfig(maskColor, cornerRadius, showShimmer, shimmerColor, shimmerDurationInMillis, shimmerDirection, shimmerAngle)",
        "com.faltenreich.skeletonlayout.SkeletonConfig"
    )
)
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
): Skeleton {
    return applySkeleton(listItemLayoutResId, itemCount, SkeletonConfig(maskColor, cornerRadius, showShimmer, shimmerColor, shimmerDurationInMillis, shimmerDirection, shimmerAngle))
}


@Deprecated(
    "Wrapped properties of Skeleton in SkeletonConfig to reduce parameter count. Will be removed with upcoming major release.",
    replaceWith = ReplaceWith(
        "applySkeleton(listItemLayoutResId, itemCount, SkeletonConfig(maskColor, cornerRadius, showShimmer, shimmerColor, shimmerDurationInMillis, shimmerDirection, shimmerAngle)",
        "com.faltenreich.skeletonlayout.SkeletonConfig"
    )
)
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
): Skeleton {
    return applySkeleton(listItemLayoutResId, itemCount, SkeletonConfig(maskColor, cornerRadius, showShimmer, shimmerColor, shimmerDurationInMillis, shimmerDirection, shimmerAngle))
}