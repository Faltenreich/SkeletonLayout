package com.faltenreich.skeletonview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var skeletonView: SkeletonView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = ListAdapter(this)
        skeletonView = SkeletonView(list, R.layout.list_item)
        skeletonView.show()

        fab.setOnClickListener { if (skeletonView.isShown()) skeletonView.hide() else skeletonView.show() }
    }
}