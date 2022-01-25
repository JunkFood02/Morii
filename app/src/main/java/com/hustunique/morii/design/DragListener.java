package com.hustunique.morii.design;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import morii.R;

public class DragListener implements View.OnLongClickListener {
    private int imageID;
    private boolean dragFromSquares = false;
    public DragListener(int resourceId) {
        imageID = resourceId;
    }
    public DragListener(int resourceId,boolean dragFromSquares){
        this.dragFromSquares = dragFromSquares;
        imageID = resourceId;
    }

    @Override
    public boolean onLongClick(View v) {
        //传递被拖动View数据
        Intent intent = new Intent();
        intent.putExtra("ImageID",imageID);
        Log.d("imageViewID", imageID+"");
        ClipData.Item item = new ClipData.Item(intent);
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_INTENT};
        View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
        ClipData dragDate = new ClipData("wdwd",mimeTypes,item);
        v.startDrag(dragDate,shadow,null,View.DRAG_FLAG_GLOBAL);
        if(dragFromSquares){
            ImageView imageView = (ImageView) v;
            imageView.setImageResource(R.drawable.square);
        }
        return false;
    }
}
