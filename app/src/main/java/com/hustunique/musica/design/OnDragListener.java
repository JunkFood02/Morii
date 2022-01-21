package com.hustunique.musica.design;

import android.content.ClipData;
import android.content.ClipDescription;
import android.view.View;

public class OnDragListener implements View.OnLongClickListener {
    private View itemView;
    public OnDragListener(View view){
        itemView = view;
    }
    @Override
    public boolean onLongClick(View v) {
        ClipData.Item item = new ClipData.Item("helll");
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        View.DragShadowBuilder shadow = new View.DragShadowBuilder(itemView);
        ClipData dragDate = new ClipData("wdwd",mimeTypes,item);
        itemView.startDrag(dragDate,shadow,null,View.DRAG_FLAG_GLOBAL);
        return false;
    }
}
