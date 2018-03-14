package com.faltenreich.skeletonview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLayout()
    }

    private fun initLayout() {
        val listAdapter = ListAdapter()
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = listAdapter

        val skeletonView = SkeletonView(list, R.layout.list_item)
        skeletonView.show()
    }
}