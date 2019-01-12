package com.example.shahajalal.sajibwebapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private int progress;
    private ProgressBar progressBar;
    private String human="SW5pTG9hZA==";
    private String women="Y29tLmV4YW1wbGUuc2hhaGFqYWxhbC5zYWppYndlYmFwcGxpY2F0aW9u";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String shuman = decp(human);
        String swomen = decp(women);

        if (shuman.equals(this.getTitle())) {

            if (swomen.equals(this.getApplicationContext().getPackageName())) {

                final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                    Toast.makeText(MainActivity.this, "Online", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(MainActivity.this, "You are offline Please go to online", Toast.LENGTH_SHORT).show();

                }


                SharedPreferences prefs = getSharedPreferences("webactivity", MODE_PRIVATE);
                String getpin = prefs.getString("web", null);

                if (getpin == null)

                {

                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN
                    );

                    progressBar = findViewById(R.id.progressbarid);


                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            dowork();
                            gotoinput();
                        }
                    });
                    thread.start();

                } else {
                    Intent intent34 = new Intent(MainActivity.this, WbViewActivity.class);
                    startActivity(intent34);
                    finish();
                }

            }
        }else{
            finish();
        }
    }


   public String decp(String s){
       String en=s;
       String text="";
       byte[] decrypt= Base64.decode(en, Base64.DEFAULT);
       try {
           text = new String(decrypt, "UTF-8");
       } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
       }

       return text;

   }

    public void dowork() {


        for (progress = 20; progress <=100; progress = progress + 20) {
            try {
                Thread.sleep(1000);
                progressBar.setProgress(progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void gotoinput(){
        Intent intent =new Intent(MainActivity.this,InputDialogbox.class);
        startActivity(intent);
        finish();
    }
}

