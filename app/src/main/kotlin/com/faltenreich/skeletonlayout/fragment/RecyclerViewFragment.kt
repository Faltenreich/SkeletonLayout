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
                RecyclerViewListItem(R.string.schwarzenegger, R.string.schwarzenegger_desc, R.mipmap.schwarzenegger, R.mipmap.terminator),
                RecyclerViewListItem(R.string.stallone, R.string.stallone_desc, R.mipmap.stallone, R.mipmap.rambo),
                RecyclerViewListItem(R.string.willis, R.string.willis_desc, R.mipmap.willis, R.mipmap.diehard)
        )

        val listAdapter = RecyclerViewAdapter(items)
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = listAdapter

        skeleton = list.applySkeletonAdapter(R.layout.list_item, itemCount = items.size).apply { showSkeleton() }
    }

    override fun getSkeleton(): Skeleton = skeleton
}