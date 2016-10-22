package com.joker.allenmp3.fragment;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.joker.allenmp3.R;
import com.joker.allenmp3.adapter.DiscoverListViewAdapter;
import com.joker.allenmp3.adapter.TestNormalAdapter;
import com.joker.allenmp3.application.MyApplication;
import com.joker.allenmp3.dao.WebDiscoverDao;
import com.joker.allenmp3.dao.WebMainDao;
import com.joker.allenmp3.entity.DiscoverDailyData;
import com.joker.allenmp3.entity.DiscoversData;
import com.joker.allenmp3.entity.WebDiscoverCache;
import com.joker.allenmp3.entity.WebMainCache;
import com.joker.allenmp3.inneractivity.FormHotInfoActivity;
import com.joker.allenmp3.inneractivity.NewestPeriodicalActivity;
import com.joker.allenmp3.inneractivity.NewsRecommendationActivity;
import com.joker.allenmp3.util.HttpUrl;
import com.joker.allenmp3.util.RefreshLayout;
import com.jude.rollviewpager.RollPagerView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebMusicFragment extends Fragment
         implements RefreshLayout.OnRefreshListener{
    private ListView musicList;
    private ImageView loading;
    private AnimationDrawable animationDrawable;
    private RefreshLayout swipeRefreshLayout;
    private RollPagerView pagerView;
    private  View view;
    private WebDiscoverDao discoverDao;
    private WebMainDao mDao;
    private DiscoverDailyData discoverDailyData;
    private DiscoversData discoversData;
    public WebMusicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_music, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loading= (ImageView) getView().findViewById(R.id.Loading);
        animationDrawable= (AnimationDrawable) loading.getDrawable();
        animationDrawable.start();
        musicList = (ListView) getView().findViewById(R.id.DiscoverListView);
        mDao = new WebMainDao(getContext());
        discoverDao = new WebDiscoverDao(getContext());

        swipeRefreshLayout= (RefreshLayout) getView().findViewById(R.id.DiscoverSwipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.titleText);
        swipeRefreshLayout.setOnRefreshListener(this);
        view = LayoutInflater.from(getContext())
                .inflate(R.layout.header_discover_layout,null);
        pagerView = (RollPagerView) view.findViewById(R.id.pv);
        pagerView.setPlayDelay(3000);
        pagerView.setAnimationDurtion(500);
        musicList.addHeaderView(view);

        getCache();
        setMoreMusic();//加载更多音乐
    }

    @Override
    public void onRefresh() {
        mDao.deleteAll();
        getRequest(HttpUrl.MAINDAILY);
        discoverDao.deleteAll();
        getRequest(HttpUrl.DISCOVERHEADERS);
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what ==1){
                musicList.setAdapter(new DiscoverListViewAdapter(getActivity(),
                        discoverDailyData.getData().getItems()));
                animationDrawable.stop();
                loading.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            } else if (msg.what ==2){
                pagerView.setAdapter(new TestNormalAdapter(getContext(),
                        discoversData.getAd()));
                setRecommend(discoversData);
                setAlbum(discoversData);
            }
        }
    };
    public void getRequest(final String url){
        final StringRequest request=new StringRequest(0, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                switch (url){
                    case HttpUrl.MAINDAILY:{
                        discoverDailyData =
                                gson.fromJson(response,DiscoverDailyData.class);
                        handler.sendEmptyMessage(1);
                        WebMainCache mainCache = new WebMainCache();
                        mainCache.setData(response);
                        mainCache.setId(mDao.searchAll().size()+1);
                        mDao.addMain(mainCache);
                        break;
                    }
                    case HttpUrl.DISCOVERHEADERS:{
                        discoversData = gson.fromJson(response,DiscoversData.class);
                        handler.sendEmptyMessage(2);
                        WebDiscoverCache discoverCache = new WebDiscoverCache();
                        discoverCache.setData(response);
                        discoverCache.setId(discoverDao.searchAll().size()+1);
                        discoverDao.addDiscover(discoverCache);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.getMessage()
                        ,Toast.LENGTH_SHORT).show();
            }
        });
        MyApplication.getInstens().getQueue().add(request);
    }


    //设置音乐期刊
    private void setAlbum( DiscoversData data){
        DiscoversData.AlbumBean volsBean=data.getAlbum();
        ImageView PeriodicalTopicImage= (ImageView) view.findViewById(R.id.PeriodicalTopicImage);
        TextView PeriodicalVols= (TextView) view.findViewById(R.id.PeriodicalVols);
        TextView PeriodicalTitle= (TextView) view.findViewById(R.id.PeriodicalTitle);
        TextView PeriodicalFav= (TextView) view.findViewById(R.id.PeriodicalFav);
        LinearLayout linearLayout= (LinearLayout) view.findViewById(R.id.PeriodicalLinear);
        ImageLoader.getInstance().displayImage(volsBean.getMphoto(),PeriodicalTopicImage);
        PeriodicalVols.setText(String.valueOf(volsBean.getMnum()));
        PeriodicalTitle.setText(String.valueOf(volsBean.getMname()));
        PeriodicalFav.setText(String.valueOf(volsBean.getPlay_count()));
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewestPeriodicalActivity.class);
                getActivity().startActivity(intent);
            }
        });

    }
    //设置原创推荐
    private void  setRecommend( DiscoversData data){
        ImageView rPoster= (ImageView) view.findViewById(R.id.RecommendPoster);
        TextView rName= (TextView) view.findViewById(R.id.RecommendName);
        TextView rArtist= (TextView) view.findViewById(R.id.RecommendArtist);
        rName.setText(data.getMusician().getTrackname());
        rArtist.setText(data.getMusician().getDescription());
        ImageLoader.getInstance().displayImage(data.getMusician().getImage(),rPoster);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.RecommMore);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewsRecommendationActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
    //设置更多歌曲
    public void  setMoreMusic(){
        musicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(), FormHotInfoActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("data",(discoverDailyData).getData().getItems().get(i-1));
                intent.putExtra("discover",true);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
    }

    public void getCache(){
        Gson gson =new Gson();
        if (mDao.searchAll().size()==0){
            getRequest(HttpUrl.MAINDAILY);
        } else {
            discoverDailyData =
                    gson.fromJson(mDao.searchAll().get(0).getData()
                            ,DiscoverDailyData.class);
            handler.sendEmptyMessage(1);
        }
        if (discoverDao.searchAll().size()==0){
            getRequest(HttpUrl.DISCOVERHEADERS);
        } else {
            discoversData =
                    gson.fromJson(discoverDao.searchAll().get(0).getData()
                              ,DiscoversData.class);
            handler.sendEmptyMessage(2);
        }
    }
}
