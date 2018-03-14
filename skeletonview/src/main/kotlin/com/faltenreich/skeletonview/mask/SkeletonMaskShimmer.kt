package com.faltenreich.skeletonview.mask

import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.support.annotation.ColorInt
import android.view.View
import com.faltenreich.skeletonview.isAttachedToWindowCompat
import com.faltenreich.skeletonview.refreshRateInSeconds
import com.faltenreich.skeletonview.withTimeAtStartOfDay
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.*

internal class SkeletonMaskShimmer(
        parent: View,
        @ColorInt maskColor: Int,
        @ColorInt shimmerColor: Int,
        private val durationInMillis: Long
) : SkeletonMask(parent, maskColor) {

    private val refreshIntervalInMillis by lazy { ((1000f / parent.context.refreshRateInSeconds()) * .9f).toInt() }
    private val colors by lazy { intArrayOf(color, shimmerColor, color) }
    private val width = parent.width.toFloat()

    private var animation: Job? = null

    override fun invalidate() {
        when {
            parent.isAttachedToWindowCompat() && parent.visibility == View.VISIBLE -> start()
            else -> stop()
        }
    }

    override fun start() {
        if (animation == null) {
            animation = launch(UI) {
                while (isActive) {
                    updateShimmer()
                    delay(refreshIntervalInMillis)
                }
            }
        }
    }

    override fun stop() {
        animation?.cancel()
        animation = null
    }

    override fun createPaint() = Paint().apply {
        shader = gradientForOffset(0f)
        isAntiAlias = true
    }

    private fun gradientForOffset(offset: Float) = LinearGradient(offset, 0f, offset + width, 0f, colors, null, Shader.TileMode.CLAMP)

    // Progress is time-dependent to support synchronization between uncoupled views
    private fun currentProgress(): Float {
        val now = Calendar.getInstance()
        val millis = (now.timeInMillis - now.withTimeAtStartOfDay().timeInMillis)

        val current = millis.toDouble()
        val interval = durationInMillis
        val divisor = Math.floor(current / interval)
        val start = interval * divisor
        val end = start + interval
        val percentage = (current - start) / (end - start)
        return percentage.toFloat()
    }

    private fun currentOffset(): Float {
        val progress = currentProgress()
        val offset = parent.width * 2
        val min = -offset
        val max = parent.width + offset
        return progress * (max - min) + min
    }

    private fun updateShimmer() {
        paint.shader = gradientForOffset(currentOffset())
        parent.invalidate()
    }
}