package com.faltenreich.skeletonlayout.demo.viewpager2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faltenreich.skeletonlayout.demo.R
import com.faltenreich.skeletonlayout.demo.databinding.ListItemViewpager2Binding
import com.faltenreich.skeletonlayout.demo.recyclerview.RecyclerViewListItem

class ViewPager2ViewHolder(
    parent: ViewGroup,
    view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_viewpager2, parent, false)
) : RecyclerView.ViewHolder(view) {

    private val binding = ListItemViewpager2Binding.bind(view)

    fun bind(listItem: RecyclerViewListItem) = with(binding.recyclerView) {
        wallpaperView.setImageResource(listItem.wallpaperResId)
        avatarView.setImageResource(listItem.avatarResId)
        titleView.setText(listItem.titleResId)
        descriptionView.setText(listItem.descriptionResId)
    }
}