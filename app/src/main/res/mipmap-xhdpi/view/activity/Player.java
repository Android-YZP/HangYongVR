package com.mytv365.view.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.fhrj.library.tools.ToolLog;

import java.io.IOException;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/11
 * Description:
 */
public class Player implements SurfaceHolder.Callback, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener {
    private int videoWidth;
    private int videoHeight;
    public MediaPlayer mediaPlayer;
    private SurfaceHolder surfaceHolder;
    private int position;

    public Player(SurfaceView surfaceView) {
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        ToolLog.i("Player:", "surfaceCreated");
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.setDisplay(surfaceHolder);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnPreparedListener(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        ToolLog.i("Player:", "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        ToolLog.i("Player:", "surfaceDestroyed");
    }

    public void play() {
        ToolLog.i("Player", "play");
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.prepareAsync();
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void stop() {
        ToolLog.i("Player", "stop");
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void setSourceUrl(String url) {
        ToolLog.i("Player", "setSourceUrl");
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(url);
            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        ToolLog.i("Player:", "onBufferingUpdate:" + percent);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        videoWidth = mediaPlayer.getVideoWidth();
        videoHeight = mediaPlayer.getVideoHeight();
        if (videoWidth != 0 && videoHeight != 0) {
            mp.start();
            if (position > 0) {
                mp.seekTo(position);
                position = 0;
            }
        }
        ToolLog.i("Player:", "onPrepared");
    }
}
