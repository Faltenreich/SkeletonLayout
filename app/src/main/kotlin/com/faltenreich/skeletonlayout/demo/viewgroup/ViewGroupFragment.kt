package com.faltenreich.skeletonlayout.demo.viewgroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.demo.MainPagerFragment
import com.faltenreich.skeletonlayout.demo.R
import com.faltenreich.skeletonlayout.demo.databinding.FragmentViewgroupBinding
import com.faltenreich.skeletonlayout.demo.recyclerview.RecyclerViewListItem

class ViewGroupFragment : MainPagerFragment(R.layout.fragment_viewgroup, "ViewGroup") {

    private var _binding: FragmentViewgroupBinding? = null
    private val binding get() = _binding!!

    override lateinit var skeleton: Skeleton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewgroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        val item = RecyclerViewListItem.DEMO.first()

        container.wallpaperView.setImageResource(item.wallpaperResId)
        container.avatarView.setImageResource(item.avatarResId)
        container.titleView.setText(item.titleResId)
        container.descriptionView.setText(item.descriptionResId)

        skeleton = skeletonLayout.apply { showSkeleton() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}