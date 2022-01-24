package com.hustunique.musica.edit;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public interface EditContract {
    interface IPresenter {
        void getUI();
        void getPicture();

    };
    interface IModel{
        void setAppCompatActivityUse(Context context);

        void getPicture();

    }

    interface IListener{
        void setIt(String path);
    }

    interface IView {

        void setAddPhoto(String path);
    };
}
