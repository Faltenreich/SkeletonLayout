package com.faltenreich.skeletonlayout

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.faltenreich.skeletonlayout.mask.SkeletonMask
import com.faltenreich.skeletonlayout.mask.SkeletonMaskFactory
import com.faltenreich.skeletonlayout.mask.SkeletonShimmerDirection

open class SkeletonLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        originView: View? = null,
        private val config: SkeletonConfig = SkeletonConfig.default(context)
) : FrameLayout(context, attrs, defStyleAttr), Skeleton, SkeletonStyle by config {

    internal constructor(
            originView: View,
            config: SkeletonConfig
    ) : this(originView.context, null, 0, originView, config)

    private var mask: SkeletonMask? = null
    private var isSkeleton: Boolean = false
    private var isRendered: Boolean = false
    private var limitToLevel: Int = Int.MAX_VALUE

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.SkeletonLayout, 0, 0)
            this.maskColor = typedArray.getColor(R.styleable.SkeletonLayout_maskColor, maskColor)
            this.maskCornerRadius = typedArray.getDimensionPixelSize(R.styleable.SkeletonLayout_maskCornerRadius, maskCornerRadius.toInt()).toFloat()
            this.showShimmer = typedArray.getBoolean(R.styleable.SkeletonLayout_showShimmer, showShimmer)
            this.shimmerColor = typedArray.getColor(R.styleable.SkeletonLayout_shimmerColor, shimmerColor)
            this.shimmerDurationInMillis = typedArray.getInt(R.styleable.SkeletonLayout_shimmerDurationInMillis, shimmerDurationInMillis.toInt()).toLong()
            this.shimmerDirection = SkeletonShimmerDirection.valueOf(typedArray.getInt(R.styleable.SkeletonLayout_shimmerDirection, shimmerDirection.ordinal)) ?: DEFAULT_SHIMMER_DIRECTION
            this.shimmerAngle = typedArray.getInt(R.styleable.SkeletonLayout_shimmerAngle, shimmerAngle)
            typedArray.recycle()
        }
        config.addValueObserver(::invalidateMask)
        originView?.let { view -> addView(view) }
    }

    override fun showOriginal() {
        isSkeleton = false

        if (childCount > 0) {
            views().forEach { it.visibility = View.VISIBLE }
            mask?.stop()
            mask = null
        }
    }

    override fun showSkeleton() {
        isSkeleton = true

        if (isRendered) {
            if (childCount > 0) {
                views().forEach { it.visibility = View.INVISIBLE }
                setWillNotDraw(false)
                invalidateMask()
                mask?.invalidate()

            } else {
                Log.i(tag(), "No views to mask")
            }
        }
    }
    override fun isSkeleton(): Boolean = isSkeleton

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        isRendered = true

        if (isSkeleton) {
            showSkeleton()
        }
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        mask?.invalidate()
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        when (hasWindowFocus) {
            true -> mask?.start()
            false -> mask?.stop()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (isRendered) {
            invalidateMask()
            mask?.start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mask?.stop()
    }

    override fun onDraw(canvas: Canvas) {
        mask?.draw(canvas)
    }

    private fun invalidateMask() {
        if (isRendered) {
            mask?.stop()
            mask = null
            if (isSkeleton) {
                if (width > 0 && height > 0) {
                    mask = SkeletonMaskFactory
                            .createMask(this, config)
                            .also { mask -> mask.mask(this, maskCornerRadius,limitToLevel) }
                } else {
                    Log.e(tag(), "Failed to mask view with invalid width and height")
                }
            }
        } else {
            Log.e(tag(), "Skipping invalidation until view is rendered")
        }
    }

    companion object {
        val DEFAULT_MASK_COLOR = R.color.skeleton_mask
        const val DEFAULT_MASK_CORNER_RADIUS = 8f
        const val DEFAULT_SHIMMER_SHOW = true
        val DEFAULT_SHIMMER_COLOR = R.color.skeleton_shimmer
        const val DEFAULT_SHIMMER_DURATION_IN_MILLIS = 2000L
        val DEFAULT_SHIMMER_DIRECTION = SkeletonShimmerDirection.LEFT_TO_RIGHT
        const val DEFAULT_SHIMMER_ANGLE = 0
    }
}
