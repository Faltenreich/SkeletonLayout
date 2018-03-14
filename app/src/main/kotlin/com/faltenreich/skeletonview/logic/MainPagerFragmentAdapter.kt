package com.faltenreich.skeletonview.logic

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.faltenreich.skeletonview.fragment.BaseFragment

class MainPagerFragmentAdapter(fragmentManager: FragmentManager, private val fragments: Array<BaseFragment>) : FragmentPagerAdapter(fragmentManager) {

    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]

    override fun getPageTitle(position: Int) = getItem(position).title
}