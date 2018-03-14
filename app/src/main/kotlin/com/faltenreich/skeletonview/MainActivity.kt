package com.faltenreich.skeletonview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.faltenreich.skeletonview.fragment.RecyclerViewFragment
import com.faltenreich.skeletonview.fragment.ViewGroupFragment
import com.faltenreich.skeletonview.logic.MainPagerFragmentAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager.adapter = MainPagerFragmentAdapter(supportFragmentManager, arrayOf(RecyclerViewFragment(), ViewGroupFragment()))
        tabLayout.setupWithViewPager(viewPager)
    }
}