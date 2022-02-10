package com.hustunique.morii.music

interface MusicSelectContract {
    interface IPresenter {
        fun switchMusic(position: Int)
        fun stopMusic()
    }

    interface IView
}