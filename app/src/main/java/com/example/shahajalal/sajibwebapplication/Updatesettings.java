package com.example.shahajalal.sajibwebapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Updatesettings extends AppCompatActivity {
    private EditText pin;
    private Button send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatesettings);

        pin=findViewById(R.id.pininput);
        send=findViewById(R.id.sendbutton);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pinin = pin.getText().toString();
                String method = "sendpin";
                BackgroudTask backgroundTask = new BackgroudTask(Updatesettings.this);
                backgroundTask.execute(method, pinin);

                SharedPreferences prefs = getSharedPreferences("prefName", MODE_PRIVATE);
                String getpin= prefs.getString("name", null);

                try {
                   String[] gpin = getpin.split(" ");
                    if (gpin[0].equals(pinin)) {
                        Intent intent4 = new Intent(Updatesettings.this, WbViewActivity.class);
                        startActivity(intent4);
                        finish();

                    } else {
                        Toast.makeText(Updatesettings.this, "Please wait ", Toast.LENGTH_LONG).show();
                    }
                } catch (NullPointerException e) {

                }
            }
        });
    }
}
