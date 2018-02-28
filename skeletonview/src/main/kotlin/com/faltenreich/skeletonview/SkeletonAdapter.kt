package com.faltenreich.skeletonview

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class SkeletonAdapter(@LayoutRes private val layoutResId: Int, private val itemCount: Int) : RecyclerView.Adapter<SkeletonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SkeletonViewHolder(layoutResId, parent)

    override fun onBindViewHolder(holder: SkeletonViewHolder, position: Int) = holder.bind()

    override fun getItemCount() = itemCount
}