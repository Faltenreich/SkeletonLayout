package com.faltenreich.skeletonlayout

import android.content.Context
import android.graphics.*
import android.support.annotation.ColorRes
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
        @ColorRes private var maskColorResId: Int = DEFAULT_MASK_COLOR,
        private var cornerRadius: Float = DEFAULT_CORNER_RADIUS,
        private var showShimmer: Boolean = DEFAULT_SHIMMER_SHOW,
        @ColorRes private var shimmerColorResId: Int =  DEFAULT_SHIMMER_COLOR,
        private var shimmerDurationInMillis: Long = DEFAULT_SHIMMER_DURATION_IN_MILLIS
) : FrameLayout(context, attrs, defStyleAttr), Skeleton {

    constructor(
            originView: View,
            @ColorRes maskColorResId: Int,
            cornerRadius: Float,
            showShimmer: Boolean,
            @ColorRes shimmerColorResId: Int,
            shimmerDuration: Long = 0
    ) : this(originView.context, null, 0, originView, maskColorResId, cornerRadius, showShimmer, shimmerColorResId, shimmerDuration)

    private var maskColor = ContextCompat.getColor(context, maskColorResId)
    private var shimmerColor = ContextCompat.getColor(context, shimmerColorResId)

    private var mask: SkeletonMask? = null

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.SkeletonLayout, 0, 0)
            maskColor = typedArray.getColor(R.styleable.SkeletonLayout_maskColor, maskColor)
            cornerRadius = typedArray.getFloat(R.styleable.SkeletonLayout_cornerRadius, cornerRadius)
            showShimmer = typedArray.getBoolean(R.styleable.SkeletonLayout_showShimmer, showShimmer)
            shimmerColor = typedArray.getColor(R.styleable.SkeletonLayout_shimmerColor, shimmerColor)
            shimmerDurationInMillis = typedArray.getInt(R.styleable.SkeletonLayout_shimmerDurationInMillis, shimmerDurationInMillis.toInt()).toLong()
            typedArray.recycle()
        }
        originView?.let { addView(it) }
    }

    override fun show() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hide() {
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
        mask?.let { canvas.drawBitmap(it.bitmap, 0f, 0f, it.paint) }
    }

    private fun mask() {
        mask = if (showShimmer) SkeletonMaskShimmer(this, maskColor, shimmerColor, shimmerDurationInMillis) else SkeletonMaskSolid(this, maskColor)
        mask?.let {
            val xferPaint = Paint().apply {
                xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
                isAntiAlias = cornerRadius > 0
            }
            maskViews(it.canvas, xferPaint, this, this)
        }
    }

    private fun maskViews(canvas: Canvas, paint: Paint, view: View, root: ViewGroup) {
        (view as? ViewGroup)?.let { it.views().forEach { maskViews(canvas, paint, it, root) } } ?: maskView(canvas, paint, view, root)
    }

    private fun maskView(canvas: Canvas, paint: Paint, view: View, root: ViewGroup) {
        val rect = Rect()
        view.getDrawingRect(rect)
        root.offsetDescendantRectToMyCoords(view, rect)

        if (cornerRadius > 0) {
            val rectF = RectF(rect.left.toFloat(), rect.top.toFloat(), rect.right.toFloat(), rect.bottom.toFloat())
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)
        } else {
            canvas.drawRect(rect, paint)
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