package com.faltenreich.skeletonlayout.demo.viewpager2

import android.os.Bundle
import android.view.View
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.faltenreich.skeletonlayout.demo.BuildConfig
import com.faltenreich.skeletonlayout.demo.MainPagerFragment
import com.faltenreich.skeletonlayout.demo.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_viewpager2.*

class ViewPager2Fragment : MainPagerFragment(R.layout.fragment_viewpager2, "ViewPager2") {


    override lateinit var skeleton: Skeleton

    private lateinit var mediator: TabLayoutMediator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = ViewPager2ListItem.DEMO

        val listAdapter = ViewPager2Adapter(items)
        pager.adapter = listAdapter

        val skeletonItemSize = if (BuildConfig.isDemoMode) items.size else SKELETON_ITEM_COUNT
        skeleton = pager.applySkeleton(R.layout.pager_item, skeletonItemSize).apply { showSkeleton() }

        mediator = TabLayoutMediator(pager_tabs, pager) { _, _ -> }.apply { attach() }
    }

    override fun onSkeletonToggled() {
        //We are swapping out the adapter when showing/hiding the skeleton.
        //Therefore we need to reattach the mediator in order to show the correct amount of Tabs
        mediator.detach()
        mediator.attach()
    }

    companion object {
        private const val SKELETON_ITEM_COUNT = 2
    }

}