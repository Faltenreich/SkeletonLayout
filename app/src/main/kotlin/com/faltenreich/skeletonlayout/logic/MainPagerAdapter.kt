package com.faltenreich.skeletonlayout.logic

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.faltenreich.skeletonlayout.fragment.BaseFragment

class MainPagerAdapter(fragmentManager: FragmentManager, private val fragments: Array<BaseFragment>) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]

    override fun getPageTitle(position: Int) = getItem(position).title
}