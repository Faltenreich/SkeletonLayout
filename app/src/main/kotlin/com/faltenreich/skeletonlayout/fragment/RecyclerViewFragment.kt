package com.faltenreich.skeletonlayout.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.faltenreich.skeletonlayout.R
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeletonAdapter
import com.faltenreich.skeletonlayout.logic.RecyclerViewAdapter
import com.faltenreich.skeletonlayout.logic.RecyclerViewListItem
import kotlinx.android.synthetic.main.fragment_recyclerview.*

class RecyclerViewFragment : BaseSkeletonFragment(R.layout.fragment_recyclerview, "RecyclerView") {

    private lateinit var skeleton: Skeleton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = listOf<RecyclerViewListItem>(
                RecyclerViewListItem(R.string.user_0_name, R.string.user_0_statement, R.mipmap.list_avatar_0, R.mipmap.list_image_0),
                RecyclerViewListItem(R.string.user_1_name, R.string.user_1_statement, R.mipmap.list_avatar_1, R.mipmap.list_image_1),
                RecyclerViewListItem(R.string.user_2_name, R.string.user_2_statement, R.mipmap.list_avatar_2, R.mipmap.list_image_2)
        )

        val listAdapter = RecyclerViewAdapter(items)
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = listAdapter

        skeleton = list.applySkeletonAdapter(R.layout.list_item, itemCount = items.size).apply { showSkeleton() }
    }

    override fun getSkeleton(): Skeleton = skeleton
}