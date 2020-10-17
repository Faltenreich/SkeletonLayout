package com.faltenreich.skeletonlayout

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.faltenreich.skeletonlayout.mask.ShimmerDirection
import com.faltenreich.skeletonlayout.mask.SkeletonMask
import com.faltenreich.skeletonlayout.mask.SkeletonMaskFactory

open class SkeletonLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    originView: View? = null,
    @ColorInt private var maskColorInternal: Int = ContextCompat.getColor(context, DEFAULT_MASK_COLOR),
    private var cornerRadiusInternal: Float = DEFAULT_CORNER_RADIUS,
    private var showShimmerInternal: Boolean = DEFAULT_SHIMMER_SHOW,
    @ColorInt private var shimmerColorInternal: Int = ContextCompat.getColor(context, DEFAULT_SHIMMER_COLOR),
    private var shimmerDurationInMillisInternal: Long = DEFAULT_SHIMMER_DURATION_IN_MILLIS,
    private var shimmerDirectionInternal: ShimmerDirection = DEFAULT_SHIMMER_DIRECTION,
    private var shimmerAngleInternal: Int = DEFAULT_SHIMMER_ANGLE
) : FrameLayout(context, attrs, defStyleAttr), Skeleton {

    internal constructor(
        originView: View,
        @ColorInt maskColor: Int,
        cornerRadius: Float,
        showShimmer: Boolean,
        @ColorInt shimmerColor: Int,
        shimmerDuration: Long = 0,
        shimmerDirection: ShimmerDirection = DEFAULT_SHIMMER_DIRECTION,
        shimmerAngle: Int = DEFAULT_SHIMMER_ANGLE
    ) : this(originView.context, null, 0, originView, maskColor, cornerRadius, showShimmer, shimmerColor, shimmerDuration, shimmerDirection, shimmerAngle)

    override var maskColor: Int
        get() = maskColorInternal
        set(value) {
            maskColorInternal = value
            invalidateMask()
        }

    override var maskCornerRadius: Float
        get() = cornerRadiusInternal
        set(value) {
            cornerRadiusInternal = value
            invalidateMask()
        }

    override var showShimmer: Boolean
        get() = showShimmerInternal
        set(value) {
            showShimmerInternal = value
            invalidateMask()
        }

    override var shimmerColor: Int
        get() = shimmerColorInternal
        set(value) {
            shimmerColorInternal = value
            invalidateMask()
        }

    override var shimmerDurationInMillis: Long
        get() = shimmerDurationInMillisInternal
        set(value) {
            shimmerDurationInMillisInternal = value
            invalidateMask()
        }

    override var shimmerDirection: ShimmerDirection
        get() = shimmerDirectionInternal
        set(value) {
            shimmerDirectionInternal = value
            invalidateMask()
        }

    override var shimmerAngle: Int
        get() = shimmerAngleInternal
        set(value) {
            shimmerAngleInternal = value
            invalidateMask()
        }

    private var mask: SkeletonMask? = null
    private var isSkeleton: Boolean = false
    private var isRendered: Boolean = false

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.SkeletonLayout, 0, 0)
            this.maskColorInternal = typedArray.getColor(R.styleable.SkeletonLayout_maskColor, maskColorInternal)
            this.cornerRadiusInternal = typedArray.getDimensionPixelSize(R.styleable.SkeletonLayout_maskCornerRadius, cornerRadiusInternal.toInt()).toFloat()
            this.showShimmerInternal = typedArray.getBoolean(R.styleable.SkeletonLayout_showShimmer, showShimmerInternal)
            this.shimmerColorInternal = typedArray.getColor(R.styleable.SkeletonLayout_shimmerColor, shimmerColorInternal)
            this.shimmerDurationInMillisInternal = typedArray.getInt(R.styleable.SkeletonLayout_shimmerDurationInMillis, shimmerDurationInMillisInternal.toInt()).toLong()
            this.shimmerDirectionInternal = ShimmerDirection.valueOf(typedArray.getInt(R.styleable.SkeletonLayout_shimmerDirection, shimmerDirectionInternal.ordinal)) ?: DEFAULT_SHIMMER_DIRECTION
            this.shimmerAngleInternal = typedArray.getInt(R.styleable.SkeletonLayout_shimmerAngle, shimmerAngle)
            typedArray.recycle()
        }
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
                        .createMask(this, maskColor, showShimmer, shimmerColor, shimmerDurationInMillis, shimmerDirection, shimmerAngle)
                        .also { mask -> mask.mask(this, maskCornerRadius) }
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
        const val DEFAULT_CORNER_RADIUS = 8f
        const val DEFAULT_SHIMMER_SHOW = true
        val DEFAULT_SHIMMER_COLOR = R.color.skeleton_shimmer
        const val DEFAULT_SHIMMER_DURATION_IN_MILLIS = 2000L
        val DEFAULT_SHIMMER_DIRECTION = ShimmerDirection.LEFT_TO_RIGHT
        const val DEFAULT_SHIMMER_ANGLE = 0
    }
}
