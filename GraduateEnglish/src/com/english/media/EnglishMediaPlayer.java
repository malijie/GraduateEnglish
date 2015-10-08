package com.english.media;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.english.util.Logger;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by vic_ma on 15/10/8.
 * 英语音频播放类
 */
public class EnglishMediaPlayer {
    private static final Object mObject = new Object();

    public static EnglishMediaPlayer mEnglishMediaPlayer = null;
    //media播放
    private MediaPlayer mMediaPlayer = null;

    private Context mContext = null;

    public static EnglishMediaPlayer getInstance(Context context){
        if(mEnglishMediaPlayer == null){
            synchronized (mObject){
                if(mEnglishMediaPlayer == null){
                    mEnglishMediaPlayer = new EnglishMediaPlayer(context);
                }
            }
        }
        return  mEnglishMediaPlayer;
    }

    private EnglishMediaPlayer(Context context){
        mMediaPlayer = new MediaPlayer();
        mContext = context;
    }

    /**
     * 播放单词音频
     */
    public void playTheWordTune(String wordName){
        mMediaPlayer.reset();
        try {
            AssetFileDescriptor afd = mContext.getAssets().openFd(wordName + ".wav");
            mMediaPlayer.setDataSource(afd.getFileDescriptor(),
                                        afd.getStartOffset(),afd.getLength());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止播放
     */
    public void stopPlay(){
        if(mMediaPlayer != null){
            mMediaPlayer.stop();
        }
    }
}
