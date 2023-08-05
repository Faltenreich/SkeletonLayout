package com.faltenreich.skeletonlayout.demo.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.faltenreich.skeletonlayout.demo.MainPagerFragment
import com.faltenreich.skeletonlayout.demo.R
import com.faltenreich.skeletonlayout.demo.databinding.FragmentRecyclerviewBinding

class RecyclerViewFragment : MainPagerFragment(R.layout.fragment_recyclerview, "RecyclerView") {

    private var _binding: FragmentRecyclerviewBinding? = null
    private val binding get() = _binding!!

    override lateinit var skeleton: Skeleton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        val items = RecyclerViewListItem.DEMO

        val listAdapter = RecyclerViewAdapter(items)
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = listAdapter

        skeleton = list.applySkeleton(R.layout.list_item_recyclerview, items.size).apply { showSkeleton() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}