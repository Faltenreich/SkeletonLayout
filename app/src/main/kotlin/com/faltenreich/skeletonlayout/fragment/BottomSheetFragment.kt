package com.faltenreich.skeletonlayout.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.faltenreich.skeletonlayout.R
import com.faltenreich.skeletonlayout.logic.ValueChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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
        maskColorView.setListener { _, color -> valueChangedListener?.onMaskColorChanged(color) }

        maskCornerRadiusView.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val cornerRadius = (progress.toFloat() / 100) * MAX_MASK_CORNER_RADIUS
                maskCornerRadiusLabel.text = String.format(getString(R.string.mask_corner_radius), cornerRadius.toInt())
                valueChangedListener?.onMaskCornerRadiusChanged(cornerRadius)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        shimmerShowView.setOnCheckedChangeListener { _, isChecked -> valueChangedListener?.onShowShimmerChanged(isChecked) }

        shimmerColorView.setListener { _, color -> valueChangedListener?.onShimmerColorChanged(color) }

        shimmerDurationView.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val durationInMillis = ((progress.toFloat() / 100) * MAX_SHIMMER_DURATION_IN_MILLIS).toLong()
                shimmerDurationLabel.text = String.format(getString(R.string.shimmer_duration), durationInMillis)
                valueChangedListener?.onShimmerDurationChanged(durationInMillis)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun initValues() {
        arguments?.apply {
            maskColorView.selectColor(getInt(ARGUMENT_MASK_COLOR))

            val maskCornerRadius = getFloat(ARGUMENT_MASK_CORNER_RADIUS)
            val maskCornerRadiusProgress = ((maskCornerRadius / MAX_MASK_CORNER_RADIUS) * 100).toInt()
            maskCornerRadiusView.progress = maskCornerRadiusProgress
            maskCornerRadiusLabel.text = String.format(getString(R.string.mask_corner_radius), maskCornerRadius.toInt())

            shimmerShowView.isChecked = getBoolean(ARGUMENT_SHOW_SHIMMER)

            shimmerColorView.selectColor(getInt(ARGUMENT_SHIMMER_COLOR))

            val shimmerDuration = getLong(ARGUMENT_SHIMMER_DURATION_IN_MILLIS)
            val shimmerDurationProgress = ((shimmerDuration.toFloat() / MAX_SHIMMER_DURATION_IN_MILLIS) * 100).toInt()
            shimmerDurationView.progress = shimmerDurationProgress
            shimmerDurationLabel.text = String.format(getString(R.string.shimmer_duration), shimmerDuration)
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
            val skeleton = skeletonFragment.skeleton

            val arguments = Bundle()
            arguments.putFloat(ARGUMENT_MASK_CORNER_RADIUS, skeleton.maskCornerRadius)
            arguments.putInt(ARGUMENT_MASK_COLOR, skeleton.maskColor)
            arguments.putBoolean(ARGUMENT_SHOW_SHIMMER, skeleton.showShimmer)
            arguments.putInt(ARGUMENT_SHIMMER_COLOR, skeleton.shimmerColor)
            arguments.putLong(ARGUMENT_SHIMMER_DURATION_IN_MILLIS, skeleton.shimmerDurationInMillis)
            fragment.arguments = arguments

            return fragment
        }
    }
}