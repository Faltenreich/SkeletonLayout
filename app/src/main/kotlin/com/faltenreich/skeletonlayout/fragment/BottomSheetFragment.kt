package com.faltenreich.skeletonlayout.fragment

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.faltenreich.skeletonlayout.R
import com.faltenreich.skeletonlayout.logic.ValueChangedListener
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*

class BottomSheetFragment : BottomSheetDialogFragment() {

    var valueChangedListener: ValueChangedListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValues()
        initLayout()
    }

    private fun initLayout() {
        maskCornerRadiusView.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val cornerRadius = (progress.toFloat() / MAX_MASK_CORNER_RADIUS) * 100
                maskCornerRadiusLabel.text = String.format(getString(R.string.mask_corner_radius), cornerRadius.toInt())
                valueChangedListener?.onCornerRadiusChanged(cornerRadius)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        shimmerShowView.setOnCheckedChangeListener { _, isChecked -> valueChangedListener?.onShowShimmerChanged(isChecked) }

        shimmerDurationView.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val durationInMillis = ((progress.toFloat() / 100f) * MAX_SHIMMER_DURATION_IN_MILLIS).toLong()
                shimmerDurationLabel.text = String.format(getString(R.string.shimmer_duration), durationInMillis)
                valueChangedListener?.onShimmerDurationChanged(durationInMillis)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun initValues() {
        arguments?.apply {
            // TODO: Mask color

            val maskCornerRadiusProgress = ((getFloat(ARGUMENT_MASK_CORNER_RADIUS) / MAX_MASK_CORNER_RADIUS) * 100).toInt()
            maskCornerRadiusView.progress = maskCornerRadiusProgress
            maskCornerRadiusLabel.text = String.format(getString(R.string.mask_corner_radius), maskCornerRadiusProgress)

            shimmerShowView.isChecked = getBoolean(ARGUMENT_SHOW_SHIMMER)

            // TODO: Shimmer color

            val shimmerDurationProgress = ((getLong(ARGUMENT_SHIMMER_DURATION_IN_MILLIS).toFloat() / MAX_SHIMMER_DURATION_IN_MILLIS) * 100).toInt()
            shimmerDurationView.progress = shimmerDurationProgress
            shimmerDurationLabel.text = String.format(getString(R.string.shimmer_duration), shimmerDurationProgress)
        }
    }

    companion object {

        private const val ARGUMENT_MASK_CORNER_RADIUS = "maskCornerRadius"
        private const val ARGUMENT_MASK_COLOR = "maskColor"
        private const val ARGUMENT_SHOW_SHIMMER = "showShimmer"
        private const val ARGUMENT_SHIMMER_COLOR = "shimmerColor"
        private const val ARGUMENT_SHIMMER_DURATION_IN_MILLIS = "shimmerDurationInMillis"

        private const val MAX_MASK_CORNER_RADIUS = 100f
        private const val MAX_SHIMMER_DURATION_IN_MILLIS = 10000L

        fun newInstance(skeletonFragment: BaseSkeletonFragment): BottomSheetFragment {
            val fragment = BottomSheetFragment()

            fragment.valueChangedListener = skeletonFragment

            val skeletonLayout = skeletonFragment.getSkeletonLayout()
            val arguments = Bundle()
            arguments.putFloat(ARGUMENT_MASK_CORNER_RADIUS, skeletonLayout.cornerRadius)
            arguments.putInt(ARGUMENT_MASK_COLOR, skeletonLayout.maskColor)
            arguments.putBoolean(ARGUMENT_SHOW_SHIMMER, skeletonLayout.showShimmer)
            arguments.putInt(ARGUMENT_SHIMMER_COLOR, skeletonLayout.shimmerColor)
            arguments.putLong(ARGUMENT_SHIMMER_DURATION_IN_MILLIS, skeletonLayout.shimmerDurationInMillis)
            fragment.arguments = arguments

            return fragment
        }
    }
}