package com.faltenreich.skeletonlayout.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.faltenreich.skeletonlayout.R
import com.faltenreich.skeletonlayout.SkeletonRecyclerView
import com.faltenreich.skeletonlayout.logic.RecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_recyclerview.*

class RecyclerViewFragment : BaseFragment(R.layout.fragment_recyclerview, "RecyclerView") {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listAdapter = RecyclerViewAdapter()
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = listAdapter

        SkeletonRecyclerView(list, R.layout.list_item, itemCount = 30).apply { show() }
    }
}