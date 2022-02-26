package com.hustunique.morii.edit

import android.content.Context

interface EditContract {
    interface IPresenter {
        val picture: Unit
    }

    interface IModel {
        fun setAppCompatActivityUse(context: Context)
        val picture: Unit
    }

    interface IListener {
        fun setIt(path: String)
    }

    interface IView {
        fun setAddPhoto(path: String)
    }
}