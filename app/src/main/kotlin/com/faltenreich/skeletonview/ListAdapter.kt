package com.faltenreich.skeletonview

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class ListAdapter : RecyclerView.Adapter<ListViewHolder>() {

    private val items = ArrayList<ListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListViewHolder(parent)

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    fun add(item: ListItem) = items.add(item)
}