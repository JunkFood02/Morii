package com.hustunique.morii.home

interface HomePageContract {
    interface IPresenter {
        val uI: Unit
    }

    interface IView {
        fun setRecyclerView(list: MutableList<MusicDiaryItem?>)
    }
}