package com.hustunique.morii.design;

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
import android.widget.LinearLayout;

import morii.R;
import com.hustunique.morii.util.MyApplication;

public class Drag implements View.OnTouchListener {
    private static LinearLayout _delete_area ;
    private int imageID,resId;
    private boolean dragFromSquares = false;
    private long start;
    private float x, y;
    Vibrator v = (Vibrator) MyApplication.context.getSystemService(Context.VIBRATOR_SERVICE);

    public Drag(int iconId) {
        imageID = iconId;
    }

    public Drag(int iconId, boolean dragFromSquares,int resId) {
        this.dragFromSquares = dragFromSquares;
        imageID = iconId;
        this.resId = resId;
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
                    MyApplication.getSoundItemThroughIconID(imageID).reResId(resId);
                    //传递被拖动View数据
                    makeVibrate();
                    Intent intent = new Intent();
                    intent.putExtra("ImageID", imageID);
                    Log.d("imageViewID", imageID + ""+"draged");
                    ClipData.Item item = new ClipData.Item(intent);
                    String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_INTENT};
                    View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
                    ClipData dragDate = new ClipData("wdwd", mimeTypes, item);
                    v.startDrag(dragDate, shadow, null, View.DRAG_FLAG_GLOBAL);
                    if (dragFromSquares) {
                        ImageView imageView = (ImageView) v;
                        imageView.setImageResource(R.drawable.square);
                        _delete_area.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
        return true;
    }

    private boolean isLongPressed(float thisX, float thisY) {
        long lastTime = System.currentTimeMillis() - start;
        float offsetX = Math.abs(thisX - x);
        float offsetY = Math.abs(thisY - y);
        return (lastTime >= 50 && offsetX <= 50 && offsetY <= 25);
    }

    private void makeVibrate() {
        long vibrateDuration=50;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(vibrateDuration, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(vibrateDuration);
        }
    }
    public static void setDelete_area(LinearLayout delete_area){
        _delete_area = delete_area;
    }
}
