package com.joker.allenmp3.inneractivity;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.joker.allenmp3.R;
import com.joker.allenmp3.application.MyApplication;
import com.joker.allenmp3.entity.DiscoverDailyData;
import com.joker.allenmp3.service.WebMusicService;
import com.joker.allenmp3.util.FastBlurUtil;
import com.joker.allenmp3.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.text.SimpleDateFormat;

public class WebMusicActvity extends AppCompatActivity {
    private ObjectAnimator oja;
    private ImageView flag,mode,back;
    private TextView musicName,currentTime,totalTime,artist,roll;
    private RoundImageView riv;
    private ImageView bgImg,popList;
    private SimpleDateFormat sdf;
    private SeekBar seekBar;
    private SeekBarUpdate seekBarUpdate;
    private AudioNext audioNext;
    private static webCallback musicState;
    private static webCallback musicPlaying;
    private static webCallback playTime;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_play);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        flag = (ImageView) findViewById(R.id.music_flag);
        mode = (ImageView) findViewById(R.id.music_mode);
        popList = (ImageView) findViewById(R.id.music_popWindow);
        back = (ImageView) findViewById(R.id.music_back);
        musicName = (TextView) findViewById(R.id.music_name);
        currentTime = (TextView) findViewById(R.id.showtime);
        totalTime = (TextView) findViewById(R.id.maxtime);
        roll = (TextView) findViewById(R.id.roll);
        artist = (TextView) findViewById(R.id.artist);
        riv = (RoundImageView) findViewById(R.id.riv);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        bgImg = (ImageView) findViewById(R.id.bgImg);

        sdf = new SimpleDateFormat("mm:ss");  //时间格式标准化

        if (getIntent().getIntExtra("info",-1) == -1){
            position = MyApplication.getInstens().getWebPosition();
        } else {
            position = getIntent().getIntExtra("info", -1);
        }
        initbroadCast();
        initOja();
        initMusicInfo(MyApplication.getInstens().getAudioBeanList().get(position));//配置歌曲信息
        initHandle();
        initListener();
    }

    private void initbroadCast(){
        //注册进度条广播
        seekBarUpdate = new SeekBarUpdate();
        IntentFilter filter = new IntentFilter();
        filter.addAction("SEEKBARUPDATE");
        registerReceiver(seekBarUpdate,filter);

        //注册下一首广播
        audioNext = new AudioNext();
        filter = new IntentFilter();
        filter.addAction("audioNext");
        registerReceiver(audioNext,filter);
    }
    //对按钮操作
    private void initHandle() {
        mode.setVisibility(View.GONE);
        popList.setVisibility(View.GONE);
        roll.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //添加动画和背景配置文件信息!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ErrorErrorErrorErrorErrorErrorErrorErrorError
    private void initMusicInfo(DiscoverDailyData.DataBean.ItemsBean.PostAudioBean resultbean){

        musicName.setText(resultbean.getSong_name());
        artist.setText(resultbean.getArtist());
        ImageLoader.getInstance().displayImage(resultbean.getCover(),riv ,
                new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap  loadedImage) {
                        loadedImage = Bitmap.createScaledBitmap(loadedImage,
                                loadedImage.getWidth() / 10,
                                loadedImage.getHeight() / 10,
                                false);
                        FastBlurUtil.doBlur(loadedImage, 8, true);
                        bgImg.setImageBitmap(loadedImage);
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {

                    }
                });
        if (!musicPlaying.isWebPlaying()){
            oja.pause();
            flag.setImageResource(R.mipmap.play_btn_play);
        }
        currentTime.setText(sdf.format(playTime.musicTime()[0]));//标准格式
        totalTime.setText(sdf.format(playTime.musicTime()[1]));
        seekBar.setProgress(playTime.musicTime()[0]);
        seekBar.setMax(playTime.musicTime()[1]);
    }

    private void initOja(){
        oja = ObjectAnimator.ofFloat(riv,"rotation",0,360);
        oja.setDuration(10000);//持续时间
        oja.setRepeatCount(Integer.MAX_VALUE);//重复转的圈数（0转一圈）
        //设置 插补器?
        oja.setInterpolator(new LinearInterpolator());
        oja.start();
    }

    private void initListener(){
     seekBar.setOnSeekBarChangeListener(new SeekBarChangeListener());
    }

    public void click(View view){
        switch (view.getId()){
            case R.id.music_flag:
                if (musicState.state()){
                    flag.setImageResource(R.mipmap.play_btn_pause);
                    oja.resume();
                } else {
                    flag.setImageResource(R.mipmap.play_btn_play);
                    oja.pause();
                }
                break;
            case R.id.music_last:
                handleLast();
                break;
            case R.id.music_next:
                handleNext();
                break;
        }
    }

    private void handleNext() {
        Intent i = new Intent(this,WebMusicService.class);
        if (position + 1 == MyApplication.getInstens().getAudioBeanList().size()){
            position = 0;
            i.putExtra("info",MyApplication.getInstens().getAudioBeanList().get(0));
            initMusicInfo(MyApplication.getInstens().getAudioBeanList().get(0));
        } else {
            i.putExtra("info", MyApplication.getInstens().getAudioBeanList().get(++position));
            initMusicInfo(MyApplication.getInstens().getAudioBeanList().get(position));
        }
        MyApplication.getInstens().setWebPosition(position);
        startService(i);
    }

    private void handleLast() {
        Intent i = new Intent(this,WebMusicService.class);
        if (position == 0){
            position = MyApplication.getInstens().getAudioBeanList().size()-1;
            i.putExtra("info",MyApplication.getInstens().getAudioBeanList().get(position));
            initMusicInfo(MyApplication.getInstens().getAudioBeanList().get(position));
        } else {
            i.putExtra("info", MyApplication.getInstens().getAudioBeanList().get(--position));
            initMusicInfo(MyApplication.getInstens().getAudioBeanList().get(position));
        }
        MyApplication.getInstens().setWebPosition(position);
        startService(i);
    }

    //seekBar进度改变的监听
    public class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener{
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser){
                Intent intent = new Intent(getBaseContext(),WebMusicService.class);
                intent.putExtra("UPDATE",progress);
                intent.putExtra("seekto","PROGRESS");
                startService(intent);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    // 进度条更新的广播接收
    public class SeekBarUpdate extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int progress = intent.getIntExtra("progress",0);
            int max = intent.getIntExtra("max",0);
            currentTime.setText(sdf.format(progress));//标准格式
            totalTime.setText(sdf.format(max));
            seekBar.setProgress(progress);
            seekBar.setMax(max);
        }
    }

    //自动下一首广播接收
    public class AudioNext extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            initMusicInfo((DiscoverDailyData.DataBean.ItemsBean.PostAudioBean)
                    intent.getParcelableExtra("nextMusic"));
        }
    }

    public static void MusicState(Context context){
        musicState = (webCallback) context;
    }
    public static void MusicPlay(Context context){
        musicPlaying = (webCallback) context;
    }
    public static void MusicTime(Context context){
        playTime = (webCallback) context;
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getInstens().setWebPosition(position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(seekBarUpdate);
        unregisterReceiver(audioNext);
    }

    public interface webCallback{
        public boolean state();
        public boolean isWebPlaying();
        public int[] musicTime();
    }
}

