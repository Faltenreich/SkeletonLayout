package com.faltenreich.skeletonlayout.logic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.skeletonlayout.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.*

class RecyclerViewHolder(
        parent: ViewGroup,
        private val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
) : RecyclerView.ViewHolder(view), LayoutContainer {

    override val containerView: View?
        get() = view

    fun bind(listItem: RecyclerViewListItem) {
        wallpaperView.setImageResource(listItem.wallpaperResId)
        avatarView.setImageResource(listItem.avatarResId)
        titleView.setText(listItem.titleResId)
        descriptionView.setText(listItem.descriptionResId)
    }
}