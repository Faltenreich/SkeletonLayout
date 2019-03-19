package com.faltenreich.skeletonlayout

import android.os.Bundle
import android.os.Handler
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.faltenreich.skeletonlayout.fragment.BaseSkeletonFragment
import com.faltenreich.skeletonlayout.fragment.BottomSheetFragment
import com.faltenreich.skeletonlayout.fragment.RecyclerViewFragment
import com.faltenreich.skeletonlayout.fragment.ViewGroupFragment
import com.faltenreich.skeletonlayout.logic.MainPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewPagerAdapter: MainPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

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

    private fun init() {
        viewPagerAdapter = MainPagerAdapter(supportFragmentManager, arrayOf(RecyclerViewFragment(), ViewGroupFragment()))
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                invalidateSkeleton()
            }
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        })

        fab.setOnClickListener { openEditor() }

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

    private fun getSkeleton(): Skeleton? = (viewPagerAdapter.getItem(viewPager.currentItem) as? BaseSkeletonFragment)?.skeleton

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
        getSkeleton()?.let { if (it.isSkeleton()) hideSkeleton(it) else showSkeleton(it) }
    }

    private fun showSkeleton(skeleton: Skeleton) {
        skeleton.showSkeleton()
        fab.visibility = if (!BuildConfig.isDemoMode) View.VISIBLE else View.GONE
    }

    private fun hideSkeleton(skeleton: Skeleton) {
        skeleton.showOriginal()
        fab.visibility = View.GONE
    }

    private fun openEditor() {
        val visibleFragment = viewPagerAdapter.getItem(viewPager.currentItem)
        when (visibleFragment) {
            is BaseSkeletonFragment -> BottomSheetFragment.newInstance(visibleFragment).show(supportFragmentManager, "bottomSheet")
            else -> Toast.makeText(this, "Unsupported", Toast.LENGTH_LONG).show()
        }
    }
}