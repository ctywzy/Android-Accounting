package com.example.administrator.finalworks;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;


public class MusicService extends Service {

    private MediaPlayer mediaPlayer;

    public MusicService() {
    }

    public class MyBinder extends Binder {
        public void play() {
            MusicService.this.play();
        }

        public void pauseMusic() {
            MusicService.this.pauseMusic();
        }
        public void resumeMusic() {
            MusicService.this.resume();
        }
    }

    private void pauseMusic() {
        mediaPlayer.pause();
    }


    @Override
    public IBinder onBind(Intent intent) {

        return new MyBinder();
    }

    public void resume() {
        mediaPlayer.start();
    }

    public void play() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            File musicFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Download/music.mp3");
            mediaPlayer.setDataSource(musicFile.getAbsolutePath());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }


}
