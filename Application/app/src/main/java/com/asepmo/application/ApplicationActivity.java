package com.asepmo.application;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.AsyncTask;

public class ApplicationActivity extends AppCompatActivity
{
    
    public static String TAG = ApplicationActivity.class.getSimpleName();
    public static void start(Context c, String url) {
        Intent mApplication = new Intent(c, ApplicationActivity.class);
        c.startActivity(mApplication);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        
        Toolbar mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        
     }
}
