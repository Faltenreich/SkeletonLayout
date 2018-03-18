package com.faltenreich.skeletonlayout

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
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

        fab.setOnClickListener { openEditor() }
    }

    private fun toggleSkeleton() {
        val skeleton = (viewPagerAdapter.getItem(viewPager.currentItem) as? BaseSkeletonFragment)?.getSkeleton()
        skeleton?.let { if (it.isSkeleton()) it.showOriginal() else it.showSkeleton() }
    }

    private fun openEditor() {
        val visibleFragment = viewPagerAdapter.getItem(viewPager.currentItem)
        when (visibleFragment) {
            is BaseSkeletonFragment -> BottomSheetFragment.newInstance(visibleFragment).show(supportFragmentManager, "bottomSheet")
            else -> Toast.makeText(this, "Unsupported", Toast.LENGTH_LONG).show()
        }
    }
}