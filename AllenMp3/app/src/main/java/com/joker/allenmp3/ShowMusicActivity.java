package com.joker.allenmp3;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.joker.allenmp3.adapter.popList;
import com.joker.allenmp3.application.MyApplication;
import com.joker.allenmp3.dao.MusicDao;
import com.joker.allenmp3.entity.Music;
import com.joker.allenmp3.service.mediaPlayerService;
import com.joker.allenmp3.util.FastBlurUtil;
import com.joker.allenmp3.util.PlayerState;
import com.joker.allenmp3.util.settingRoll;
import com.joker.allenmp3.view.RoundImageView;

import java.text.SimpleDateFormat;

public class ShowMusicActivity extends AppCompatActivity {
    private ImageView flag,mode;
    private TextView musicName,currentTime,totalTime,artist;
    private String state;
    private SimpleDateFormat sdf;
    private ObjectAnimator oja;
    private RoundImageView riv;
    private ImageView bgImg;
    private MusicDao musicDao;
    private SeekBar seekBar;
    private SeekBarUpdate seekBarUpdate;
    private MusicCompletion musicCompletion;
    private NotificationState notificationState;
    public static callBack handleMusic;//回调第3步：静态变量
    public static callBack handleMode;//回调第3步：静态变量
    public static callBack getSeriviceMusic;//回调第3步：静态变量
    public static callBack getPlayState;//回调第3步：静态变量
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_play);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        initView();
        initbroadCastAndMusic();
        initOja();
    }

    private void initView() {
        flag = (ImageView) findViewById(R.id.music_flag);
        mode = (ImageView) findViewById(R.id.music_mode);
        musicName = (TextView) findViewById(R.id.music_name);
        currentTime = (TextView) findViewById(R.id.showtime);
        totalTime = (TextView) findViewById(R.id.maxtime);
        artist = (TextView) findViewById(R.id.artist);
        riv = (RoundImageView) findViewById(R.id.riv);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        bgImg = (ImageView) findViewById(R.id.bgImg);

        musicDao = new MusicDao(this);
        sdf = new SimpleDateFormat("mm:ss");
    }

    private void initbroadCastAndMusic() {
        //注册进度条广播
        seekBarUpdate = new SeekBarUpdate();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.mp3.SEEKBARUPDATE");
        registerReceiver(seekBarUpdate,filter);

        //注册歌曲切换广播
        musicCompletion = new MusicCompletion();
        filter = new IntentFilter();
        filter.addAction("android.mp3.MUSICNEXT");
        registerReceiver(musicCompletion,filter);

        //状态的广播
        notificationState = new NotificationState();
        filter = new IntentFilter();
        filter.addAction("NotificationMode");
        registerReceiver(notificationState,filter);

        if (getSeriviceMusic==null){
            if (musicDao.searchAll().size()==0) {
                initMusicInfo(MyApplication.getInstens().getMusics().get(0));
                MyApplication.getInstens().setPosition(0);
            } else {
                Music music = musicDao.searchAll().get(0);
                initMusicInfo(music);
                MyApplication.getInstens()
                        .setPosition(Integer.parseInt(music.getMusicId()));
            }
        } else {
            initMusicInfo(getSeriviceMusic.getMusic());//回调获得播放的歌曲
        }

        seekBar.setOnSeekBarChangeListener(new SeekBarChangeListener());
    }

    @Override
    protected void onStart() {
        super.onStart();
        state =MyApplication.getInstens().getFlag();
        initMode();
    }

    //添加动画和背景配置文件信息
    private void initMusicInfo(Music music){
        Bitmap loadedImage;
        musicName.setText(music.getDisplayName());
        totalTime.setText(music.getDuration());
        artist.setText(music.getArtist());

        //图片模糊
        if (music.getImageUrl()==null){
            loadedImage = BitmapFactory.decodeResource(getResources(),R.mipmap.play_bk);
            riv.setImageResource(R.mipmap.play_bk);
        } else if (!music.getImageUrl().equals("")){
            loadedImage = BitmapFactory.decodeFile(music.getImageUrl());
            riv.setImageURI(Uri.parse(music.getImageUrl()));
        } else {
            loadedImage = BitmapFactory.decodeResource(getResources(),R.mipmap.play_bk);
            riv.setImageResource(R.mipmap.play_bk);
        }
        loadedImage = Bitmap.createScaledBitmap(loadedImage,
                loadedImage.getWidth() / 10,
                loadedImage.getHeight() / 10,
                false);
        FastBlurUtil.doBlur(loadedImage, 8, true);
        bgImg.setImageBitmap(loadedImage);

    }
    //设置RoundImage动画
    private void initOja(){
        oja = ObjectAnimator.ofFloat(riv,"rotation",0,360);
        oja.setDuration(10000);//持续时间
        oja.setRepeatCount(Integer.MAX_VALUE);//重复转的圈数（0转一圈）
        //设置 插补器?
        oja.setInterpolator(new LinearInterpolator());
        oja.start();
    }
    //开始的模式图标
    private void initMode() {
        if (state.equals("MODE_LOOP")){
            mode.setImageResource(R.mipmap.desk_loop);
        } else if (state.equals("MODE_ONE")){
            mode.setImageResource(R.mipmap.desk_one);
        } else {
            mode.setImageResource(R.mipmap.desk_shuffle);
        }
        if (getPlayState == null){
            flag.setImageResource(R.mipmap.play_btn_play);
            oja.pause();
            return;
        }
        if (getPlayState.isPlay()){//回调第4步：调用方法
            flag.setImageResource(R.mipmap.play_btn_pause);
            oja.resume();
        } else {
            flag.setImageResource(R.mipmap.play_btn_play);
            oja.pause();
        }
    }
    //对播放器的操作
    public void click(View v){
        switch (v.getId()){
            case R.id.music_mode:
                musicMode();
                break;
            case R.id.music_flag://暂停/开始
                if (handleMusic == null){
                    if (musicDao.searchAll().size()==0) {
                        Music music = MyApplication.getInstens().getMusics().get(0);
                        Intent intent1 = new Intent(this, mediaPlayerService.class);
                        intent1.putExtra("music", music);
                        startService(intent1);
                        flag.setImageResource(R.mipmap.play_btn_pause);
                    } else {
                        Music music = musicDao.searchAll().get(0);
                        Intent intent1 = new Intent(this, mediaPlayerService.class);
                        intent1.putExtra("music", music);
                        startService(intent1);
                        flag.setImageResource(R.mipmap.play_btn_pause);
                    }
                } else if (handleMusic.handleMusic()){//回调第4步：调用方法
                    flag.setImageResource(R.mipmap.play_btn_pause);
                    oja.resume();
                } else {
                    flag.setImageResource(R.mipmap.play_btn_play);
                    oja.pause();
                }
                break;
            case R.id.music_last://上一首
                MyApplication.getInstens().setPop(true);
                handleLast();
                break;
            case R.id.music_next://下一首
                MyApplication.getInstens().setPop(true);
                handleNext();
                break;
            case R.id.music_back:
                finish();
                break;
            case R.id.music_popWindow://显示音乐列表
                showPopList();
                break;
            case R.id.roll:
                initListener();
                break;
            default:
                break;
        }
    }

    //seekBar进度改变的监听
    public class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener{
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
             if (fromUser){
                 Intent intent = new Intent(getBaseContext(),mediaPlayerService.class);
                 intent.putExtra("seekbar",progress);
                 intent.putExtra("seekto","PROGRESS_CHANGE");
                 MyApplication.getInstens().setPlaying(true);
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

    //显示音乐列表
    private void showPopList() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_list, null);
        ListView lv = (ListView) view.findViewById(R.id.lv);
        TextView tv = (TextView) view.findViewById(R.id.popheader);
        lv.setAdapter(new popList(this, MyApplication.getInstens().getMusics()));
        tv.setText( "播放列表 (共"+MyApplication.getInstens().getMusics().size()+"首)");
        final PopupWindow popList = new PopupWindow(view);
        popList.setFocusable(true);//popWindow获得焦点
        ColorDrawable cd = new ColorDrawable();
        popList.setBackgroundDrawable(cd); //不写退出不了
        popList.setAnimationStyle(R.style.PopWin);
        popList.setWidth(getResources().getDisplayMetrics().widthPixels);
        popList.setHeight(getResources().getDisplayMetrics().heightPixels / 2);
        popList.showAtLocation(flag, Gravity.BOTTOM,0, 0);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyApplication.getInstens().setPop(false);
                MyApplication.getInstens().setPlaying(true);
                Music music = MyApplication.getInstens().getMusics().get(i);
                Intent intent =new Intent(ShowMusicActivity.this,mediaPlayerService.class);
                intent.putExtra("music",music);
                startService(intent);
                oja.start();
                MyApplication.getInstens().setPosition(i);//是从popList传入的
                musicName.setText(music.getDisplayName());
                totalTime.setText(music.getDuration());
                artist.setText(music.getArtist());
                flag.setImageResource(R.mipmap.play_btn_pause);
                popList.dismiss();
            }
        });
    }
    //播放模式
    private void musicMode() {
        String statu =MyApplication.getInstens().getFlag();
        if (statu.equals("MODE_LOOP")){
            MyApplication.getInstens().setFlag(PlayerState.STATEB);
            mode.setImageResource(R.mipmap.desk_one);
        } else if (statu.equals("MODE_ONE")){
            MyApplication.getInstens().setFlag(PlayerState.STATEC);
            mode.setImageResource(R.mipmap.desk_shuffle);
        } else {
            MyApplication.getInstens().setFlag(PlayerState.STATEA);
            mode.setImageResource(R.mipmap.desk_loop);
        }
    }

    //播放下一首
    private void handleNext() {

        int i = MyApplication.getInstens().getPosition()+1;
        if (MyApplication.getInstens().getFlag().equals("MODE_SHUFFLE")){
            if (i == MyApplication.getInstens().getMessMusics().size()) {
                i = 0;
            }

            musicName.setText(MyApplication.getInstens()
                    .getMessMusics().get(i).getDisplayName());
            totalTime.setText(MyApplication.getInstens()
                    .getMessMusics().get(i).getDuration());
            artist.setText(MyApplication.getInstens()
                    .getMessMusics().get(i).getArtist());
            MyApplication.getInstens().setPosition(i);
            if (handleMusic == null){
                Intent intent = new Intent(this,mediaPlayerService.class);
                intent.putExtra("music",MyApplication.getInstens()
                        .getMessMusics().get(i));
                startService(intent);
            } else {
                handleMusic.handleMode();
            }
        } else  {
        if (i == MyApplication.getInstens().getMusics().size()) {
            i = 0;
         }
            musicName.setText(MyApplication.getInstens()
                    .getMusics().get(i).getDisplayName());
            totalTime.setText(MyApplication.getInstens()
                    .getMusics().get(i).getDuration());
            artist.setText(MyApplication.getInstens()
                    .getMusics().get(i).getArtist());
            MyApplication.getInstens().setPosition(i);
            if (handleMusic == null){
                Intent intent = new Intent(this,mediaPlayerService.class);
                intent.putExtra("music",MyApplication.getInstens()
                        .getMusics().get(i));
                startService(intent);
            } else {
                handleMusic.handleMode();
            }
        }
        oja.start();
        flag.setImageResource(R.mipmap.play_btn_pause);
    }

    //播放上一首
    private void handleLast() {
        int i = MyApplication.getInstens().getPosition()-1;
        if (MyApplication.getInstens().getFlag().equals("MODE_SHUFFLE")) {
            if (i == -1) {
                i = MyApplication.getInstens().getMusics().size() - 1;
            }
            musicName.setText(MyApplication.getInstens()
                    .getMessMusics().get(i).getDisplayName());
            totalTime.setText(MyApplication.getInstens()
                    .getMessMusics().get(i).getDuration());
            artist.setText(MyApplication.getInstens()
                    .getMessMusics().get(i).getArtist());
            MyApplication.getInstens().setPosition(i);
            if (handleMusic == null){
                Intent intent = new Intent(this,mediaPlayerService.class);
                intent.putExtra("music",MyApplication.getInstens()
                        .getMessMusics().get(i));
                startService(intent);
            } else {
                handleMusic.handleMode();
            }
        } else {
            if (i == -1) {
                i = MyApplication.getInstens().getMusics().size() - 1;
            }
            musicName.setText(MyApplication.getInstens()
                    .getMusics().get(i).getDisplayName());
            totalTime.setText(MyApplication.getInstens()
                    .getMusics().get(i).getDuration());
            artist.setText(MyApplication.getInstens()
                    .getMusics().get(i).getArtist());
            MyApplication.getInstens().setPosition(i);
            if (handleMusic == null){
                Intent intent = new Intent(this,mediaPlayerService.class);
                intent.putExtra("music",MyApplication.getInstens()
                        .getMusics().get(i));
                startService(intent);
            } else {
                handleMusic.handleMode();
            }
        }
        oja.start();
        flag.setImageResource(R.mipmap.play_btn_pause);

    }

    //初始化监听
    private void initListener(){
        if (MyApplication.getInstens().getFlag().equals("MODE_SHUFFLE")){
            settingRoll.getRINGTONE(ShowMusicActivity.this,
                    MyApplication.getInstens().getMessMusics()
                        .get(MyApplication.getInstens().getPosition()));
            Toast.makeText(getBaseContext(),"试试吧A",Toast.LENGTH_SHORT).show();
        } else {
            settingRoll.getRINGTONE(ShowMusicActivity.this,
                    MyApplication.getInstens().getMusics()
                            .get(MyApplication.getInstens().getPosition()));
            Toast.makeText(getBaseContext(),"试试吧B",Toast.LENGTH_SHORT).show();

        }

    }

    //回调第3步：静态方法
    public static void getService(Context context){
       handleMusic = (callBack) context;
    }
    public static void  getMode(Context context){
        handleMode = (callBack) context;
    }
    public static void  getServiceMusic(Context context){getSeriviceMusic =(callBack) context;}
    public static void  getState(Context context){getPlayState =(callBack) context;}
    //回调第1步:创建接口
    public interface callBack{
        public boolean handleMusic();//开始、暂停
        public void handleMode();
        public Music getMusic();
        public boolean isPlay();

    }
    // 进度条更新的广播接收
    public class SeekBarUpdate extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int progress = intent.getIntExtra("progress",0);
            int max = intent.getIntExtra("max",0);
            currentTime.setText(sdf.format(progress));//标准格式
            seekBar.setProgress(progress);
            seekBar.setMax(max);
        }
    }

    //播放完成 切换的广播
    public class MusicCompletion extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
           Music music = intent.getParcelableExtra("music");
           musicName.setText(music.getDisplayName());
           totalTime.setText(music.getDuration());
           artist.setText(music.getArtist());
        }
    }

    // 通知栏状态的广播接收Service广播
    public class  NotificationState extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int state = intent.getIntExtra("state",0);
            if (state ==3){
                flag.setImageResource(R.mipmap.play_btn_pause);
                oja.resume();
            } else if (state == 4 ){
                flag.setImageResource(R.mipmap.play_btn_play);
                oja.pause();
            } else if (state == 5){
                finish();
            }
        }
    }

    //解除广播
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(seekBarUpdate);
        unregisterReceiver(musicCompletion);
        unregisterReceiver(notificationState);
    }
}
