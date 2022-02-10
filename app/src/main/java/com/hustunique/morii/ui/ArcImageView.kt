package com.hustunique.morii.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * 弧形的view
 */
class ArcImageView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    AppCompatImageView(
        context!!, attrs, 0
    ) {
    /*
     *弧形高度
     */
    private val mArcHeight = 100
    override fun onDraw(canvas: Canvas) {
        val path = Path()
        path.moveTo(0f, 0f)
        path.lineTo(0f, (height - mArcHeight).toFloat())
        path.quadTo(
            (width / 2).toFloat(),
            (height + mArcHeight).toFloat(),
            width.toFloat(),
            (height - mArcHeight).toFloat()
        )
        path.lineTo(width.toFloat(), 0f)
        path.close()
        canvas.clipPath(path)
        super.onDraw(canvas)
    }
}