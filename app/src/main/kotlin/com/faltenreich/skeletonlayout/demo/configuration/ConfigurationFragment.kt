package com.faltenreich.skeletonlayout.demo.configuration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SeekBar
import com.faltenreich.skeletonlayout.demo.MainPagerFragment
import com.faltenreich.skeletonlayout.demo.R
import com.faltenreich.skeletonlayout.demo.databinding.FragmentConfigurationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ConfigurationFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentConfigurationBinding? = null
    private val binding get() = _binding!!

    private lateinit var configurationListener: ConfigurationListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfigurationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadConfiguration()
        initMaskView()
        initShimmerView()
        initPulseView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadConfiguration() = with(binding) {
        arguments?.apply {
            maskColorView.selectColor(getInt(ARGUMENT_MASK_COLOR))

            val maskCornerRadius = getFloat(ARGUMENT_MASK_CORNER_RADIUS)
            val maskCornerRadiusProgress = ((maskCornerRadius / MAX_MASK_CORNER_RADIUS) * 100).toInt()
            maskCornerRadiusView.progress = maskCornerRadiusProgress
            maskCornerRadiusLabel.text = String.format(getString(R.string.mask_corner_radius), maskCornerRadius.toInt())

            shimmerShowView.isChecked = getBoolean(ARGUMENT_SHOW_SHIMMER)
            pulseShowView.isChecked = getBoolean(ARGUMENT_SHOW_PULSE)

            shimmerColorView.selectColor(getInt(ARGUMENT_SHIMMER_COLOR))

            val shimmerDuration = getLong(ARGUMENT_SHIMMER_DURATION_IN_MILLIS)
            val shimmerDurationProgress = ((shimmerDuration.toFloat() / MAX_SHIMMER_DURATION_IN_MILLIS) * 100).toInt()
            shimmerDurationView.progress = shimmerDurationProgress
            shimmerDurationLabel.text = String.format(getString(R.string.shimmer_duration), shimmerDuration)

            val shimmerDirection = getInt(ARGUMENT_SHIMMER_DIRECTION)
            shimmerDirectionView.setSelection(shimmerDirection)

            val shimmerAngle = getInt(ARGUMENT_SHIMER_ANGLE)
            val shimmerAngleProgress = ((shimmerAngle.toFloat() / MAX_SHIMMER_ANGLE) * 100).toInt()
            shimmerAngleView.progress = shimmerAngleProgress
            shimmerAngleLabel.text = String.format(getString(R.string.shimmer_angle), shimmerAngle)

            val pulseDuration = getLong(ARGUMENT_PULSE_DURATION_IN_MILLIS)
            val pulseDurationProgress = ((pulseDuration.toFloat() / MAX_PULSE_DURATION_IN_MILLIS) * 100).toInt()
            pulseDurationView.progress = pulseDurationProgress
            pulseDurationLabel.text = String.format(getString(R.string.pulse_duration), pulseDuration)

            val pulseMinAlpha = getFloat(ARGUMENT_PULSE_MIN_ALPHA)
            val pulseMinAlphaProgress = ((pulseMinAlpha / MAX_PULSE_ALPHA) * 100).toInt()
            pulseMinAlphaView.progress = pulseMinAlphaProgress
            pulseMinAlphaLabel.text = String.format(getString(R.string.pulse_min_alpha), pulseMinAlpha)

            val pulseMaxAlpha = getFloat(ARGUMENT_PULSE_MAX_ALPHA)
            val pulseMaxAlphaProgress = ((pulseMaxAlpha / MAX_PULSE_ALPHA) * 100).toInt()
            pulseMaxAlphaView.progress = pulseMaxAlphaProgress
            pulseMaxAlphaLabel.text = String.format(getString(R.string.pulse_max_alpha), pulseMaxAlpha)
        }
    }

    private fun initMaskView() = with(binding) {
        maskColorView.setListener { _, color -> configurationListener.onMaskColorChanged(color) }
        maskCornerRadiusView.onProgressChanged { progress ->
            val cornerRadius = (progress.toFloat() / 100) * MAX_MASK_CORNER_RADIUS
            maskCornerRadiusLabel.text = String.format(getString(R.string.mask_corner_radius), cornerRadius.toInt())
            configurationListener.onMaskCornerRadiusChanged(cornerRadius)
        }
    }

    private fun initShimmerView() = with(binding) {
        shimmerShowView.setOnCheckedChangeListener { _, isChecked -> 
            if (isChecked) {
                // If Shimmer is enabled, disable Pulse
                pulseShowView.isChecked = false
            }
            configurationListener.onShowShimmerChanged(isChecked)
        }
        shimmerColorView.setListener { _, color -> configurationListener.onShimmerColorChanged(color) }
        shimmerDurationView.onProgressChanged { progress ->
            val durationInMillis = ((progress.toFloat() / 100) * MAX_SHIMMER_DURATION_IN_MILLIS).toLong()
            shimmerDurationLabel.text = String.format(getString(R.string.shimmer_duration), durationInMillis)
            configurationListener.onShimmerDurationChanged(durationInMillis)
        }
        shimmerDirectionView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                configurationListener.onShimmerDirectionChanged(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
        shimmerAngleView.onProgressChanged { progress ->
            val angle = ((progress.toFloat() / 100) * MAX_SHIMMER_ANGLE).toInt()
            shimmerAngleLabel.text = String.format(getString(R.string.shimmer_angle), angle)
            configurationListener.onShimmerAngleChanged(angle)
        }
    }

    private fun initPulseView() = with(binding) {
        pulseShowView.setOnCheckedChangeListener { _, isChecked -> 
            if (isChecked) {
                // If Pulse is enabled, disable Shimmer
                shimmerShowView.isChecked = false
            }
            configurationListener.onShowPulseChanged(isChecked)
        }
        pulseDurationView.onProgressChanged { progress ->
            val durationInMillis = ((progress.toFloat() / 100) * MAX_PULSE_DURATION_IN_MILLIS).toLong()
            pulseDurationLabel.text = String.format(getString(R.string.pulse_duration), durationInMillis)
            configurationListener.onPulseDurationChanged(durationInMillis)
        }
        pulseMinAlphaView.onProgressChanged { progress ->
            val minAlpha = (progress.toFloat() / 100) * MAX_PULSE_ALPHA
            pulseMinAlphaLabel.text = String.format(getString(R.string.pulse_min_alpha), minAlpha)
            configurationListener.onPulseMinAlphaChanged(minAlpha)
        }
        pulseMaxAlphaView.onProgressChanged { progress ->
            val maxAlpha = (progress.toFloat() / 100) * MAX_PULSE_ALPHA
            pulseMaxAlphaLabel.text = String.format(getString(R.string.pulse_max_alpha), maxAlpha)
            configurationListener.onPulseMaxAlphaChanged(maxAlpha)
        }
    }

    private fun SeekBar.onProgressChanged(onProgressChanged: (progress: Int) -> Unit) {
        setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) = onProgressChanged(progress)
            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })
    }

    companion object {

        private const val ARGUMENT_MASK_CORNER_RADIUS = "maskCornerRadius"
        private const val ARGUMENT_MASK_COLOR = "maskColor"
        private const val ARGUMENT_SHOW_SHIMMER = "showShimmer"
        private const val ARGUMENT_SHIMMER_COLOR = "shimmerColor"
        private const val ARGUMENT_SHIMMER_DURATION_IN_MILLIS = "shimmerDurationInMillis"
        private const val ARGUMENT_SHIMMER_DIRECTION = "shimmerDirection"
        private const val ARGUMENT_SHIMER_ANGLE = "shimmerAngle"
        private const val ARGUMENT_SHOW_PULSE = "showPulse"
        private const val ARGUMENT_PULSE_DURATION_IN_MILLIS = "pulseDurationInMillis"
        private const val ARGUMENT_PULSE_MIN_ALPHA = "pulseMinAlpha"
        private const val ARGUMENT_PULSE_MAX_ALPHA = "pulseMaxAlpha"

        private const val MAX_MASK_CORNER_RADIUS = 100f
        private const val MAX_SHIMMER_DURATION_IN_MILLIS = 10000L
        private const val MAX_SHIMMER_ANGLE = 360
        private const val MAX_PULSE_DURATION_IN_MILLIS = 5000L
        private const val MAX_PULSE_ALPHA = 1.0f

        fun newInstance(child: MainPagerFragment): ConfigurationFragment {
            val fragment = ConfigurationFragment()
            fragment.configurationListener = child

            val skeleton = child.skeleton

            val arguments = Bundle()
            arguments.putFloat(ARGUMENT_MASK_CORNER_RADIUS, skeleton.maskCornerRadius)
            arguments.putInt(ARGUMENT_MASK_COLOR, skeleton.maskColor)
            arguments.putBoolean(ARGUMENT_SHOW_SHIMMER, skeleton.showShimmer)
            arguments.putInt(ARGUMENT_SHIMMER_COLOR, skeleton.shimmerColor)
            arguments.putLong(ARGUMENT_SHIMMER_DURATION_IN_MILLIS, skeleton.shimmerDurationInMillis)
            arguments.putInt(ARGUMENT_SHIMMER_DIRECTION, skeleton.shimmerDirection.ordinal)
            arguments.putInt(ARGUMENT_SHIMER_ANGLE, skeleton.shimmerAngle)
            arguments.putBoolean(ARGUMENT_SHOW_PULSE, skeleton.showPulse)
            arguments.putLong(ARGUMENT_PULSE_DURATION_IN_MILLIS, skeleton.pulseDurationInMillis)
            arguments.putFloat(ARGUMENT_PULSE_MIN_ALPHA, skeleton.pulseMinAlpha)
            arguments.putFloat(ARGUMENT_PULSE_MAX_ALPHA, skeleton.pulseMaxAlpha)
            fragment.arguments = arguments

            return fragment
        }
    }
}