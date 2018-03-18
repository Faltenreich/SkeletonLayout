package com.faltenreich.skeletonlayout

import android.content.Context
import android.graphics.*
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.faltenreich.skeletonlayout.mask.SkeletonMask
import com.faltenreich.skeletonlayout.mask.SkeletonMaskShimmer
import com.faltenreich.skeletonlayout.mask.SkeletonMaskSolid

class SkeletonLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        originView: View? = null,
        @ColorInt maskColor: Int = ContextCompat.getColor(context, DEFAULT_MASK_COLOR),
        cornerRadius: Float = DEFAULT_CORNER_RADIUS,
        showShimmer: Boolean = DEFAULT_SHIMMER_SHOW,
        @ColorInt shimmerColor: Int =  ContextCompat.getColor(context, DEFAULT_SHIMMER_COLOR),
        shimmerDurationInMillis: Long = DEFAULT_SHIMMER_DURATION_IN_MILLIS
) : FrameLayout(context, attrs, defStyleAttr), Skeleton {

    internal constructor(
            originView: View,
            @ColorInt maskColor: Int,
            cornerRadius: Float,
            showShimmer: Boolean,
            @ColorInt shimmerColor: Int,
            shimmerDuration: Long = 0
    ) : this(originView.context, null, 0, originView, maskColor, cornerRadius, showShimmer, shimmerColor, shimmerDuration)

    override var maskColor: Int = maskColor
        set(value) {
            field = value
            mask()
        }

    override var maskCornerRadius: Float = cornerRadius
        set(value) {
            field = value
            mask()
        }

    override var showShimmer: Boolean = showShimmer
        set(value) {
            field = value
            mask()
        }

    override var shimmerColor: Int = shimmerColor
        set(value) {
            field = value
            mask()
        }

    override var shimmerDurationInMillis: Long = shimmerDurationInMillis
        set(value) {
            field = value
            mask()
        }

    private var mask: SkeletonMask? = null

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.SkeletonLayout, 0, 0)
            this.maskColor = typedArray.getColor(R.styleable.SkeletonLayout_maskColor, maskColor)
            this.maskCornerRadius = typedArray.getFloat(R.styleable.SkeletonLayout_cornerRadius, cornerRadius)
            this.showShimmer = typedArray.getBoolean(R.styleable.SkeletonLayout_showShimmer, showShimmer)
            this.shimmerColor = typedArray.getColor(R.styleable.SkeletonLayout_shimmerColor, shimmerColor)
            this.shimmerDurationInMillis = typedArray.getInt(R.styleable.SkeletonLayout_shimmerDurationInMillis, shimmerDurationInMillis.toInt()).toLong()
            typedArray.recycle()
        }
        originView?.let { addView(it) }
    }

    override fun showOriginal() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSkeleton() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isSkeleton(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (!isInEditMode) {
            if (childCount > 0) {
                views().forEach { it.visibility = View.INVISIBLE }
                setWillNotDraw(false)
                mask()
                mask?.invalidate()
            } else {
                Log.e(tag(), "Missing view to mask")
            }
        }
    }

    override fun onVisibilityChanged(changedView: View?, visibility: Int) {
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
        mask?.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mask?.stop()
    }

    override fun onDraw(canvas: Canvas) {
        mask?.draw(canvas)
    }

    private fun mask() {
        mask?.stop()
        mask = if (showShimmer) SkeletonMaskShimmer(this, maskColor, shimmerColor, shimmerDurationInMillis) else SkeletonMaskSolid(this, maskColor)

        val xferPaint = Paint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
            isAntiAlias = maskCornerRadius > 0
        }

        maskViews(xferPaint, this, this)
    }

    private fun maskViews(paint: Paint, view: View, root: ViewGroup) {
        (view as? ViewGroup)?.let { it.views().forEach { maskViews(paint, it, root) } } ?: maskView(paint, view, root)
    }

    private fun maskView(paint: Paint, view: View, root: ViewGroup) {
        val rect = Rect()
        view.getDrawingRect(rect)
        root.offsetDescendantRectToMyCoords(view, rect)

        if (maskCornerRadius > 0) {
            val rectF = RectF(rect.left.toFloat(), rect.top.toFloat(), rect.right.toFloat(), rect.bottom.toFloat())
            mask?.draw(rectF, maskCornerRadius, paint)
        } else {
            mask?.draw(rect, paint)
        }
    }

    companion object {
        val DEFAULT_MASK_COLOR = R.color.skeleton_mask
        const val DEFAULT_CORNER_RADIUS = 25f
        const val DEFAULT_SHIMMER_SHOW = true
        val DEFAULT_SHIMMER_COLOR = R.color.skeleton_shimmer
        const val DEFAULT_SHIMMER_DURATION_IN_MILLIS = 2000L
    }
}