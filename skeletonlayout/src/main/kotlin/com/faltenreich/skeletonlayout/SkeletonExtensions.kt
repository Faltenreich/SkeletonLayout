@file:JvmName("SkeletonLayoutUtils")

package com.faltenreich.skeletonlayout

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.faltenreich.skeletonlayout.recyclerview.SkeletonRecyclerView
import com.faltenreich.skeletonlayout.viewpager2.SkeletonViewPager2

private const val LIST_ITEM_COUNT_DEFAULT = 3

@JvmOverloads
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

@JvmOverloads
fun RecyclerView.applySkeleton(
    @LayoutRes listItemLayoutResId: Int,
    itemCount: Int = LIST_ITEM_COUNT_DEFAULT,
    config: SkeletonConfig = SkeletonConfig.default(context)
): Skeleton = SkeletonRecyclerView(this, listItemLayoutResId, itemCount, config)

@JvmOverloads
fun ViewPager2.applySkeleton(
    @LayoutRes listItemLayoutResId: Int,
    itemCount: Int = LIST_ITEM_COUNT_DEFAULT,
    config: SkeletonConfig = SkeletonConfig.default(context)
): Skeleton = SkeletonViewPager2(this, listItemLayoutResId, itemCount, config)