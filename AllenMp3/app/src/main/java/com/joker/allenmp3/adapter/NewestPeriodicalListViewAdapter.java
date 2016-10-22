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
import com.joker.allenmp3.entity.NPreodicalData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chen on 2016/4/8.
 */
public class NewestPeriodicalListViewAdapter extends BaseAdapter{
    List<NPreodicalData> nPreodicalDataList=new ArrayList<>();
    private Context context;

    public NewestPeriodicalListViewAdapter(Context context, List nPreodicalDataList) {
        this.context = context;
        this.nPreodicalDataList=nPreodicalDataList;
    }

    @Override
    public int getCount() {
        return nPreodicalDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return nPreodicalDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NPreodicalData nPreodicalData=nPreodicalDataList.get(position);
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_newestperiodical_listview_layout,null);
            holder.topicImg= (ImageView) convertView.findViewById(R.id.NPeriodicalTopicImage);
            holder.title= (TextView) convertView.findViewById(R.id.NPeriodicalTitle);
            holder.fav= (TextView) convertView.findViewById(R.id.NPeriodicalFav);
            holder.comment= (TextView) convertView.findViewById(R.id.NPeriodicalComm);
            holder.vols= (TextView) convertView.findViewById(R.id.NPeriodicalVols);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(nPreodicalData.getOphoto(),holder.topicImg, MyApplication.getOptions());
        holder.title.setText(nPreodicalData.getMname());
        holder.vols.setText(nPreodicalData.getMnum());
        holder.comment.setText(nPreodicalData.getComment_count());
        holder.fav.setText(nPreodicalData.getPlay_count());
        return convertView;
    }
    private class ViewHolder{
        private ImageView topicImg;
        private TextView title;
        private TextView fav;
        private TextView comment;
        private TextView vols;
    }
}
