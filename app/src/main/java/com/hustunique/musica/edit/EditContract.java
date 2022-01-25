package com.hustunique.morii.edit;

import android.content.Context;

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
