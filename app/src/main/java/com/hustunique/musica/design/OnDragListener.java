package com.hustunique.musica.design;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class OnDragListener implements View.OnLongClickListener {
    private int imageID;
    public OnDragListener(int resourceId) {
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
        return false;
    }
}
