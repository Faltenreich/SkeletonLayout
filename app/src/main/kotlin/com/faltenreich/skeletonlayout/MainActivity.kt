package com.faltenreich.skeletonlayout

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

    private fun init() {
        viewPagerAdapter = MainPagerAdapter(supportFragmentManager, arrayOf(RecyclerViewFragment(), ViewGroupFragment()))
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        fab.setOnClickListener {
            val visibleFragment = viewPagerAdapter.getItem(viewPager.currentItem)
            when (visibleFragment) {
                is BaseSkeletonFragment -> BottomSheetFragment.newInstance(visibleFragment).show(supportFragmentManager, "bottomSheet")
                else -> Toast.makeText(this, "Unsupported", Toast.LENGTH_LONG).show()
            }
        }
    }
}