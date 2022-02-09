package com.hustunique.morii.util;

import static com.hustunique.morii.util.MyApplication.context;
import static com.hustunique.morii.util.MyApplication.musicTabList;
import static com.hustunique.morii.util.MyApplication.soundItemList;

import android.net.Uri;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.hustunique.morii.music.MusicTab;

import java.util.ArrayList;
import java.util.List;

public class AudioExoPlayerUtil {
    private static final float[] volumes = {0.3f, 0.2f, 0.1f};
    private static final ExoPlayer musicPlayer = new ExoPlayer.Builder(context).build();
    private static final String TAG = "AudioExoPlayerUtil";
    private static final List<ExoPlayer> soundPlayerList = new ArrayList<>();
    private static onReadyListener listener;
    private static long duration;

    public static void initMusicPlayer() {
        for (MusicTab musicTab : musicTabList
        ) {
            MediaItem mediaItem = MediaItem.fromUri(UriParser(musicTab.getMusicResId()));
            musicPlayer.addMediaItem(mediaItem);
        }
        musicPlayer.setVolume(0.6f);
        musicPlayer.setRepeatMode(ExoPlayer.REPEAT_MODE_ONE);
        musicPlayer.prepare();
        musicPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                Player.Listener.super.onPlaybackStateChanged(playbackState);
                if (playbackState == ExoPlayer.STATE_READY) {
                    duration = musicPlayer.getDuration();
                    if (listener != null && duration > 0) {
                        listener.onReady(musicPlayer.getDuration());

                    }
                }
            }
        });
    }

    public static void initSoundPlayer() {
        for (int i = 0; i <= 8; i++) {
            ExoPlayer player = new ExoPlayer.Builder(context).build();
            player.setVolume(volumes[i / 3]);
            player.setRepeatMode(ExoPlayer.REPEAT_MODE_ONE);
            soundPlayerList.add(player);
        }
    }

    public static void playMusic(int num) {
        musicPlayer.seekToDefaultPosition(num);
        musicPlayer.play();
    }

    public static void setSoundPlayer(int soundItemId, int position) {
        ExoPlayer player = soundPlayerList.get(position);
        int soundResId = soundItemList.get(soundItemId).getSoundResIds().get(position % 3);
        player.setMediaItem(MediaItem.fromUri(UriParser(soundResId)));
        player.prepare();
    }

    public static void stopPlayingSoundItem(int position) {
        ExoPlayer player = soundPlayerList.get(position);
        player.pause();
        player.removeMediaItem(0);
    }

    public static void stopAllSoundPlayers() {
        for (ExoPlayer player : soundPlayerList) {
            {
                if (player.isPlaying())
                    player.pause();
            }
        }
    }

    public static void startSoundPlayer(int position) {
        soundPlayerList.get(position).play();
    }

    public static void resetAllSoundPlayers() {
        for (ExoPlayer player : soundPlayerList) {
            player.removeMediaItem(0);
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

    public static void pauseAllPlayers() {
        stopAllSoundPlayers();
        pauseMusicPlayer();
    }

    public static void startAllPlayers() {
        startAllSoundPlayers();
        musicPlayer.play();
    }

    public static long getCurrentPosition() {
        return musicPlayer.getCurrentPosition();
    }

    public static boolean isPlaying() {
        return musicPlayer.isPlaying();
    }

    public static long getDuration() {
        return musicPlayer.getDuration();

    }

    public static void setListener(onReadyListener listener) {
        AudioExoPlayerUtil.listener = listener;
    }
}
