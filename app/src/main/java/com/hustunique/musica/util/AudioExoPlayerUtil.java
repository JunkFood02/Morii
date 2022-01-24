package com.hustunique.musica.util;

import static com.hustunique.musica.util.MyApplication.context;
import static com.hustunique.musica.util.MyApplication.musicTabList;

import android.net.Uri;
import android.util.Log;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.hustunique.musica.music.MusicTab;

public class AudioExoPlayerUtil {
    public static final int NEXT = 1;
    public static final int PREVIOUS = -1;
    private static final ExoPlayer musicPlayer = new ExoPlayer.Builder(context).build();
    private static final String TAG = "AudioExoPlayerUtil";

    public void initMusicPlayer() {
        for (MusicTab musicTab : musicTabList
        ) {
            Uri uri = Uri.parse("android.resource://"
                    + context.getPackageName() + "/" + musicTab.getMusicResId());
            MediaItem mediaItem = MediaItem.fromUri(uri);
            musicPlayer.addMediaItem(mediaItem);
        }
        musicPlayer.setRepeatMode(ExoPlayer.REPEAT_MODE_ONE);
        musicPlayer.prepare();
    }

    public static void play(int num) {
        musicPlayer.seekToDefaultPosition(num);
        musicPlayer.play();
    }

    public static void pause(){
        musicPlayer.pause();
    }
}
