package com.faltenreich.skeletonlayout.demo.viewpager2

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ViewPager2Adapter(private val items: List<ViewPager2ListItem>) : RecyclerView.Adapter<ViewPager2ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewPager2ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewPager2ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size
}