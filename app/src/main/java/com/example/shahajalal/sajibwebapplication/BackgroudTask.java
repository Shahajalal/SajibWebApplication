package com.example.shahajalal.sajibwebapplication;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroudTask extends AsyncTask<String,Void,String>{

    private String thelol="aHR0cDovL2luaXR1eC5jb20vV2ViYXBwL2ZhdGNocGluLnBocA==";
    Context context;
    BackgroudTask(Context context){
        this.context=context;
    }

    String loginurl;

    @Override
    protected void onPreExecute() {
        loginurl=decp(thelol);
    }

    @Override
    protected String doInBackground(String... params) {

        String method=params[0];
        if(method.equals("sendpin")){
            String pin=params[1];

            try {
                URL url =new URL(loginurl);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data= URLEncoder.encode("pin","UTF-8")+"="+URLEncoder.encode(pin,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();

                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response=bufferedReader.readLine();
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                //Log.d("name",response);
                return  response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
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

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String response) {

        if (response != null) {
            String name = response;

            SharedPreferences preferences = context.getSharedPreferences("prefName", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putString("name", name);
            edit.apply();
        }
        else{
            Log.i("userName invalid", "onPostExecute: ");
        }
    }

}

