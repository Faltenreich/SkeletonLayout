package com.faltenreich.skeletonlayout.demo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.viewpager.widget.ViewPager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.demo.configuration.ConfigurationFragment
import com.faltenreich.skeletonlayout.demo.databinding.ActivityMainBinding
import com.faltenreich.skeletonlayout.demo.recyclerview.RecyclerViewFragment
import com.faltenreich.skeletonlayout.demo.viewgroup.ViewGroupFragment
import com.faltenreich.skeletonlayout.demo.viewpager2.ViewPager2Fragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: MainPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_toggle -> {
                toggleSkeleton()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initLayout() = with(binding) {
        ViewCompat.setOnApplyWindowInsetsListener(fab) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = insets.left
                bottomMargin = insets.bottom
                rightMargin = insets.right
            }
            WindowInsetsCompat.CONSUMED
        }

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

    private fun getSkeleton(): Skeleton? = with(binding) {
        return (viewPagerAdapter.getItem(viewPager.currentItem)).skeleton
    }

    private fun invalidateSkeleton() = with(binding) {
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

    private fun showSkeleton(skeleton: Skeleton) = with(binding) {
        skeleton.showSkeleton()
        fab.visibility = View.VISIBLE
    }

    private fun hideSkeleton(skeleton: Skeleton) = with(binding) {
        skeleton.showOriginal()
        fab.visibility = View.GONE
    }

    private fun openConfiguration() = with(binding) {
        val visibleFragment = viewPagerAdapter.getItem(viewPager.currentItem)
        ConfigurationFragment.newInstance(visibleFragment).show(supportFragmentManager, "bottomSheet")
    }
}