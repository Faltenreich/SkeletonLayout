package com.faltenreich.skeletonlayout.demo.viewpager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.faltenreich.skeletonlayout.demo.MainPagerFragment
import com.faltenreich.skeletonlayout.demo.R
import com.faltenreich.skeletonlayout.demo.databinding.FragmentViewpager2Binding
import com.faltenreich.skeletonlayout.demo.recyclerview.RecyclerViewListItem
import com.google.android.material.tabs.TabLayoutMediator

class ViewPager2Fragment : MainPagerFragment(R.layout.fragment_viewpager2, "ViewPager2") {

    private var _binding: FragmentViewpager2Binding? = null
    private val binding get() = _binding!!

    override lateinit var skeleton: Skeleton

    private lateinit var mediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewpager2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        val items = RecyclerViewListItem.DEMO

        val listAdapter = ViewPager2Adapter(items)
        viewPager.adapter = listAdapter

        skeleton = viewPager.applySkeleton(R.layout.list_item_viewpager2, items.size).apply { showSkeleton() }
        mediator = TabLayoutMediator(viewPagerIndicator, viewPager) { _, _ -> }.apply { attach() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}