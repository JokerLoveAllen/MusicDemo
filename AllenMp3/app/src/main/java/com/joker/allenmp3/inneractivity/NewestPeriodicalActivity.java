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
import com.google.gson.reflect.TypeToken;
import com.joker.allenmp3.R;
import com.joker.allenmp3.adapter.NewestPeriodicalListViewAdapter;
import com.joker.allenmp3.application.MyApplication;
import com.joker.allenmp3.entity.NPreodicalData;
import com.joker.allenmp3.util.HttpUrl;
import com.joker.allenmp3.util.RefreshLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NewestPeriodicalActivity extends AppCompatActivity
        implements RefreshLayout.OnRefreshListener,
        RefreshLayout.OnLoadListener{
    List<NPreodicalData> nPreodicalDataList=new ArrayList<>();
    private RefreshLayout refreshLayout;
    private ListView listview;
    private int page=1;
    private ImageView loading;
    private AnimationDrawable animationDrawable;
    private ImageView preArrow;
    private NewestPeriodicalListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newest_periodical_layout);
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
//        Intent intent=new Intent(this,PeriodicalPlayActivity.class);
//        Bundle bundle=new Bundle();
//        bundle.putSerializable("data", (Serializable) nPreodicalDataList);
//        intent.putExtras(bundle);
//        intent.putExtra("position",position);
//        startActivity(intent);
//    }

    @Override
    public void onRefresh() {
        page=1;
        getNPData(page);
    }

    @Override
    public void onLoad() {
        page++;
        getNPData(page);
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    adapter=new NewestPeriodicalListViewAdapter(NewestPeriodicalActivity.this,nPreodicalDataList);
                    listview.setAdapter(adapter);
                    animationDrawable.stop();
                    loading.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);
                    break;
                case 2:
                    adapter=new NewestPeriodicalListViewAdapter(NewestPeriodicalActivity.this,nPreodicalDataList);
                    adapter.notifyDataSetChanged();
                    refreshLayout.setLoading(false);
                    break;
            }
        }
    };
    private void getNPData(final int page){
        String url= HttpUrl.NPERIODICAL;
        url+=String.valueOf(page);
        StringRequest request=new StringRequest(0, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                switch (page){
                    case 1:
                        nPreodicalDataList= gson.fromJson(response,new TypeToken<List<NPreodicalData>>(){}.getType());
                        handler.sendEmptyMessage(1);
                        break;
                    default:
                        nPreodicalDataList.addAll((Collection<? extends NPreodicalData>) gson.fromJson(response,new TypeToken<List<NPreodicalData>>(){}.getType()));
                        handler.sendEmptyMessage(2);
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
