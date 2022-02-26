package com.hustunique.morii.edit

import com.hustunique.morii.edit.EditContract.IListener
import com.hustunique.morii.edit.EditContract.IModel
import android.content.*

class EditPresenter internal constructor(context: Context) : EditContract.IPresenter, IListener {
    private val view: EditContract.IView
    private val model: IModel
    override val picture: Unit
        get() {
            model.picture
        }

    override fun setIt(path: String) {
        view.setAddPhoto(path)
    }

    init {
        view = context as EditContract.IView
        model = EditModel(this)
        model.setAppCompatActivityUse(context)
    }
}