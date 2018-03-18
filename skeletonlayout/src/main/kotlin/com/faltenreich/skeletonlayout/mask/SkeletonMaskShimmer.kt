package com.faltenreich.skeletonlayout.mask

import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.os.Handler
import android.support.annotation.ColorInt
import android.view.View
import com.faltenreich.skeletonlayout.isAttachedToWindowCompat
import com.faltenreich.skeletonlayout.refreshRateInSeconds
import com.faltenreich.skeletonlayout.withTimeAtStartOfDay
import java.util.*

internal class SkeletonMaskShimmer(
        parent: View,
        @ColorInt maskColor: Int,
        @ColorInt var shimmerColor: Int,
        var durationInMillis: Long
) : SkeletonMask(parent, maskColor) {

    private val refreshIntervalInMillis by lazy { ((1000f / parent.context.refreshRateInSeconds()) * .9f).toLong() }
    private val width = parent.width.toFloat()

    private var animation: Handler? = null
    private var animationTask: Runnable? = null

    override fun invalidate() {
        when {
            parent.isAttachedToWindowCompat() && parent.visibility == View.VISIBLE -> start()
            else -> stop()
        }
    }

    override fun start() {
        if (animation == null) {
            animation = Handler()
            animationTask = object : Runnable {
                override fun run() {
                    updateShimmer()
                    animation?.postDelayed(this, refreshIntervalInMillis)
                }
            }
            animation?.post(animationTask)
        }
    }

    override fun stop() {
        animation?.removeCallbacks(animationTask)
        animation = null
    }

    override fun createPaint() = Paint().apply {
        shader = gradientForOffset(0f)
        isAntiAlias = true
    }

    private fun gradientForOffset(offset: Float) = LinearGradient(offset, 0f, offset + width, 0f, intArrayOf(color, shimmerColor, color), null, Shader.TileMode.CLAMP)

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