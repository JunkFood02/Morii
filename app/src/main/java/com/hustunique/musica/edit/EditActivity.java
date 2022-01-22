package com.hustunique.musica.edit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hustunique.musica.R;

public class EditActivity extends AppCompatActivity implements EditContract.IView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }
}