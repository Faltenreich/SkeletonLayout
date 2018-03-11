package com.faltenreich.skeletonview

import android.content.Context
import android.graphics.*
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

internal class SkeletonMaskLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        originView: View? = null,
        @ColorInt private val maskColor: Int = 0,
        private val cornerRadius: Float = 0f,
        private val showShimmer: Boolean = true,
        @ColorInt private val shimmerColor: Int = 0,
        private val shimmerDurationInMillis: Long = 0,
        private val shimmerAngle: Float = 0f
) : FrameLayout(context, attrs, defStyleAttr) {

    constructor(
            originalView: View,
            @ColorInt maskColor: Int,
            cornerRadius: Float,
            showShimmer: Boolean,
            @ColorInt shimmerColor: Int,
            shimmerDuration: Long = 0,
            shimmerAngle: Float = 0f
    ) : this(originalView.context, null, 0, originalView, maskColor, cornerRadius, showShimmer, shimmerColor, shimmerDuration, shimmerAngle)

    private lateinit var mask: SkeletonMask
    private var shimmer: SkeletonShimmer? = null

    init {
        originView?.let {
            layoutParams = it.layoutParams
            it.visibility = View.INVISIBLE
            addView(it)
            setWillNotDraw(false)
        } ?: Log.e(tag(), "Missing view to mask")
    }

    override fun onVisibilityChanged(changedView: View?, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        shimmer?.invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(mask.bitmap, 0f, 0f, mask.paint)
        shimmer?.let { canvas.drawBitmap(it.bitmap, 0f, 0f, it.paint) }
    }

    fun bind() {
        setOnLayoutChangeListener {
            mask()
            shimmer?.invalidate()
        }
    }

    fun unbind() {
        shimmer?.stop()
    }

    private fun mask() {
        mask = SkeletonMask(this, maskColor)

        val xferPaint = Paint().apply {
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
            isAntiAlias = cornerRadius > 0
        }

        if (showShimmer) {
            shimmer = SkeletonShimmer(this, shimmerColor, shimmerDurationInMillis, shimmerAngle, width.toFloat() / 2)
        }

        maskViews(mask.canvas, shimmer?.canvas, xferPaint, this, this)
    }

    private fun maskViews(maskCanvas: Canvas, shimmerCanvas: Canvas?, paint: Paint, view: View, root: ViewGroup) {
        (view as? ViewGroup)?.let { it.views().forEach { maskViews(maskCanvas, shimmerCanvas, paint, it, root) } } ?: maskView(maskCanvas, shimmerCanvas, paint, view, root)
    }

    private fun maskView(maskCanvas: Canvas, shimmerCanvas: Canvas?, paint: Paint, view: View, root: ViewGroup) {
        val rect = Rect()
        view.getDrawingRect(rect)
        root.offsetDescendantRectToMyCoords(view, rect)

        if (cornerRadius > 0) {
            val rectF = RectF(rect.left.toFloat(), rect.top.toFloat(), rect.right.toFloat(), rect.bottom.toFloat())
            maskCanvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)
            shimmerCanvas?.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)
        } else {
            maskCanvas.drawRect(rect, paint)
            shimmerCanvas?.drawRect(rect, paint)
        }
    }
}