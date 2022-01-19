package com.hustunique.musica.player;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {
    List<Track> tracks = new ArrayList<>();

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

    public void exchangeAudio(int trackId, int resId) {
        for (Track t : tracks) {
            if (t.getId() == trackId) {
                t.reset();
                t.init(resId);
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
}
