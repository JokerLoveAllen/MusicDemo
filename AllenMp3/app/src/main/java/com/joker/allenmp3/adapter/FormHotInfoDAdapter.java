package com.joker.allenmp3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joker.allenmp3.R;
import com.joker.allenmp3.application.MyApplication;
import com.joker.allenmp3.entity.DiscoverDailyData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class FormHotInfoDAdapter extends BaseAdapter {
    private List<DiscoverDailyData.DataBean.ItemsBean.PostAudioBean> postAudioBeanList=new ArrayList<>();
    private Context context;

    public FormHotInfoDAdapter(List postAudioBeanList, Context context) {
        this.postAudioBeanList = postAudioBeanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return postAudioBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return postAudioBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DiscoverDailyData.DataBean.ItemsBean.PostAudioBean postAudio=postAudioBeanList.get(position);
        ViewHolder holder;
        if (convertView==null) {
            holder=new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pplistview_layout, null);
            holder.album= (ImageView) convertView.findViewById(R.id.PPListViewAlbum);
            holder.artist= (TextView) convertView.findViewById(R.id.PPListViewArtist);
            holder.name= (TextView) convertView.findViewById(R.id.PPListViewName);
            holder.num= (TextView) convertView.findViewById(R.id.PPListViewNum);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        if(position<9) {
            holder.num.setText("0" + String.valueOf(position+1));
        }else {
            holder.num.setText(String.valueOf(position+1));
        }
        holder.artist.setText(postAudio.getArtist());
        holder.name.setText(postAudio.getSong_name());
        ImageLoader.getInstance().displayImage(postAudio.getCover(),holder.album, MyApplication.getOptions());
        return convertView;
    }

    private class ViewHolder{
        private TextView num;
        private TextView name;
        private TextView artist;
        private ImageView album;
    }
}
