package com.hustunique.musica.design;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.hustunique.musica.R;
import com.hustunique.musica.util.MyApplication;

public class Drag implements View.OnTouchListener {
    private int imageID;
    private boolean dragFromSquares = false;
    private long start;
    private float x, y;
    Vibrator v = (Vibrator) MyApplication.context.getSystemService(Context.VIBRATOR_SERVICE);

    public Drag(int resourceId) {
        imageID = resourceId;
    }

    public Drag(int resourceId, boolean dragFromSquares) {
        this.dragFromSquares = dragFromSquares;
        imageID = resourceId;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                start = System.currentTimeMillis();
                x = event.getX();
                y = event.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                if (isLongPressed(event.getX(), event.getY())) {
                    //传递被拖动View数据
                    makeVibrate();
                    Intent intent = new Intent();
                    intent.putExtra("ImageID", imageID);
                    Log.d("imageViewID", imageID + "");
                    ClipData.Item item = new ClipData.Item(intent);
                    String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_INTENT};
                    View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
                    ClipData dragDate = new ClipData("wdwd", mimeTypes, item);
                    v.startDrag(dragDate, shadow, null, View.DRAG_FLAG_GLOBAL);
                    if (dragFromSquares) {
                        ImageView imageView = (ImageView) v;
                        imageView.setImageResource(R.drawable.square);
                    }
                }
                break;
        }
        return true;
    }

    private boolean isLongPressed(float thisX, float thisY) {
        long lastTine = System.currentTimeMillis() - start;
        float offsetX = Math.abs(thisX - x);
        float offsetY = Math.abs(thisY - y);
        return (lastTine >= 50 && offsetX <= 50 && offsetY <= 25);
    }

    private void makeVibrate() {
        long vibrateDuration=50;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(vibrateDuration, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(vibrateDuration);
        }
    }
}
