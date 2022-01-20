package com.hustunique.musica.player;

import com.hustunique.musica.R;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {
    List<Track> tracks = new ArrayList<>();
    Track MusicTrack=new Track();

    public void addTrack(Track t) {
        tracks.add(t);
    }

    public void addTrack(int resId) {
        Track t = new Track(resId);
        addTrack(t);
    }

    public void removeTrack(int resId) {
        for (Track t : tracks) {
            if (t.getId() == resId) {
                t.release();
                tracks.remove(t);
                return;
            }
        }
    }

    public void switchMusicTrack(int resId) {
        new Thread(() -> {
            MusicTrack.reset();
            MusicTrack.play(resId);
        }).start();

    }

    public void exchangeAudio(int trackId, int resId) {
        for (Track t : tracks) {
            if (t.getId() == trackId) {
                t.reset();
                t.play(resId);
                return;
            }
        }
    }

    public void pause() {
        for (Track t : tracks
        ) {
            t.pause();
        }
    }

    public void play() {
        for (Track t : tracks
        ) {
            t.play();
        }
    }

    public MusicPlayer() {
        tracks.add(MusicTrack);
    }
}
