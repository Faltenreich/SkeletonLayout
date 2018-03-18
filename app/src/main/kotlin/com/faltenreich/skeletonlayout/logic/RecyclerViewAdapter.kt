package com.faltenreich.skeletonlayout.logic

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class RecyclerViewAdapter(private val items: List<RecyclerViewListItem>) : RecyclerView.Adapter<RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecyclerViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size
}