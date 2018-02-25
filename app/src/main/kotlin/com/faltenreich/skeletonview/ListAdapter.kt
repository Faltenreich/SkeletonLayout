package com.faltenreich.skeletonview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class ListAdapter(private val context: Context) : RecyclerView.Adapter<ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ListViewHolder(context, parent)

    override fun onBindViewHolder(holder: ListViewHolder?, position: Int) = Unit

    override fun getItemCount() = 0
}