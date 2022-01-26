package com.hustunique.morii.design;

import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import morii.R;
import com.hustunique.morii.edit.EditActivity;
import com.hustunique.morii.util.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MixActivity extends AppCompatActivity implements IMixDesign.IView{
    private LinearLayout delete_area;
    public static final int DRAG_SQUARE = 1;
    private IMixDesign.IPresenter presenter;
    private final List<ImageView> squareList = new ArrayList<>(9);
    //private final ImageView[] squares = new ImageView[9];
    private final Map<ImageView,SoundItem>imageViewIntegerMap = new HashMap<>(9);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix);

        presenter = new MixPresenter(this);
        initUI();

    }

    private void initUI() {
        MyApplication.clearAllResIdInSoundItemList();
        delete_area = findViewById(R.id.delete_area);
        Drag.setDelete_area(delete_area);
        ImageView complete_image = findViewById(R.id.imageView_edit_complete);
        ImageView back_image = findViewById(R.id.imageView_edit_back);
        TextView complete_text = findViewById(R.id.textView_edit_complete);
        TextView back_text = findViewById(R.id.textView_edit_back);
        delete_area.setVisibility(View.GONE);
        squareList.add(findViewById(R.id.square1));
        squareList.add(findViewById(R.id.square2));
        squareList.add(findViewById(R.id.square3));
        squareList.add(findViewById(R.id.square4));
        squareList.add(findViewById(R.id.square5));
        squareList.add(findViewById(R.id.square6));
        squareList.add(findViewById(R.id.square7));
        squareList.add(findViewById(R.id.square8));
        squareList.add(findViewById(R.id.square9));

        complete_image.setOnClickListener(v->{
            Intent intent = new Intent(this, EditActivity.class);
            startActivity(intent);
        });
        complete_text.setOnClickListener(v->{
            Intent intent = new Intent(this, EditActivity.class);
            startActivity(intent);
        });
        back_text.setOnClickListener(v->onBackPressed());
        back_image.setOnClickListener(v->onBackPressed());
        for(ImageView square:squareList){
            imageViewIntegerMap.put(square, MyApplication.soundItemList.get(7));
        }
        /*
        for (ImageView square : squares) {
            imageViewIntegerMap.put(square, MyApplication.soundItemList.get(7));
        }

         */
        for (ImageView imageView:imageViewIntegerMap.keySet()){
            if(imageViewIntegerMap.get(imageView).getIconResId()!=R.drawable.square){
                imageView.setOnTouchListener(new Drag(imageViewIntegerMap.get(imageView).getIconResId(),true,squareList.indexOf(imageView)));
            }
            imageView.setOnDragListener(((v, event) -> {
                int iconId;
                int resId;
                if (event.getAction() == DragEvent.ACTION_DROP) {
                    iconId = event.getClipData().getItemAt(0).getIntent().getIntExtra("ImageID", R.drawable.x1);
                    resId = squareList.indexOf(imageView);
                    imageView.setImageResource(iconId);
                    MyApplication.getSoundItemThroughIconID(iconId).addResId(resId);
                    imageViewIntegerMap.put(imageView, MyApplication.getSoundItemThroughIconID(iconId));
                    imageView.setOnTouchListener(new Drag(iconId, true,resId));
                    delete_area.setVisibility(View.GONE);
                }
                return true;
            }));
        }

        setRecyclerView();
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView03);
        WhiteNoiseAdapter adapter = new WhiteNoiseAdapter(this, presenter);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        StaggeredGridLayoutManager manager= new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnDragListener((v, event) -> {
            int iconId;

            if (event.getAction() == DragEvent.ACTION_DROP) {
                iconId = event.getClipData().getItemAt(0).getIntent().getIntExtra("ImageID", R.drawable.x1);
                MyApplication.getSoundItemThroughIconID(iconId).clearResId();
                delete_area.setVisibility(View.GONE);
            }
            return true;

        });

    }
}