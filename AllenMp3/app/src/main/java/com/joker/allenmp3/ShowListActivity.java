package com.joker.allenmp3;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.joker.allenmp3.adapter.ListViewFragmentAdapter;
import com.joker.allenmp3.application.MyApplication;
import com.joker.allenmp3.dao.MusicDao;
import com.joker.allenmp3.entity.Music;
import com.joker.allenmp3.fragment.LocalListFragment;
import com.joker.allenmp3.fragment.WebMusicFragment;
import com.joker.allenmp3.service.mediaPlayerService;
import com.joker.allenmp3.view.RoundImageView;

import java.util.ArrayList;
import java.util.List;

public class ShowListActivity extends AppCompatActivity
        implements LocalListFragment.getMuisc{
    private TextView musicName,artist;
    private ImageView state;
    private RoundImageView riv;
    private ObjectAnimator oja;
    private MusicDao musicDao;
    private PagerSlidingTabStrip pst;
    private ViewPager vp;
    private List<Fragment> fragmentList;
    private List<String> itemName;
    private ListViewFragmentAdapter lfa;
    private MusicCompletion musicCompletion;
    private NotificationState notificationState;
    private FocusFlagState flagState;
    private static bottomList getMusicState;
    public static bottomList getPlayState;
    public static bottomList getHandleMode;
    public static bottomList getSerivicesMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        LocalListFragment.getMusicInfo(this);

        //初始化View
        initView();
        //初始化碎片
        initTabStrip();
        //初始化动画
        initOja();
        //添加记忆
        initMusic();
        //初始化广播
        initBroad();
    }

    private void initView() {
        musicName = (TextView) findViewById(R.id.showlist_name);
        artist = (TextView) findViewById(R.id.showlist_artist);
        state = (ImageView) findViewById(R.id.showlist_state);
        riv = (RoundImageView) findViewById(R.id.showlist_round);
        pst = (PagerSlidingTabStrip) findViewById(R.id.pst);
        vp = (ViewPager) findViewById(R.id.vp);
    }

    private void initTabStrip() {
        //添加碎片
        itemName = new ArrayList<>();
        fragmentList = new ArrayList<>();
        lfa =new ListViewFragmentAdapter(getSupportFragmentManager(), itemName,fragmentList);
        vp.setAdapter(lfa);
        pst.setViewPager(vp);
        pst.setIndicatorHeight(0);
        pst.setUnderlineHeight(0);
        pst.setShouldExpand(true);
        initFragment();
        pst.notifyDataSetChanged();
        lfa.notifyDataSetChanged();

    }

    //注册广播
    private void initBroad() {
        //注册歌曲切换广播
        musicCompletion = new MusicCompletion();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.mp3.MUSICNEXT");
        registerReceiver(musicCompletion,filter);

        //状态的广播
        notificationState = new NotificationState();
        filter = new IntentFilter();
        filter.addAction("NotificationMode");
        registerReceiver(notificationState,filter);

        flagState = new FocusFlagState();
        filter = new IntentFilter();
        filter.addAction("FocusFlag");
        registerReceiver(flagState,filter);
    }

    //添加上次播放的信息
    private void initMusic() {
            musicDao = new MusicDao(this);
        if (musicDao.searchAll().size()==0){
            Music music = MyApplication.getInstens().getMusics().get(0);
            musicName.setText(music.getDisplayName());
            artist.setText(music.getArtist());
        } else {
            Music music = musicDao.searchAll().get(0);
            musicName.setText(music.getDisplayName());
            artist.setText(music.getArtist());
        }
    }

    //使进度一样
    @Override
    protected void onStart() {
        super.onStart();
        if (getPlayState == null){
            state.setImageResource(R.mipmap.play_btn_play);
            oja.pause();
        } else if (getPlayState.isPlay()){//回调第4步：调用方法
            state.setImageResource(R.mipmap.play_btn_pause);
            oja.start();
        } else {
            state.setImageResource(R.mipmap.play_btn_play);
            oja.pause();
        }
        if (getSerivicesMusic==null){
            return;
        }
        Music m = getSerivicesMusic.getMusic();
        musicName.setText(m.getDisplayName());
        artist.setText(m.getArtist());
    }
    //设置RoundImage动画
    private void initOja(){
        oja = ObjectAnimator.ofFloat(riv,"rotation",0,360);
        oja.setDuration(10000);//持续时间
        oja.setRepeatCount(Integer.MAX_VALUE);//重复转的圈数（0转一圈）
        //设置 插补器?
        oja.setInterpolator(new LinearInterpolator());
    }

    public void clicked(View view){
    switch (view.getId()){
        case R.id.showlist_ll:
            Intent intent = new Intent(getBaseContext(), ShowMusicActivity.class);
            startActivity(intent);
            break;
        case R.id.showlist_state:
            if (getMusicState == null){ //这个条件加载缓存
                if (musicDao.searchAll().size()==0){
                    Music music = MyApplication.getInstens().getMusics().get(0);
                    Intent intent1 = new Intent(this, mediaPlayerService.class);
                    intent1.putExtra("music", music);
                    startService(intent1);
                    state.setImageResource(R.mipmap.play_btn_pause);
                    oja.start();
                } else {
                    Music music = musicDao.searchAll().get(0);
                    MyApplication.getInstens().setPosition(Integer.parseInt(music.getMusicId()));
                    Intent intent1 = new Intent(this, mediaPlayerService.class);
                    intent1.putExtra("music", music);
                    startService(intent1);
                    state.setImageResource(R.mipmap.play_btn_pause);
                    oja.start();
                }
            }else if (getMusicState.handleMusic()){
                state.setImageResource(R.mipmap.play_btn_pause);
                oja.resume();
            } else {
                state.setImageResource(R.mipmap.play_btn_play);
                oja.pause();
            }
            break;
        case R.id.showlist_next:
            handleNext();
            if (getHandleMode==null){
                if (musicDao.searchAll().size()==0){
                    Intent i = new Intent(this, mediaPlayerService.class);
                    i.putExtra("music", MyApplication.getInstens().getMusics().get(1));
                    MyApplication.getInstens().setPosition(1);
                    startService(i);
                } else {
                    Music music = musicDao.searchAll().get(0);
                    int index = Integer.parseInt(music.getMusicId())+1;
                    Intent i = new Intent(this, mediaPlayerService.class);
                    if (index== MyApplication.getInstens().getMusics().size()){
                        index = 0;
                    }
                    MyApplication.getInstens().setPosition(index);
                    i.putExtra("music", MyApplication.getInstens().getMusics().get(index));
                    startService(i);
                    musicName.setText(MyApplication.getInstens().getMusics().get(index).getDisplayName());
                    artist.setText(MyApplication.getInstens().getMusics().get(index).getArtist());
                }
            } else {
                getHandleMode.handleMode();
            }
            break;
    }
  }
    //下一首
    private void handleNext() {
        int i = MyApplication.getInstens().getPosition()+1;
        if (MyApplication.getInstens().getFlag().equals("MODE_SHUFFLE")){
            if (i == MyApplication.getInstens().getMessMusics().size()) {
                i = 0;
            }
            musicName.setText(MyApplication.getInstens()
                    .getMessMusics().get(i).getDisplayName());
            artist.setText(MyApplication.getInstens()
                    .getMessMusics().get(i).getArtist());
        } else  {
            if (i == MyApplication.getInstens().getMusics().size()) {
                i = 0;
            }
            musicName.setText(MyApplication.getInstens()
                    .getMusics().get(i).getDisplayName());
            artist.setText(MyApplication.getInstens()
                    .getMusics().get(i).getArtist());
        }
        MyApplication.getInstens().setPosition(i);
        oja.start();
        state.setImageResource(R.mipmap.play_btn_pause);
    }

    //回调获得fragment的音乐信息
    @Override
    public void getMusicInfo(Music music) {
        musicName.setText(music.getDisplayName());
        artist.setText(music.getArtist());
        state.setImageResource(R.mipmap.play_btn_pause);
        oja.start();
    }

    //播放完成 切换的广播
    public class MusicCompletion extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Music music = intent.getParcelableExtra("music");
            musicName.setText(music.getDisplayName());
            artist.setText(music.getArtist());
        }
    }

    public class FocusFlagState extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            intent.getIntExtra("flag",0);
            state.setImageResource(R.mipmap.play_btn_pause);
            oja.start();
        }
    }

    // 通知栏状态的广播接收Service广播
    public class  NotificationState extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int state1 = intent.getIntExtra("state",0);
            if (state1 ==3){
                state.setImageResource(R.mipmap.play_btn_pause);
                oja.resume();
            } else if (state1 == 4 ){
                state.setImageResource(R.mipmap.play_btn_play);
                oja.pause();
            } else if (state1 == 5){
                finish();
            }
        }
    }
    //添加记忆
    @Override
    protected void onStop() {
        super.onStop();
        //将当前的音乐放到数据库
        Music music;
        musicDao.deleteAll();
        if (MyApplication.getInstens().getFlag().equals("MODE_SHUFFLE")){
            music = MyApplication.getInstens().getMessMusics()
                    .get(MyApplication.getInstens().getPosition());
            music.setMusicId(MyApplication.getInstens().getPosition()+"");
            musicDao.addMusic(music);
        } else {
            music = MyApplication.getInstens().getMusics()
                    .get(MyApplication.getInstens().getPosition());
            music.setMusicId(MyApplication.getInstens().getPosition()+"");
            musicDao.addMusic(music);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MyApplication.getInstens().setPlaying(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.getInstens().setPlaying(false);
    }

    //解除广播
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(musicCompletion);
        unregisterReceiver(notificationState);
        unregisterReceiver(flagState);
    }

    //添加碎片
    public void initFragment(){
        itemName.add("本地音乐");
        itemName.add("发现音乐");
        Fragment localList = new LocalListFragment();
        fragmentList.add(localList);
        localList = new WebMusicFragment();
        fragmentList.add(localList);

    }

    public static void getMusicInstence(Context context){
        getMusicState = (bottomList) context;
    }
    public static void  getBottomState(Context context){getPlayState =(bottomList) context;}
    public static void  getBottomMode(Context context){
        getHandleMode = (bottomList) context;
    }
    public static void  getServiceMusic(Context context){getSerivicesMusic =(bottomList) context;}
    public interface bottomList{
        public boolean handleMusic();
        public boolean isPlay();
        public Music getMusic();
        public void handleMode();
    }

    //两次退出，监听物理回退键。
    long firstTime =0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime>2000){
                Toast.makeText(getBaseContext(),"再按一次退出程序"
                        ,Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyUp(keyCode, event);
    }
}
