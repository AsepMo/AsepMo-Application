package com.asepmo.application;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.content.DialogInterface;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.ClipData;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.ArrayList;
import java.io.File;

import com.asepmo.R;
import com.asepmo.engine.graphics.typeface.CalligraphyContextWrapper;
import com.asepmo.application.browser.BrowserActivity;
import com.asepmo.application.updater.Updater;
import com.asepmo.application.updater.UpdateModel;
import com.asepmo.application.updater.UpdaterService;
import com.asepmo.application.updater.UpdateListener;
import com.asepmo.application.updater.UpgradeUtil;

public class ApplicationActivity extends AppCompatActivity
{
    
    public static String TAG = ApplicationActivity.class.getSimpleName();
    public static void start(Context c) {
        Intent mApplication = new Intent(c, ApplicationActivity.class);
        c.startActivity(mApplication);
    }
	
	private String mDirPath;
    public static final String UPGRADE = "upgrade";
    private UpdaterService loadFileService;
    
    private boolean isRegisteredService;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        
        Toolbar mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDirPath = createDownLoadUpgradeDir(getApplicationContext());
		new Updater(ApplicationActivity.this, "https://raw.githubusercontent.com/AsepMo/AsepMo-Application/master/Application/update_application.json", new UpdateListener() {
				@Override
				public void onJsonDataReceived(final UpdateModel updateModel, JSONObject jsonObject) {
					if (Updater.getCurrentVersionCode(ApplicationActivity.this) < updateModel.getVersionCode()) {
						new AlertDialog.Builder(ApplicationActivity.this)
                            .setTitle(getString(R.string.actions_update))
                            .setCancelable(updateModel.isCancellable())
                            .setPositiveButton(getString(R.string.app_updater_btn_update), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
									String url = updateModel.getUrl();
                                    Intent intent = new Intent(getApplicationContext(), UpdaterService.class);
									intent.putExtra("down_load_path", mDirPath);
									intent.putExtra("down_load_url", url);
									bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                                }
                            })
                            .show();
					}
				}

				@Override
				public void onError(String error) {
					// Do something

				}
			}).execute();
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

	public void setStopDownload(){
		if (isRegisteredService) {
			unbindService(serviceConnection);
			isRegisteredService = false;
		}
	}
	
	public void setDeleteFile()
	{
		if (isRegisteredService) {
			unbindService(serviceConnection);
			isRegisteredService = false;
		}
		File dir = new File(mDirPath);
		UpgradeUtil.deleteContentsOfDir(dir);
	}

	private static String createDownLoadUpgradeDir(Context context) {
        String dir = null;
        final String dirName = UPGRADE;
        File root = null;
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            root = context.getExternalFilesDir(null);
        } else {
            root = context.getFilesDir();
        }
        File file = new File(root, dirName);
        file.mkdirs();
        dir = file.getAbsolutePath();
        return dir;
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            loadFileService = ((UpdaterService.LocalBinder) service).getService();
            isRegisteredService = true;

            loadFileService.setOnProgressChangeListener(new UpdaterService.onProgressChangeListener() {
					@Override
					public void onProgressChange(final int progress, final String message) {
						/*handler.post(new Runnable() {
						 @Override
						 public void run() {
						 messageTextView.setText(message + ",Progress" + progress + "%");
						 }
						 });*/
					}
				});
            Log.i(TAG, "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "onServiceDisconnected");
            loadFileService = null;
            isRegisteredService = false;
        }
    };
}
