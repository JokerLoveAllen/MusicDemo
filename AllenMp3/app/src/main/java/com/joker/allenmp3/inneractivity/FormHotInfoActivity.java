package com.joker.allenmp3.inneractivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.joker.allenmp3.R;
import com.joker.allenmp3.adapter.FormHotInfoAdapter;
import com.joker.allenmp3.adapter.FormHotInfoDAdapter;
import com.joker.allenmp3.application.MyApplication;
import com.joker.allenmp3.entity.DiscoverDailyData;
import com.joker.allenmp3.entity.ForumData;
import com.joker.allenmp3.service.WebMusicService;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FormHotInfoActivity extends AppCompatActivity {

    @Bind(R.id.PPlayListview)
    ListView listview;
    @Bind(R.id.PreArrow)
    ImageView preArrow;
    @Bind(R.id.web_music_image)
    ImageView web_music_image;
    @Bind(R.id.web_music_name)
    TextView web_music_name;
    @Bind(R.id.web_music_art)
    TextView web_music_art;
    @Bind(R.id.web_bottom)
    LinearLayout web_bottom;
    @Bind(R.id.web_seekbar)
    SeekBar web_seekbar;
    private TextView content;
    private TextView contentless;
    private ImageView topicImage;
    private TextView moreContent;
    private TextView userName;
    private ImageView avatar;
    private static musicState webMusic;
    private SeekBarUpdate seekBarUpdate;
    private AudioNext audioNext;
    private ForumData.DataBean.ItemsBean itemsBean;
    private DiscoverDailyData.DataBean.ItemsBean DitemsBean;
    private DiscoverDailyData.DataBean.ItemsBean.PostAudioBean audioBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_hot_layout);
        ButterKnife.bind(this);
        if (getIntent().getBooleanExtra("discover",false)){
            DitemsBean= (DiscoverDailyData.DataBean.ItemsBean)
                    getIntent().getExtras().get("data");
            listview.addHeaderView(getDHeader());
            listview.setAdapter(new FormHotInfoDAdapter(DitemsBean.getPost_audio(),this));
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    position = position == 0 ?0:position-1;
                    audioBean = DitemsBean.getPost_audio().get(position);
                    MyApplication.getInstens().setAudioBeanList(DitemsBean.getPost_audio());
                    Intent intent = new Intent(FormHotInfoActivity.this, WebMusicService.class);
                    intent.putExtra("info", audioBean);
                    startService(intent);
                    Intent i = new Intent();
                    i.setAction("WebMusic");
                    i.putExtra("flag",1);
                    sendBroadcast(i);
                    Intent intent1 = new Intent(FormHotInfoActivity.this, WebMusicActvity.class);
                    intent1.putExtra("info",position);
                    startActivity(intent1);
                    }
            });

        }else {
            itemsBean = (ForumData.DataBean.ItemsBean) getIntent().getExtras().get("data");
            listview.addHeaderView(getHeader());
            if (itemsBean.getPost_audio() != null) {
                listview.setAdapter(new FormHotInfoAdapter(itemsBean.getPost_audio(), this));
            } else {
                listview.setAdapter(new FormHotInfoAdapter(new ArrayList<>(), this));
            }
        }
        initbroadCast();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (webMusic !=null){
            audioBean = webMusic.getMusic();
            initMusic(audioBean);
        }
    }

    private View getHeader() {
        View view = LayoutInflater.from(this).inflate(R.layout.header_form_hot_info_layout, null);
        content = (TextView) view.findViewById(R.id.PPlayContent);
        contentless = (TextView) view.findViewById(R.id.PPlayContentLess);
        moreContent = (TextView) view.findViewById(R.id.PPlayMoreContent);
        topicImage = (ImageView) view.findViewById(R.id.NPeriodicalTopicImage);
        avatar = (ImageView) view.findViewById(R.id.PPlayAvatar);
        userName = (TextView) view.findViewById(R.id.PPlayUserName);
        userName.setText(itemsBean.getUser_name());
        content.setText(itemsBean.getContent());
        contentless.setText(itemsBean.getContent());
        if (itemsBean.getPost_img()==null){
            topicImage.setVisibility(View.GONE);
        }else {
            ImageLoader.getInstance().displayImage(itemsBean.getPost_img().get(0), topicImage, MyApplication.getOptions());
        }
        ImageLoader.getInstance().displayImage(itemsBean.getUser_avatar(), avatar);
        moreContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentless.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);
                moreContent.setVisibility(View.GONE);
            }
        });
        return view;
    }

    private View getDHeader() {
        View view = LayoutInflater.from(this).inflate(R.layout.header_form_hot_info_layout, null);
        content = (TextView) view.findViewById(R.id.PPlayContent);
        contentless = (TextView) view.findViewById(R.id.PPlayContentLess);
        moreContent = (TextView) view.findViewById(R.id.PPlayMoreContent);
        topicImage = (ImageView) view.findViewById(R.id.NPeriodicalTopicImage);
        avatar = (ImageView) view.findViewById(R.id.PPlayAvatar);
        userName = (TextView) view.findViewById(R.id.PPlayUserName);
        userName.setText(DitemsBean.getUser_name());
        content.setText(DitemsBean.getContent());
        contentless.setText(DitemsBean.getContent());
        if (DitemsBean.getPost_img()==null){
            topicImage.setVisibility(View.GONE);
        }else {
            ImageLoader.getInstance().displayImage(DitemsBean.getPost_img().get(0), topicImage, MyApplication.getOptions());
        }
        ImageLoader.getInstance().displayImage(DitemsBean.getUser_avatar(), avatar);
        moreContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentless.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);
                moreContent.setVisibility(View.GONE);
            }
        });
        return view;
    }

    @OnClick(R.id.PreArrow)
    public void onClick() {
        finish();
    }

    //配置音乐
    private void initMusic(DiscoverDailyData.DataBean.ItemsBean.PostAudioBean bean){
        ImageLoader.getInstance().displayImage(bean.getCover(),web_music_image, MyApplication.getOptions());
        web_music_name.setText(bean.getSong_name());
        web_music_art.setText(bean.getArtist());
        web_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(),WebMusicActvity.class);
                startActivity(i);
            }
        });
    }

    //注册进度条的广播
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
    //接收Service进度更新的广播
    public class SeekBarUpdate extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int progress = intent.getIntExtra("progress",0);
            int max = intent.getIntExtra("max",0);
            web_seekbar.setProgress(progress);
            web_seekbar.setMax(max);
        }
    }
    //自动下一首广播接收
    public class AudioNext extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            audioBean = intent.getParcelableExtra("nextMusic");
            initMusic(audioBean);
        }
    }


    public static void getMusicPosition(Context context){
        webMusic = (musicState) context;
    }
    public interface musicState{
        public DiscoverDailyData.DataBean.ItemsBean.PostAudioBean getMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(seekBarUpdate);
        unregisterReceiver(audioNext);
    }
}
