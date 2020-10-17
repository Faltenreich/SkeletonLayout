package com.faltenreich.skeletonlayout.recyclerview

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.SkeletonConfig
import com.faltenreich.skeletonlayout.SkeletonStyle

internal class SkeletonRecyclerView(
    private val recyclerView: RecyclerView,
    @LayoutRes private val layoutResId: Int,
    private val itemCount: Int,
    private val config: SkeletonConfig
) : Skeleton, SkeletonStyle by config {

    private val originalAdapter = recyclerView.adapter

    private var skeletonAdapter: SkeletonRecyclerViewAdapter? = null

    init {
        config.onValueChanged = ::invalidate
        invalidate()
    }

    override fun showOriginal() {
        recyclerView.adapter = originalAdapter
    }

    override fun showSkeleton() {
        recyclerView.adapter = skeletonAdapter
    }

    override fun isSkeleton(): Boolean {
        return skeletonAdapter != null && recyclerView.adapter == skeletonAdapter
    }

    private fun invalidate() {
        val showSkeleton = isSkeleton()
        skeletonAdapter = SkeletonRecyclerViewAdapter(layoutResId, itemCount, config)
        if (showSkeleton) {
            showSkeleton()
        }
    }
}