package com.faltenreich.skeletonview

import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var listAdapter: ListAdapter
    private lateinit var skeletonView: SkeletonView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLayout()
        initContent()
    }

    private fun initLayout() {
        listAdapter = ListAdapter()
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = listAdapter

        skeletonView = SkeletonView(list, R.layout.list_item, ITEM_COUNT, cornerRadius = 25f)
        skeletonView.show()

        toggleButton.setOnClickListener { if (skeletonView.isShown()) skeletonView.hide() else skeletonView.show() }
        addButton.setOnClickListener { addListItem() }
    }

    private fun initContent() {
        Handler().postDelayed({
            skeletonView.hide()
            (0 until ITEM_COUNT).forEach { addListItem() }
        }, 2000)
    }

    private fun addListItem() {
        val index = listAdapter.itemCount
        val indexUi = index + 1

        val title = "Item $indexUi"
        val description = "Description $indexUi"
        val color = ContextCompat.getColor(this, R.color.teal)
        val listItem = ListItem(title, description, color)

        listAdapter.add(listItem)
        listAdapter.notifyItemInserted(index)
    }

    companion object {
        private const val ITEM_COUNT = 3
    }
}