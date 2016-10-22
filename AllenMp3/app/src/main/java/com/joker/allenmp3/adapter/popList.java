package com.joker.allenmp3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.joker.allenmp3.R;
import com.joker.allenmp3.entity.Music;

import java.util.List;

/**
 * Created by Administrator on 2016/9/9.
 */
public class popList extends BaseAdapter {
    private Context context;
    private List<Music> musicList;

    public popList(Context context, List<Music> musicList) {
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
            view = LayoutInflater.from(context).inflate(R.layout.poplist,null);
            vh.musicName = (TextView) view.findViewById(R.id.name);
            vh.author = (TextView) view.findViewById(R.id.author);
            vh.showTime = (TextView) view.findViewById(R.id.time);
            vh.id = (TextView) view.findViewById(R.id.Id);
            view.setTag(vh);
        }  else {
            vh = (ViewHolder) view.getTag();
        }

        Music music = musicList.get(i);
        vh.id.setText(i+1+"");
        vh.musicName.setText(music.getDisplayName());
        vh.author.setText(music.getArtist());
        vh.showTime.setText(music.getDuration());
        return view;
    }
    private class ViewHolder{
        public TextView musicName,showTime,author,id;
    }
}
