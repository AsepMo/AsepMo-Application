package com.asepmo.application;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.asepmo.R;
import com.asepmo.application.browser.BrowserActivity;
import com.asepmo.engine.graphics.typeface.CalligraphyContextWrapper;

public class ApplicationStarter extends AppCompatActivity {
    
    public static String TAG = ApplicationStarter.class.getSimpleName();
    private Handler mHandler = new Handler();
	private Runnable mRunner = new Runnable(){
		@Override
		public void run()
		{
			ApplicationActivity.start(ApplicationStarter.this);
            ApplicationStarter.this.finish();
		}
		
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_starter);
        Toolbar mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        
        mHandler.postDelayed(mRunner,2500);
    }

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
		mHandler.removeCallbacks(mRunner);
	}

	@Override
	protected void onDestroy()
	{
		// TODO: Implement this method
		super.onDestroy();
		mHandler.removeCallbacks(mRunner);
	}
  
	
    @Override
    protected void attachBaseContext(Context newBase)
    {
        // TODO: Implement this method
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}
}
