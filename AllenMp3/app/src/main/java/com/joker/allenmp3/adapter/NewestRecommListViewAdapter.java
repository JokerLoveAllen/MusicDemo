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
import com.joker.allenmp3.entity.MusitionData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Chen on 2016/4/8.
 */
public class NewestRecommListViewAdapter extends BaseAdapter {
    List<MusitionData.ListBean> nPreodicalDataList = new ArrayList<>();
    private Context context;

    public NewestRecommListViewAdapter(Context context, List nPreodicalDataList) {
        this.context = context;
        this.nPreodicalDataList = nPreodicalDataList;
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
        MusitionData.ListBean nPreodicalData = nPreodicalDataList.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_articleactivity_listview_layout, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(nPreodicalData.getCover_url(),holder.topicImage, MyApplication.getOptions());
        holder.title.setText(nPreodicalData.getSongname());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.AArticleItemTopicImage)
        ImageView topicImage;
        @Bind(R.id.AArticleItemTitle)
        TextView title;
        @Bind(R.id.AArticleItemAuthor)
        TextView author;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
