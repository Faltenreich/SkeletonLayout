package com.faltenreich.skeletonlayout.demo

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.demo.configuration.ConfigurationFragment
import com.faltenreich.skeletonlayout.demo.recyclerview.RecyclerViewFragment
import com.faltenreich.skeletonlayout.demo.viewgroup.ViewGroupFragment
import com.faltenreich.skeletonlayout.demo.viewpager2.ViewPager2Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var viewPagerAdapter: MainPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }




    private fun initLayout() {
        viewPagerAdapter = MainPagerAdapter(supportFragmentManager, arrayOf(RecyclerViewFragment(), ViewGroupFragment(), ViewPager2Fragment()))
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) = invalidateSkeleton()
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        })

        fab.setOnClickListener { openConfiguration() }
    }

    private fun getSkeleton(): Skeleton? {
        return (viewPagerAdapter.getItem(viewPager.currentItem) as? MainPagerFragment)?.skeleton
    }

    private fun invalidateSkeleton() {
        val skeleton = getSkeleton() ?: return
        val shouldShow = fab.visibility == View.VISIBLE
        val isShown = skeleton.isSkeleton()
        if (shouldShow != isShown) {
            if (shouldShow) {
                showSkeleton(skeleton)
            } else {
                hideSkeleton(skeleton)
            }
        }
    }

    private fun toggleSkeleton() {
        val skeleton = getSkeleton() ?: return
        if (skeleton.isSkeleton()) {
            hideSkeleton(skeleton)
        } else {
            showSkeleton(skeleton)
        }
    }

    private fun showSkeleton(skeleton: Skeleton) {
        skeleton.showSkeleton()
        fab.visibility = View.VISIBLE
    }

    private fun hideSkeleton(skeleton: Skeleton) {
        skeleton.showOriginal()
        fab.visibility = View.GONE
    }

    private fun openConfiguration() {
        val visibleFragment = viewPagerAdapter.getItem(viewPager.currentItem)
        ConfigurationFragment.newInstance(visibleFragment).show(supportFragmentManager, "bottomSheet")
    }
}