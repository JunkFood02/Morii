package com.hustunique.musica.player;

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
    public ExoPlayer player = new ExoPlayer.Builder(context).build();
    private static final String TAG = "AudioExoPlayerUtil";
    public void addItems() {
        for (MusicTab musicTab : musicTabList
        ) {
            Uri uri=Uri.parse("android.resource://"
                    + context.getPackageName()+"/" + musicTab.getMusicResId());
            Log.d(TAG, "addItems: "+uri.toString());
            MediaItem mediaItem = MediaItem.fromUri(uri);
            player.addMediaItem(mediaItem);
        }
        player.setRepeatMode(ExoPlayer.REPEAT_MODE_ONE);
        player.prepare();
    }

    public void play(int num) {
        player.seekToDefaultPosition(num);
        player.play();
    }
}
