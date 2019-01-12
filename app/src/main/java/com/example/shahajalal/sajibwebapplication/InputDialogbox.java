package com.example.shahajalal.sajibwebapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class InputDialogbox extends AppCompatActivity {

    String gpin[];
    ProgressBar progressBar;
    int progress=20;
    String m_Text;
    String getpin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_dialogbox);

        progressBar=findViewById(R.id.progressbaridlol);


            AlertDialog.Builder builder = new AlertDialog.Builder(InputDialogbox.this);
            builder.setTitle("Please give Pin");

            final EditText input = new EditText(InputDialogbox.this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    m_Text = input.getText().toString();
                    String method = "sendpin";
                    BackgroudTask backgroundTask = new BackgroudTask(InputDialogbox.this);
                    backgroundTask.execute(method, m_Text);

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            dowork();
                            finish();
                        }
                    });
                    thread.start();
                }

            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    dialog.cancel();
                }
            });

            builder.show();


    }
    public void dowork() {

         while(getpin==null) {
             SharedPreferences prefs = getSharedPreferences("prefName", MODE_PRIVATE);
             getpin= prefs.getString("name", null);
            try {
                Thread.sleep(1000);

                    progressBar.setProgress(progress);
                    try {
                        gpin = getpin.split(" ");
                        if (gpin[0].equals(m_Text)) {
                            Intent intent4 = new Intent(InputDialogbox.this, WbViewActivity.class);
                            startActivity(intent4);
                            break;

                        } else {
                            //Toast.makeText(InputDialogbox.this, "Please wait ", Toast.LENGTH_LONG).show();
                        }
                    } catch (NullPointerException e) {

                    }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            progress=progress+20;
        }
    }



}
