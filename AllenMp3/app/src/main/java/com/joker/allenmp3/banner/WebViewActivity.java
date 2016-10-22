package com.joker.allenmp3.banner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.joker.allenmp3.R;

public class WebViewActivity extends AppCompatActivity {
    private WebView wv;
    private ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        wv = (WebView) findViewById(R.id.wv);
        pb = (ProgressBar) findViewById(R.id.pb);
        WebSettings ws = wv.getSettings();
        ws.setSupportZoom(true);//支持缩放
        ws.setDisplayZoomControls(true);//缩放的控件
        ws.setJavaScriptEnabled(true);//使用脚本语言
        ws.setBuiltInZoomControls(true);//支持缩放按钮
        ws.setLoadWithOverviewMode(true);//缩放至屏幕大小
        ws.setDomStorageEnabled(true);//支持数据库
        ws.setUseWideViewPort(true);//关键点手机端访问

        wv.setBackgroundColor(getResources().getColor(R.color.white));
        wv.setWebViewClient(new WebViewClient());//在程序链接里面打开新的链接，解析网页
        wv.setWebChromeClient(new MyWebChrome());//加载网页
        wv.loadUrl(getIntent().getStringExtra("url"));
    }
    public class MyWebChrome extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view,
                                      int newProgress) {
            super.onProgressChanged(view, newProgress);
            pb.setVisibility(View.VISIBLE);//显示进度条
//            Log.d("=====Progress",""+newProgress);
            if (newProgress == 100){
                pb.setVisibility(View.GONE);//隐藏进度条
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        wv.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() ==KeyEvent.KEYCODE_BACK && wv.canGoBack()){
            wv.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
