package com.faltenreich.skeletonlayout.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.skeletonlayout.SkeletonConfig
import com.faltenreich.skeletonlayout.SkeletonLayout
import com.faltenreich.skeletonlayout.createSkeleton

internal class SkeletonRecyclerViewAdapter(
    @LayoutRes private val layoutResId: Int,
    private val itemCount: Int,
    private val config: SkeletonConfig
) : RecyclerView.Adapter<SkeletonRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkeletonRecyclerViewHolder {
        val originView = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        val skeleton = originView.createSkeleton(config) as SkeletonLayout
        skeleton.layoutParams = originView.layoutParams
        skeleton.showSkeleton()
        return SkeletonRecyclerViewHolder(skeleton)
    }

    override fun onBindViewHolder(holder: SkeletonRecyclerViewHolder, position: Int) = Unit

    override fun getItemCount() = itemCount
}