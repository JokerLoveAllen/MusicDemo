package com.joker.allenmp3.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.joker.allenmp3.banner.WebViewActivity;
import com.joker.allenmp3.entity.DiscoversData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

//banner条幅的适配器
public class TestNormalAdapter extends PagerAdapter {
    private List<DiscoversData.AdBean> viewList=new ArrayList<>();
    private Context context;

        public TestNormalAdapter( Context context,List<DiscoversData.AdBean> views) {
            this.context =context;
            viewList = views;
        }

    @Override
   public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView=new ImageView(context);
            ImageLoader.getInstance().displayImage(viewList.get(position% viewList.size())
                    .getImage(),imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT
                            ,ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, WebViewActivity.class);
                intent.putExtra("url",viewList.get(position % viewList.size()).getLink());
                context.startActivity(intent);
            }
        });
            container.addView(imageView);
            return imageView;
        }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    @Override
   public int getCount() {
            return viewList.size();
        }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}