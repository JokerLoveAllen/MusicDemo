package com.joker.allenmp3.service;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.joker.allenmp3.application.MyApplication;
import com.joker.allenmp3.entity.DiscoverDailyData;
import com.joker.allenmp3.inneractivity.FormHotInfoActivity;
import com.joker.allenmp3.inneractivity.WebMusicActvity;

import java.io.IOException;

public class WebMusicService extends Service
       implements WebMusicActvity.webCallback,
        FormHotInfoActivity.musicState {
    private MediaPlayer player;
    private DiscoverDailyData.DataBean.ItemsBean.PostAudioBean Bean;
    public Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Message m = new Message();
            if (player.isPlaying()){
                Intent in = new Intent();
                in.setAction("SEEKBARUPDATE");
                in.putExtra("progress",player.getCurrentPosition());
                in.putExtra("max",player.getDuration());
                sendBroadcast(in);
            }
            handler.sendMessageDelayed(m,1000);
        }
    };
    public WebMusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.sendEmptyMessage(1);
        if (intent.getStringExtra("seekto")!=null){
            player.seekTo(intent.getIntExtra("UPDATE",0));
        } else {
            playMusic(intent);
        }
        WebMusicActvity.MusicState(this);
        WebMusicActvity.MusicPlay(this);
        WebMusicActvity.MusicTime(this);
        FormHotInfoActivity.getMusicPosition(this);
        player.setOnCompletionListener(new CompletionListener());
        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }



    private void playMusic(Intent intent) {
        player.reset();
        Bean = intent.getParcelableExtra("info");
        String source = Bean.getUrl();
        try {
            if (!source.startsWith("http://")){
                source="http://luoo-mp3.kssws.ks-cdn.com"+source;
            }
            player.setDataSource(source);
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean state() {
        if (player.isPlaying()){
            player.pause();
        } else {
            player.start();
        }
        return player.isPlaying();
    }

    @Override
    public boolean isWebPlaying() {
        if (player.isPlaying()){
            return true;
        } else
        return false;
    }

    @Override
    public int[] musicTime() {
        return new int[]{player.getCurrentPosition(),player.getDuration()};
    }

    @Override
    public DiscoverDailyData.DataBean.ItemsBean.PostAudioBean getMusic() {
        return Bean;
    }

    //播放完成的监听
    private class CompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            int index = MyApplication.getInstens().getWebPosition();
            if (index + 1 == MyApplication.getInstens().getAudioBeanList().size()){
                index = 0;
                Bean = MyApplication.getInstens().getAudioBeanList().get(0);

            } else {
                Bean = MyApplication.getInstens().getAudioBeanList().get(++index);
            }
            MyApplication.getInstens().setWebPosition(index);
            player.reset();
            String source = Bean.getUrl();
            try {
                if (!source.startsWith("http://")){
                    source="http://luoo-mp3.kssws.ks-cdn.com"+source;
                }
                player.setDataSource(source);
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.start();
            Intent i = new Intent();
            i.setAction("audioNext");
            i.putExtra("nextMusic",Bean);
            sendBroadcast(i);
        }

    }
}
