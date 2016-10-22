package com.joker.allenmp3.inneractivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.joker.allenmp3.R;
import com.joker.allenmp3.adapter.NewestRecommListViewAdapter;
import com.joker.allenmp3.application.MyApplication;
import com.joker.allenmp3.entity.MusitionData;
import com.joker.allenmp3.util.HttpUrl;
import com.joker.allenmp3.util.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chen on 2016/4/10.
 */
public class NewsRecommendationActivity extends AppCompatActivity
        implements RefreshLayout.OnRefreshListener,RefreshLayout.OnLoadListener{
    List<MusitionData.ListBean> musitionDataList=new ArrayList<>();
    private RefreshLayout refreshLayout;
    private ListView listview;
    private int page=1;
    private ImageView loading;
    private AnimationDrawable animationDrawable;
    private ImageView preArrow;
    private NewestRecommListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newest_recomm_layout);
        preArrow= (ImageView) findViewById(R.id.PreArrow);
        listview= (ListView) findViewById(R.id.NPeriodicalListView);
        refreshLayout= (RefreshLayout) findViewById(R.id.NpSwipe);
        loading= (ImageView) findViewById(R.id.Loading);
        animationDrawable= (AnimationDrawable) loading.getDrawable();
        animationDrawable.start();
        refreshLayout.setColorSchemeResources(R.color.titleText);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadListener(this);
        onRefresh();
//        listview.setOnItemClickListener(this);
        preArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent=new Intent(this,RecommendationActivity.class);
//        Bundle bundle=new Bundle();
//        bundle.putSerializable("data", (Serializable) musitionDataList);
//        intent.putExtras(bundle);
//        intent.putExtra("position",position);
//        intent.putExtra("page",page);
//        startActivity(intent);
//    }

    @Override
    public void onRefresh() {
        page=1;
        getData(page);
    }

    @Override
    public void onLoad() {
        page++;
        getData(page);
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    adapter=new NewestRecommListViewAdapter(NewsRecommendationActivity.this,musitionDataList);
                    listview.setAdapter(adapter);
                    animationDrawable.stop();
                    loading.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);
                    break;
                case 2:
                    adapter=new NewestRecommListViewAdapter(NewsRecommendationActivity.this,musitionDataList);
                    adapter.notifyDataSetChanged();
                    refreshLayout.setLoading(false);
                    break;
            }
        }
    };
    private void getData(final int page){
        String url= HttpUrl.MUSITION+String.valueOf(page);
        StringRequest request=new StringRequest(0, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                response=response.substring(7);
                response=response.substring(0,response.length()-1);
                MusitionData musitionData = gson.fromJson(response,MusitionData.class);
                switch (page){
                    case 1:
                        musitionDataList=musitionData.getList();
                        handler.sendEmptyMessage(1);
                        break;
                    default:
                        musitionDataList.addAll(musitionData.getList());
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MyApplication.getInstens().getQueue().add(request);
    }
}
