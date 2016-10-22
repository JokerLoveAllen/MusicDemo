package com.joker.allenmp3.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.joker.allenmp3.R;
import com.joker.allenmp3.ShowMusicActivity;
import com.joker.allenmp3.application.MyApplication;
import com.joker.allenmp3.entity.Music;
import com.joker.allenmp3.service.mediaPlayerService;
import com.joker.allenmp3.view.RoundImageView;

import java.util.List;

/**
 * Created by Administrator on 2016/9/9.
 */
public class MusicList extends BaseAdapter {
    private Context context;
    private List<Music> musicList;

    public MusicList(Context context, List<Music> musicList) {
        this.context = context;
        this.musicList = musicList;
    }

    @Override
    public int getCount() {
        return musicList.size();
    }

    @Override
    public Object getItem(int i) {
        return musicList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder vh;
        if (view ==null){
          vh = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.showl_list,null);
            vh.musicName = (TextView) view.findViewById(R.id.title);
            vh.author = (TextView) view.findViewById(R.id.author);
            vh.showTime = (TextView) view.findViewById(R.id.time);
            vh.riv = (RoundImageView) view.findViewById(R.id.round);
            view.setTag(vh);
        }  else {
            vh = (ViewHolder) view.getTag();
        }

        Music music = musicList.get(i);
        vh.musicName.setText(music.getDisplayName());
        vh.author.setText(music.getArtist());
        vh.showTime.setText(music.getDuration());
        if (!music.getImageUrl().equals("")){
            vh.riv.setImageURI(Uri.parse(music.getImageUrl()));
        } else {
            vh.riv.setImageResource(R.mipmap.n8);
        }
//        view.setOnClickListener(new Mp3ClickListener(i));
        return view;
    }
    private class ViewHolder{
        public RoundImageView riv;
        public TextView musicName,showTime,author;
    }
    public class Mp3ClickListener implements View.OnClickListener{
        private int i;
        public Mp3ClickListener(int i){
            this.i = i;
        }
        @Override
        public void onClick(View view) {
            MyApplication.getInstens().setPosition(i);
            Intent intent = new Intent(context, mediaPlayerService.class);
            intent.putExtra("music", musicList.get(i));
            context.startService(intent);
            intent = new Intent(context, ShowMusicActivity.class);
            context.startActivity(intent);

        }
    }
}
