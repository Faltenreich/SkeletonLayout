package com.faltenreich.skeletonlayout

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.faltenreich.skeletonlayout.mask.*

open class SkeletonConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    originView: View? = null,
    private val config: SkeletonConfig = SkeletonConfig.default(context)
) : ConstraintLayout(context, attrs, defStyleAttr), Skeleton, SkeletonStyle by config {

    private var mask: SkeletonMask? = null
    private var isSkeleton: Boolean = false
    private var isRendered: Boolean = false

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.SkeletonLayout, 0, 0)
            this.maskColor = typedArray.getColor(R.styleable.SkeletonLayout_maskColor, maskColor)
            this.maskCornerRadius = typedArray.getDimensionPixelSize(R.styleable.SkeletonLayout_maskCornerRadius, maskCornerRadius.toInt()).toFloat()
            this.showShimmer = typedArray.getBoolean(R.styleable.SkeletonLayout_showShimmer, showShimmer)
            this.shimmerColor = typedArray.getColor(R.styleable.SkeletonLayout_shimmerColor, shimmerColor)
            this.shimmerDurationInMillis = typedArray.getInt(R.styleable.SkeletonLayout_shimmerDurationInMillis, shimmerDurationInMillis.toInt()).toLong()
            this.shimmerDirection = SkeletonShimmerDirection.valueOf(typedArray.getInt(R.styleable.SkeletonLayout_shimmerDirection, shimmerDirection.ordinal)) ?: SkeletonLayout.DEFAULT_SHIMMER_DIRECTION
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
                        .also { mask -> mask.mask(this, maskCornerRadius) }
                } else {
                    Log.e(tag(), "Failed to mask view with invalid width and height")
                }
            }
        } else {
            Log.e(tag(), "Skipping invalidation until view is rendered")
        }
    }
}
