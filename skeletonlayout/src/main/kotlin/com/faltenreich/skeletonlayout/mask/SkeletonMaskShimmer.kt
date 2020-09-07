package com.faltenreich.skeletonlayout.mask

import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Shader
import android.os.Handler
import android.view.View
import androidx.annotation.ColorInt
import com.faltenreich.skeletonlayout.SkeletonLayout.Companion.DEFAULT_SHIMMER_DIRECTION
import com.faltenreich.skeletonlayout.isAttachedToWindowCompat
import com.faltenreich.skeletonlayout.refreshRateInSeconds
import kotlin.math.cos
import kotlin.math.sin

internal class SkeletonMaskShimmer(
    parent: View,
    @ColorInt maskColor: Int,
    @ColorInt var shimmerColor: Int,
    var durationInMillis: Long,
    private val shimmerDirection: Int
) : SkeletonMask(parent, maskColor) {

    private val refreshIntervalInMillis: Long by lazy { ((1000f / parent.context.refreshRateInSeconds()) * .9f).toLong() }
    private val width: Float = parent.width.toFloat()
    private val matrix: Matrix = Matrix()
    private val shimmerGradient by lazy {
        if (shimmerDirection == DEFAULT_SHIMMER_DIRECTION)
            LinearGradient(
                0f,
                0f,
                width,
                0f,
                intArrayOf(color, shimmerColor, color),
                null,
                Shader.TileMode.CLAMP
            )
        else
            LinearGradient(
                0f,
                0f,
                (cos(Math.toRadians(45.0)) * width).toFloat(),
                (sin(Math.toRadians(45.0)) * width).toFloat(),
                intArrayOf(color, shimmerColor, color),
                null,
                Shader.TileMode.CLAMP
            )
    }

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

    override fun createPaint() = Paint().also {
        it.shader = shimmerGradient
        it.isAntiAlias = true
    }

    // Progress is time-dependent to support synchronization between uncoupled views
    private fun currentProgress(): Float {
        val millis = System.currentTimeMillis()
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
        val offset = width * 2
        val min = -offset
        val max = width + offset
        return progress * (max - min) + min
    }

    private fun updateShimmer() {
        matrix.setTranslate(currentOffset(), 0f)
        paint.shader.setLocalMatrix(matrix)
        parent.invalidate()
    }
}