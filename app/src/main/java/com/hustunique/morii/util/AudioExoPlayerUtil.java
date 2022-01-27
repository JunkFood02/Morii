package com.hustunique.morii.util;

import static com.hustunique.morii.util.MyApplication.context;
import static com.hustunique.morii.util.MyApplication.musicTabList;
import static com.hustunique.morii.util.MyApplication.soundItemList;

import android.net.Uri;
import android.util.Log;
import android.util.Pair;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.hustunique.morii.music.MusicTab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AudioExoPlayerUtil {
    public static final int NEXT = 1;
    public static final int PREVIOUS = -1;
    private static final float[] volumes = {0.45f, 0.3f, 0.15f};
    private static final ExoPlayer musicPlayer = new ExoPlayer.Builder(context).build();
    private static final String TAG = "AudioExoPlayerUtil";
    private static final List<ExoPlayer> soundPlayerList = new ArrayList<>();

    public static void initMusicPlayer() {
        for (MusicTab musicTab : musicTabList
        ) {
            MediaItem mediaItem = MediaItem.fromUri(UriParser(musicTab.getMusicResId()));
            musicPlayer.addMediaItem(mediaItem);
        }
        musicPlayer.setVolume(0.2f);
        musicPlayer.setRepeatMode(ExoPlayer.REPEAT_MODE_ONE);
        musicPlayer.prepare();
    }

    public static void initSoundPlayer() {
        for (int i = 0; i <= 8; i++) {
            ExoPlayer player = new ExoPlayer.Builder(context).build();
            player.setVolume(volumes[i/3]);
            player.setRepeatMode(ExoPlayer.REPEAT_MODE_ONE);
            soundPlayerList.add(player);
        }
    }

    public static void playMusic(int num) {
        musicPlayer.seekToDefaultPosition(num);
        musicPlayer.play();
    }

    public static void startPlayingSoundItem(int soundItemId, int position) {
        ExoPlayer player = soundPlayerList.get(position);
        int soundResId = soundItemList.get(soundItemId).getSoundResIds().get(position % 3);
        player.setMediaItem(MediaItem.fromUri(UriParser(soundResId)));
        player.prepare();
        player.play();
    }

    public static void stopPlayingSoundItem(int position) {
        ExoPlayer player = soundPlayerList.get(position);
        player.pause();
        player.removeMediaItem(0);
    }

    public static void stopAllSoundPlayers() {
        for (ExoPlayer player : soundPlayerList) {
            if (player.isPlaying())
                player.pause();
        }
    }

    public static void startAllSoundPlayers() {
        for (ExoPlayer player : soundPlayerList) {
            if (player.getPlaybackState() == ExoPlayer.STATE_READY)
                player.play();
        }
    }

    public static void pauseMusicPlayer() {
        musicPlayer.pause();
    }

    private static Uri UriParser(int resId) {
        return Uri.parse("android.resource://"
                + context.getPackageName() + "/" + resId);
    }
}
