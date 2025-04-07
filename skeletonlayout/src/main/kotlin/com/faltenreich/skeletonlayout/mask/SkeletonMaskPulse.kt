package com.faltenreich.skeletonlayout.mask

import android.graphics.Paint
import android.os.Handler
import android.view.View
import androidx.annotation.ColorInt
import com.faltenreich.skeletonlayout.isAttachedToWindowCompat
import com.faltenreich.skeletonlayout.refreshRateInSeconds
import kotlin.math.cos
import kotlin.math.PI

internal class SkeletonMaskPulse(
    parent: View,
    @ColorInt maskColor: Int,
    private val durationInMillis: Long,
    private val minAlpha: Float = 0.3f,
    private val maxAlpha: Float = 1.0f
) : SkeletonMask(parent, maskColor) {

    private val refreshIntervalInMillis: Long by lazy { ((1000f / parent.context.refreshRateInSeconds()) * .9f).toLong() }

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
                    updatePulse()
                    animation?.postDelayed(this, refreshIntervalInMillis)
                }
            }
            animationTask?.let { task -> animation?.post(task) }
        }
    }

    override fun stop() {
        animationTask?.let { task -> animation?.removeCallbacks(task) }
        animation = null
    }

    override fun createPaint(): Paint {
        return Paint().also {
            it.isAntiAlias = true
            it.color = color
        }
    }

    private fun updatePulse() {
        val progress = currentProgress()
        val alpha = calculateAlpha(progress)
        paint.alpha = (alpha * 255).toInt()
        parent.invalidate()
    }

    private fun calculateAlpha(progress: Float): Float {
        // Use cosine function to create a smooth pulsing effect
        val cosineValue = cos(progress * 2 * PI.toFloat())
        // Map cosine value (-1 to 1) to our alpha range
        return ((cosineValue + 1) / 2) * (maxAlpha - minAlpha) + minAlpha
    }

    // Progress is time-dependent to support synchronization between uncoupled views
    private fun currentProgress(): Float {
        val millis = System.currentTimeMillis()
        val current = millis.toDouble()
        val interval = durationInMillis
        val divisor = kotlin.math.floor(current / interval)
        val start = interval * divisor
        val end = start + interval
        val percentage = (current - start) / (end - start)
        return percentage.toFloat()
    }
}