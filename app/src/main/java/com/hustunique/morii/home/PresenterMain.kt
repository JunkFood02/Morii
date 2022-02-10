package com.hustunique.morii.home

import com.hustunique.morii.util.MyApplication

class PresenterMain internal constructor(var view: HomePageContract.IView) :
    HomePageContract.IPresenter {
    override val uI: Unit
        get() {
            view.setRecyclerView(MyApplication.Companion.musicDiaryList)
        }
}