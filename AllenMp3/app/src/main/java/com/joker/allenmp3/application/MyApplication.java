package com.joker.allenmp3.application;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.joker.allenmp3.R;
import com.joker.allenmp3.entity.DiscoverDailyData;
import com.joker.allenmp3.entity.Music;
import com.joker.allenmp3.util.LocalMusic;
import com.joker.allenmp3.util.PlayerState;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/9/11.
 */
public class MyApplication extends Application {
    private static MyApplication myApplication;
    private RequestQueue queue;
    private List<Music> musics,messMusics;//有序，无序列表
    private int position,webPosition;//传入的歌曲位置
    private String flag ; //模式
    private boolean isPop = true;//是否从popList传入
    private boolean isPlaying = true;
    private List<DiscoverDailyData.DataBean.ItemsBean.PostAudioBean> audioBeanList;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        flag = PlayerState.STATEA;
        musics = new LocalMusic(this).readMusic();
        messMusics = new LocalMusic(this).readMusic();
        queue= Volley.newRequestQueue(this);
        initImageLoader(getApplicationContext());
        Collections.shuffle(messMusics);//随机列表
    }
    public static MyApplication getInstens(){
        return myApplication;
    }

    public  void  setMusics(List<Music> musics){
        this.musics = musics;
    }
    public List<Music> getMusics() {
        return musics;
    }

    public List<Music> getMessMusics() {
        return messMusics;
    }

    public void setMessMusics(List<Music> messMusics) {
        this.messMusics = messMusics;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<DiscoverDailyData.DataBean.ItemsBean.PostAudioBean> getAudioBeanList() {
        return audioBeanList;
    }

    public void setAudioBeanList(List<DiscoverDailyData.DataBean.ItemsBean.PostAudioBean> audioBeanList) {
        this.audioBeanList = audioBeanList;
    }

    public boolean isPop() {
        return isPop;
    }

    public void setPop(boolean pop) {
        isPop = pop;
    }
    public RequestQueue getQueue(){
        return queue;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public int getWebPosition() {
        return webPosition;
    }

    public void setWebPosition(int webPosition) {
        this.webPosition = webPosition;
    }

    public static DisplayImageOptions getOptions(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_bg)
//                 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.default_bg)
//                 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.default_bg)
//                 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)
                // 设置下载的图片是否缓存在SD卡中
                .displayer(new FadeInBitmapDisplayer(300))// 图片加载好后渐入的动画时间
                .build();
        return options;
    }
    public static DisplayImageOptions getPlayingOptions(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.placeholder_disk_play_fm)
//                 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.placeholder_disk_play_fm)
//                 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.placeholder_disk_play_fm)
//                 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)
                // 设置下载的图片是否缓存在SD卡中
                .displayer(new FadeInBitmapDisplayer(0))// 图片加载好后渐入的动画时间
                .build();
        return options;
    }
    public void initImageLoader(Context context){
        ImageLoaderConfiguration.Builder config=new ImageLoaderConfiguration.Builder(context);
        DisplayImageOptions options=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
        config.defaultDisplayImageOptions(options);
        config.diskCacheSize(100 * 1024 * 1024);
        config.memoryCacheSize(20 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        ImageLoader.getInstance().init(config.build());
    }
}
