package com.joker.allenmp3.adapter;

import android.content.Context;
import android.text.Html;
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
public class DiscoverListViewAdapter extends BaseAdapter {
    private Context context;
    private List<DiscoverDailyData.DataBean.ItemsBean> itemsBeanList=new ArrayList<>();

    public DiscoverListViewAdapter(Context context, List itemsBeanList) {
        this.context = context;
        this.itemsBeanList=itemsBeanList;
    }

    @Override
    public int getCount() {
        return itemsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DiscoverDailyData.DataBean.ItemsBean itemsBean=itemsBeanList.get(position);
        ViewHolder holder;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=LayoutInflater.from(context).inflate(R.layout.item_discover_listview_layout,null);
            holder.topicImg= (ImageView) convertView.findViewById(R.id.DailyTopicImage);
            ViewGroup.LayoutParams params=holder.topicImg.getLayoutParams();
            params.height=context.getResources().getDisplayMetrics().widthPixels;
            holder.topicImg.setLayoutParams(params);
            holder.content= (TextView) convertView.findViewById(R.id.DailyContent);
            holder.headImg= (ImageView) convertView.findViewById(R.id.DailyUserHeadImg);
            holder.shareCount = (TextView) convertView.findViewById(R.id.DailyShareCount);
            holder.username= (TextView) convertView.findViewById(R.id.DailyUserName);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.content.setText(Html.fromHtml(itemsBean.getContent()));
        holder.shareCount.setText(String.valueOf(itemsBean.getPost_audio().size()));
        holder.username.setText(itemsBean.getUser_name());
        ImageLoader.getInstance().displayImage(itemsBean.getAvatar(),holder.headImg);
        ImageLoader.getInstance().displayImage(itemsBean.getPost_img().get(0),holder.topicImg, MyApplication.getOptions());
        return convertView;
    }

    private class ViewHolder{
        private ImageView topicImg;
        private ImageView headImg;
        private TextView content;
        private TextView username;
        private TextView shareCount;
    }
}
