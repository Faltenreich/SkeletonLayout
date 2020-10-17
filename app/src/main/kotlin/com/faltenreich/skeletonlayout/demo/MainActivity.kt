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
        if (BuildConfig.isDemoMode) {
            runDemo()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.action_toggle)?.isVisible = !BuildConfig.isDemoMode
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_toggle -> {
                toggleSkeleton()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

        val uiVisibility = if (BuildConfig.isDemoMode) View.GONE else View.VISIBLE
        tabLayout.visibility = uiVisibility
        fab.visibility = uiVisibility
    }

    private fun runDemo() {
        val duration = 4000L
        Handler().apply {
            val runnable = object : Runnable {
                override fun run() {
                    toggleSkeleton()
                    postDelayed(this, duration)
                }
            }
            postDelayed(runnable, duration)
        }
    }

    private fun getSkeleton(): Skeleton? = (viewPagerAdapter.getItem(viewPager.currentItem) as? MainPagerFragment)?.skeleton

    private fun invalidateSkeleton() {
        getSkeleton()?.let {
            val shouldShow = fab.visibility == View.VISIBLE
            val isShown = it.isSkeleton()
            if (shouldShow != isShown) {
                if (shouldShow) showSkeleton(it) else hideSkeleton(it)
            }
        }
    }

    private fun toggleSkeleton() {
        getSkeleton()?.let {
            if (it.isSkeleton()) hideSkeleton(it) else showSkeleton(it)
            notifyFragmentAboutToggle()
        }
    }

    private fun notifyFragmentAboutToggle() {
        (viewPagerAdapter.getItem(viewPager.currentItem) as? MainPagerFragment)?.onSkeletonToggled()
    }

    private fun showSkeleton(skeleton: Skeleton) {
        skeleton.showSkeleton()
        fab.visibility = if (!BuildConfig.isDemoMode) View.VISIBLE else View.GONE
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