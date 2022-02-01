package com.hustunique.morii.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * 弧形的view
 */
public class ArcImageView extends AppCompatImageView {
    /*
     *弧形高度
     */
    private final int mArcHeight = 100;

    public ArcImageView(Context context) {
        this(context, null);
    }

    public ArcImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(0, getHeight() - mArcHeight);
        path.quadTo(getWidth() / 2, getHeight() + mArcHeight, getWidth(), getHeight() - mArcHeight);
        path.lineTo(getWidth(), 0);
        path.close();
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}