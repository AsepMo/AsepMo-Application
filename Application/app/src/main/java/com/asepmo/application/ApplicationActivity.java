package com.asepmo.application;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuItem;

import com.asepmo.R;
import com.asepmo.engine.graphics.typeface.CalligraphyContextWrapper;
import com.asepmo.application.browser.BrowserActivity;

public class ApplicationActivity extends AppCompatActivity
{
    
    public static String TAG = ApplicationActivity.class.getSimpleName();
    public static void start(Context c) {
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
     
    @Override
    protected void attachBaseContext(Context newBase)
    {
        // TODO: Implement this method
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // TODO: Implement this method
        menu.add("Browser").setIcon(R.drawable.ic_browser)
            .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
                @Override
                public boolean onMenuItemClick(MenuItem item)
                {
                    BrowserActivity.start(ApplicationActivity.this, "https://m.youtube.com");
                    ApplicationActivity.this.finish();
                    return true;
                }
            })
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onCreateOptionsMenu(menu);
    }

}
