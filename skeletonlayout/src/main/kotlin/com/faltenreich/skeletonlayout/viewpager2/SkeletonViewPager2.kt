package com.faltenreich.skeletonlayout.viewpager2

import androidx.annotation.LayoutRes
import androidx.viewpager2.widget.ViewPager2
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.SkeletonConfig
import com.faltenreich.skeletonlayout.SkeletonStyle
import com.faltenreich.skeletonlayout.recyclerview.SkeletonRecyclerViewAdapter

internal class SkeletonViewPager2(
    private val viewPager: ViewPager2,
    @LayoutRes private val layoutResId: Int,
    private val itemCount: Int,
    private val config: SkeletonConfig
) : Skeleton, SkeletonStyle by config {

    private val originalAdapter = viewPager.adapter

    private var skeletonAdapter: SkeletonRecyclerViewAdapter? = null

    init {
        config.onValueChanged = ::invalidate
        invalidate()
    }

    override fun showOriginal() {
        viewPager.adapter = originalAdapter
    }

    override fun showSkeleton() {
        viewPager.adapter = skeletonAdapter
    }

    override fun isSkeleton() = skeletonAdapter != null && viewPager.adapter == skeletonAdapter

    private fun invalidate() {
        val showSkeleton = isSkeleton()
        skeletonAdapter = SkeletonRecyclerViewAdapter(layoutResId, itemCount, config)
        if (showSkeleton) {
            showSkeleton()
        }
    }
}