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

import java.util.HashMap;
import java.util.Map;

public class MixActivity extends AppCompatActivity implements IMixDesign.IView{
    private LinearLayout delete_area;
    public static final int DRAG_SQUARE = 1;
    private IMixDesign.IPresenter presenter;
    private final ImageView[] squares = new ImageView[9];
    private final Map<ImageView,Integer>imageViewIntegerMap = new HashMap<>(9);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix);

        presenter = new MixPresenter(this);
        initUI();

    }

    private void initUI() {
        delete_area = findViewById(R.id.delete_area);
        Drag.setDelete_area(delete_area);
        ImageView complete_image = findViewById(R.id.imageView_edit_complete);
        ImageView back_image = findViewById(R.id.imageView_edit_back);
        TextView complete_text = findViewById(R.id.textView_edit_complete);
        TextView back_text = findViewById(R.id.textView_edit_back);
        delete_area.setVisibility(View.GONE);
        squares[0] = findViewById(R.id.square1);
        squares[1] = findViewById(R.id.square2);
        squares[2] = findViewById(R.id.square3);
        squares[3] = findViewById(R.id.square4);
        squares[4] = findViewById(R.id.square5);
        squares[5] = findViewById(R.id.square6);
        squares[6] = findViewById(R.id.square7);
        squares[7] = findViewById(R.id.square8);
        squares[8] = findViewById(R.id.square9);
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
        for (ImageView square : squares) {
            imageViewIntegerMap.put(square, R.drawable.square);
        }
        for (ImageView imageView:imageViewIntegerMap.keySet()){
            // imageView.setOnLongClickListener(new DragListener(imageViewIntegerMap.get(imageView),true));
            if(imageViewIntegerMap.get(imageView)!=R.drawable.square){
                imageView.setOnTouchListener(new Drag(imageViewIntegerMap.get(imageView),true));
            }
            imageView.setOnDragListener(((v, event) -> {
                if (event.getAction() == DragEvent.ACTION_DROP) {
                    imageView.setImageResource(event.getClipData().getItemAt(0).getIntent().getIntExtra("ImageID", R.drawable.x1));
                    imageViewIntegerMap.put(imageView, event.getClipData().getItemAt(0).getIntent().getIntExtra("ImageID", R.drawable.x1));
                    //imageView.setOnLongClickListener(new DragListener(imageViewIntegerMap.get(imageView),true));
                    imageView.setOnTouchListener(new Drag(imageViewIntegerMap.get(imageView), true));
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
            switch (event.getAction()){
                case DragEvent.ACTION_DROP:
                    delete_area.setVisibility(View.GONE);
                    break;
            }
            return true;
        });

    }
}