package com.faltenreich.skeletonview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class ListViewHolder(context: Context, parent: ViewGroup?) : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))