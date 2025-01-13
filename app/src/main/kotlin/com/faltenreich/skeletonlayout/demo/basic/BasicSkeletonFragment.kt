package com.faltenreich.skeletonlayout.demo.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.SkeletonConfig
import com.faltenreich.skeletonlayout.createSkeleton
import com.faltenreich.skeletonlayout.demo.MainPagerFragment
import com.faltenreich.skeletonlayout.demo.R
import com.faltenreich.skeletonlayout.demo.databinding.FragmentBasicBinding
import com.faltenreich.skeletonlayout.mask.SkeletonShimmerDirection

class BasicSkeletonFragment : MainPagerFragment(R.layout.fragment_basic, "Basic") {

    private var _binding: FragmentBasicBinding? = null
    private val binding get() = _binding!!

    override lateinit var skeleton: Skeleton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBasicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        skeleton = SkeletonArray(
            listOf(
                roomItem1.rootRoom.createSkeleton(),
                roomItem2.rootRoom.createSkeleton(
                    SkeletonConfig.default(
                        requireContext(),
                        maskTemplateLayoutId = R.layout.layout_custom_skeleton
                    )
                ),
                room3Skeleton,
            )
        ).apply { showSkeleton() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class SkeletonArray(
    private val skeletons: List<Skeleton>
) : Skeleton {

    override var maskColor: Int
        get() = skeletons.first().maskColor
        set(value) {
            skeletons.forEach { it.maskColor = value }
        }

    override var maskCornerRadius: Float
        get() = skeletons.first().maskCornerRadius
        set(value) {
            skeletons.forEach { it.maskCornerRadius = value }
        }

    override var showShimmer: Boolean
        get() = skeletons.first().showShimmer
        set(value) {
            skeletons.forEach { it.showShimmer = value }
        }

    override var shimmerColor: Int
        get() = skeletons.first().shimmerColor
        set(value) {
            skeletons.forEach { it.shimmerColor = value }
        }

    override var shimmerDurationInMillis: Long
        get() = skeletons.first().shimmerDurationInMillis
        set(value) {
            skeletons.forEach { it.shimmerDurationInMillis = value }
        }

    override var shimmerDirection: SkeletonShimmerDirection
        get() = skeletons.first().shimmerDirection
        set(value) {
            skeletons.forEach { it.shimmerDirection = value }
        }

    override var shimmerAngle: Int
        get() = skeletons.first().shimmerAngle
        set(value) {
            skeletons.forEach { it.shimmerAngle = value }
        }
    override var maskTemplateLayoutId: Int?
        get() = skeletons.first().maskTemplateLayoutId
        set(value) {
            skeletons.forEach { it.maskTemplateLayoutId = value }
        }

    override fun showOriginal() {
        skeletons.forEach { it.showOriginal() }
    }

    override fun showSkeleton() {
        skeletons.forEach { it.showSkeleton() }
    }

    override fun isSkeleton(): Boolean {
        return skeletons.any { it.isSkeleton() }
    }

}