package com.example.shahajalal.sajibwebapplication;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class WbViewActivity extends AppCompatActivity {

    private WebView webview;
    boolean doubleback=false;
    private AdView adView;
    private ProgressBar progressBar;
    String url;

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wb_view);

        SharedPreferences preferences = this.getSharedPreferences("webactivity", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("web", "lol");
        edit.apply();

        progressBar=findViewById(R.id.progressBar);

        //Admob implementation
       // MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        adView=findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        SharedPreferences prefs = getSharedPreferences("prefName", MODE_PRIVATE);
        String getpin = prefs.getString("name", null);
       if (getpin!=null) {
           String gpin[] = getpin.split(" ");
            url= gpin[1];
       }



        webview= findViewById(R.id.webviewid);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings websettings=webview.getSettings();
        websettings.setJavaScriptEnabled(true);
        websettings.setBuiltInZoomControls(true);
        websettings.getDatabaseEnabled();
        websettings.getCacheMode();
        websettings.getAllowUniversalAccessFromFileURLs();
        websettings.getAllowFileAccessFromFileURLs();
        websettings.getAllowContentAccess();
        websettings.supportMultipleWindows();
        websettings.setMediaPlaybackRequiresUserGesture(true);
        websettings.getJavaScriptCanOpenWindowsAutomatically();
        websettings.setDomStorageEnabled(true);
        websettings.setLoadsImagesAutomatically(true);
        webview.setWebViewClient(new myWebClient());
        webview.loadUrl(url);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.settingsid){
            Intent intent=new Intent(WbViewActivity.this,Updatesettings.class);
            startActivity(intent);
            finish();
        }else if(item.getItemId()==R.id.exitid){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (doubleback) {
            WbViewActivity.this.moveTaskToBack(true);
           return;
        }

        if (webview.canGoBack()) {
            webview.goBack();
        }

            this.doubleback = true;
            Toast.makeText(this, "Please click again to Exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleback = false;
                }
            }, 2000);


    }

    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }
        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            // TODO Auto-generated method stub
            Toast.makeText(WbViewActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            //super.onReceivedError(view, errorCode, description, failingUrl);
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.GONE);
            super.onPageFinished(view, url);


        }
    }

}