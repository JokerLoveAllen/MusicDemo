package com.joker.allenmp3.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.joker.allenmp3.R;
import com.joker.allenmp3.ShowListActivity;
import com.joker.allenmp3.ShowMusicActivity;
import com.joker.allenmp3.application.MyApplication;
import com.joker.allenmp3.entity.Music;

import java.io.IOException;

public class mediaPlayerService extends Service
      implements ShowMusicActivity.callBack,ShowListActivity.bottomList { //回调第2步：实现接口
    private MediaPlayer player;
    private Music music;
    private AudioManager audioManager;
    private NotificationBoard notificationBoard;
    private WebMusic webMusic;
    public Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Message m = new Message();
            if (player.isPlaying()){
                Intent in = new Intent();
                in.setAction("android.mp3.SEEKBARUPDATE");
                in.putExtra("progress",player.getCurrentPosition());
                in.putExtra("max",player.getDuration());
                sendBroadcast(in);
            }
            handler.sendMessageDelayed(m,1000);
        }
    };
    public mediaPlayerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        initBroadAndCallBack();
    }

    private void initBroadAndCallBack() {
        notificationBoard = new NotificationBoard();
        IntentFilter filter = new IntentFilter();
        filter.addAction("NotificationMode");
        registerReceiver(notificationBoard,filter);

        //注册WebMusic停止播放
        webMusic = new WebMusic();
        filter = new IntentFilter();
        filter.addAction("WebMusic");
        registerReceiver(webMusic,filter);

        //回调第3步：关联起来。
        ShowMusicActivity.getService(this);
        ShowMusicActivity.getMode(this);
        ShowMusicActivity.getServiceMusic(this);
        ShowMusicActivity.getState(this);

        ShowListActivity.getServiceMusic(this);
        ShowListActivity.getBottomState(this);
        ShowListActivity.getMusicInstence(this);
        ShowListActivity.getBottomMode(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.sendEmptyMessage(1);
        if (intent.getStringExtra("seekto")!=null){
            player.seekTo(intent.getIntExtra("seekbar",0));
        } else {
            playMusic(intent);
        }
        createNotification();
        player.setOnCompletionListener(new completionListener());
        checkFocus();
        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.stop();
        }
        unregisterReceiver(webMusic);
        unregisterReceiver(notificationBoard);
    }

    private void playMusic(Intent intent) {
            player.reset();
            music = intent.getParcelableExtra("music");
        try {
            player.setDataSource(this, Uri.parse(music.getData()));
            player.prepare(); //准备播放
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start();
    }

    //回调第2步：实现接口方法  ShowMusic和 ShowList公共方法
    @Override
    public boolean handleMusic() {
        if (player.isPlaying()) {
            player.pause();
        } else {
            player.start();
        }
        createNotification();//创建新的通知栏
        return player.isPlaying();
    }
    //随机播放：
    @Override
    public void handleMode() {
        String mode = MyApplication.getInstens().getFlag();
        Music music;
        if (mode.equals("MODE_SHUFFLE")) {
            music = MyApplication.getInstens().getMessMusics()
                    .get(MyApplication.getInstens().getPosition());
        } else {
            music = MyApplication.getInstens().getMusics()
                    .get(MyApplication.getInstens().getPosition());
        }
        try {
            player.reset();
            player.setDataSource(this, Uri.parse(music.getData()));
            player.prepare(); //准备播放
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start();
        createNotification();
    }
    //返回music给播放详情
    @Override
    public Music getMusic() {
        if (MyApplication.getInstens().getFlag().equals("MODE_SHUFFLE")
                &&MyApplication.getInstens().isPop()) {
            music = MyApplication.getInstens().getMessMusics()
                    .get(MyApplication.getInstens().getPosition());
        } else {
            music = MyApplication.getInstens().getMusics()
                    .get(MyApplication.getInstens().getPosition());
        }
        return music;
    }
    //   ShowMusic和 ShowList公共方法
    @Override
    public boolean isPlay() {

        if (player.isPlaying()){
            return true;
        } else {
            return false;
        }
    }

    //播放下一首
    public class completionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            if (MyApplication.getInstens().getFlag().equals("MODE_ONE")){
                try {
                    player.reset();
                    player.setDataSource(mediaPlayerService.this,
                            Uri.parse(MyApplication.getInstens()
                                    .getMusics().get(MyApplication.
                                            getInstens().getPosition()).getData()));
                    player.prepare(); //准备播放
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.start();
                return;
            }

            Music music;
            int i = MyApplication.getInstens().getPosition() + 1;
            if (MyApplication.getInstens().getFlag().equals("MODE_SHUFFLE")){
                if (i == MyApplication.getInstens().getMessMusics().size()) {
                    i = 0;
                }
                music = MyApplication.getInstens().getMessMusics().get(i);
            } else {
                if (i == MyApplication.getInstens().getMusics().size()) {
                    i = 0;
                }
                music = MyApplication.getInstens().getMusics().get(i);
            }
            MyApplication.getInstens().setPosition(i);
            try {
                player.reset();
                player.setDataSource(mediaPlayerService.this, Uri.parse(music.getData()));
                player.prepare(); //准备播放
                player.start();
                Intent intent = new Intent();
                intent.setAction("android.mp3.MUSICNEXT");
                intent.putExtra("music",music);
                sendBroadcast(intent);
                createNotification();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //通知栏的广播
    public class NotificationBoard extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int arg = intent.getIntExtra("arg",0);
            if (arg ==1){
                player.start();
            } else if (arg ==2){
                player.pause();
            } else if (arg ==3){
//                player.pause();
                stopSelf();
                return;
            }
            createNotification();
        }
    }

    //接收WebMusic停止播放
    public class WebMusic extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra("flag",0) ==1){
                player.pause();
                createNotification();
            }
        }
    }
    //创建通知栏
    public void createNotification(){

        NotificationCompat.Builder builder =
               new NotificationCompat.Builder(this);
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
               R.layout.show_window);
        if(player.isPlaying()){
            remoteViews.setViewVisibility(R.id.window_play, View.GONE);
            remoteViews.setViewVisibility(R.id.window_pause, View.VISIBLE);
        } else {
            remoteViews.setViewVisibility(R.id.window_play, View.VISIBLE);
            remoteViews.setViewVisibility(R.id.window_pause, View.GONE);
        }
        if (MyApplication.getInstens().getFlag().equals("MODE_SHUFFLE")
                &&MyApplication.getInstens().isPop()) {
            music = MyApplication.getInstens().getMessMusics()
                    .get(MyApplication.getInstens().getPosition());
        } else {
            music = MyApplication.getInstens().getMusics()
                    .get(MyApplication.getInstens().getPosition());
        }
        remoteViews.setTextViewText(R.id.window_name,music.getDisplayName());
        remoteViews.setTextViewText(R.id.window_artist,music.getArtist());
        //播放按钮执行的广播
        Intent playIntent = new Intent();
        playIntent.setAction("NotificationMode");
        playIntent.putExtra("arg",1);//向Service发送广播
        playIntent.putExtra("state",3);//向播放列表发送广播
        PendingIntent playpending = PendingIntent.getBroadcast(this,0,playIntent
                ,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.window_play,playpending);

        //暂停按钮执行的广播
        Intent pauseIntent = new Intent();
        pauseIntent.setAction("NotificationMode");
        pauseIntent.putExtra("arg",2);//向Service发送广播
        pauseIntent.putExtra("state",4);//向播放列表发送广播
        PendingIntent pausepending = PendingIntent.getBroadcast(this,1,pauseIntent
                ,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.window_pause,pausepending);

        //关闭按钮执行的广播
        Intent stopIntent = new Intent();
        stopIntent.setAction("NotificationMode");
        stopIntent.putExtra("arg",3);//向Service发送广播
        stopIntent.putExtra("state",5);//向播放列表发送广播
        PendingIntent stoppending = PendingIntent.getBroadcast(this,3,stopIntent
                ,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.window_stop,stoppending);

        //点击通知栏 跳转到播放器画面
        Intent intent = new Intent(this,ShowMusicActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,2,intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContent(remoteViews);
        builder.setSmallIcon(R.mipmap.play_bk);
        builder.setContentText("测试");
        builder.setContentTitle("标题");

        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        notification.bigContentView =remoteViews;
        startForeground(1,notification);
   }
    //检查音频焦点
    public void checkFocus(){
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        audioManager.requestAudioFocus(new AudioManager.OnAudioFocusChangeListener(){
            @Override
            public void onAudioFocusChange(int i) {
                switch (i){
                    //获得音频焦点
                    case AudioManager.AUDIOFOCUS_GAIN:
                        if (MyApplication.getInstens().isPlaying()){
                            Intent flagIntent = new Intent();
                            flagIntent.setAction("FocusFlag");
                            flagIntent.putExtra("flag",1);//向播放列表发送广播
                            sendBroadcast(flagIntent);
                            player.start();
                        }
                        break;
                    //长久失去音频焦点
                    case  AudioManager.AUDIOFOCUS_LOSS:
                        if (MyApplication.getInstens().isPlaying()){
                            return;
                        }
                        if (player.isPlaying()){ //为了使进度条不暂停
                            player.pause();
                        }
                        createNotification();
                        break;
                    //暂时失去音频焦点
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        if(player.isPlaying()){
                            player.pause();
                        }
                        //短时间失去音频焦点，无需停止
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        break;
                }
                MyApplication.getInstens().setPlaying(false);
            }
        },AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
    }
}
/*
 *           player.getDuration(); //歌曲的总长度。
 *           player.getCurrentPosition();//播放的长度。
 */