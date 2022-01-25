package com.hustunique.musica.design;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hustunique.musica.R;
import com.hustunique.musica.edit.EditActivity;

import java.util.HashMap;
import java.util.Map;

public class MixActivity extends AppCompatActivity implements IMixDesign.IView{
    public static final int DRAG_SQUARE = 1;
    private ImageView complete_image,back_image;
    private TextView complete_text,back_text;
    private IMixDesign.IPresenter presenter;
    private RecyclerView recyclerView;
    private ImageView[] squares = new ImageView[9];
    private Map<ImageView,Integer>imageViewIntegerMap = new HashMap<>(9);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix);

        presenter = new MixPresenter(this);
        initUI();

    }

    private void initUI() {
        complete_image = findViewById(R.id.imageView_edit_complete);
        back_image = findViewById(R.id.imageView_edit_back);
        complete_text = findViewById(R.id.textView_edit_complete);
        back_text = findViewById(R.id.textView_edit_back);
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
        back_text.setOnClickListener(v->{
            finish();
        });
        back_image.setOnClickListener(v->{
            finish();
        });
        for(int i = 0;i<squares.length;i++){
            imageViewIntegerMap.put(squares[i],R.drawable.square);
        }
        for (ImageView imageView:imageViewIntegerMap.keySet()){
            // imageView.setOnLongClickListener(new DragListener(imageViewIntegerMap.get(imageView),true));
            imageView.setOnTouchListener(new Drag(imageViewIntegerMap.get(imageView),true));
            imageView.setOnDragListener(((v, event) -> {
                switch (event.getAction()){
                    case DragEvent.ACTION_DROP:
                        imageView.setImageResource(event.getClipData().getItemAt(0).getIntent().getIntExtra("ImageID",R.drawable.x1));
                        imageViewIntegerMap.put(imageView,event.getClipData().getItemAt(0).getIntent().getIntExtra("ImageID",R.drawable.x1));
                        //imageView.setOnLongClickListener(new DragListener(imageViewIntegerMap.get(imageView),true));
                        imageView.setOnTouchListener(new Drag(imageViewIntegerMap.get(imageView),true));
                        break;
                }
                return true;
            }));
        }

        setRecyclerView();
    }

    private void setRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView03);
        WhiteNoiseAdapter adapter = new WhiteNoiseAdapter(this, presenter);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        StaggeredGridLayoutManager manager= new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()){
                    case DragEvent.ACTION_DROP:
                        break;
                }
                return true;
            }
        });

    }
}