package com.example.micka.iportproject;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewWrappingActivity extends AppCompatActivity {

    private WebView webView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_wrapping);

        //full screen mode
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //hide top bar
        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //initial singleton variable of shared preference
        SharePrefSingleton sPref = SharePrefSingleton.getInstance(getApplicationContext());

        //init webView
        webView = (WebView) findViewById(R.id.wv_broadcast_holder);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(sPref.getUrl());


        //handle long click
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                openSettings();
                return true;
            }
        });
    }

    private void openSettings(){
        SettingsDialog settingsDialog = new SettingsDialog();
        settingsDialog.show(getSupportFragmentManager(),"settings dialog");
    }
}
