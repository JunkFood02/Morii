package com.hustunique.musica.design;

import android.os.Bundle;
import android.view.DragEvent;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hustunique.musica.R;

public class MixActivity extends AppCompatActivity implements MixContract.IView{

    private MixContract.IPresenter presenter;
    private RecyclerView recyclerView;
    private ImageView[] squares = new ImageView[9];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix);

        presenter = new MixPresenter(this);
        initUI();

    }

    private void initUI() {
        squares[0] = findViewById(R.id.square1);
        squares[1] = findViewById(R.id.square2);
        squares[2] = findViewById(R.id.square3);
        squares[3] = findViewById(R.id.square4);
        squares[4] = findViewById(R.id.square5);
        squares[5] = findViewById(R.id.square6);
        squares[6] = findViewById(R.id.square7);
        squares[7] = findViewById(R.id.square8);
        squares[8] = findViewById(R.id.square9);
        for(ImageView imageView:squares){
            imageView.setOnDragListener(((v, event) -> {
                switch (event.getAction()){
                    case DragEvent.ACTION_DROP:
                        imageView.setImageResource(R.drawable.x1);
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


    }
}