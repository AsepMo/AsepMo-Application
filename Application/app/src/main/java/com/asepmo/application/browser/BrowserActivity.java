package com.asepmo.application.browser;

import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.asepmo.R;

public class BrowserActivity extends AbstractBrowserActivity {

    public static void start(Context c, String url) {
        Intent mApplication = new Intent(c, BrowserActivity.class);
        mApplication.setAction(ACTION_OPEN_URL);
        mApplication.putExtra(EXTRA_URL, url);
        c.startActivity(mApplication);
    }
	private SharedPreferences mPreferences;

	private CookieManager mCookieManager;
    public static String ACTION_OPEN_URL = "open_url";
    public static String EXTRA_URL = "url";
    private Handler mHandler = new Handler();
    private Runnable mRunner = new Runnable()    
    {
        @Override
        public void run() {
            deleteTab(0);
        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPreferences = getSharedPreferences(PreferenceConstants.PREFERENCES, 0);
        String action = getIntent().getAction();

        if (action != null && action.equals(ACTION_OPEN_URL)) {
            String url = getIntent().getStringExtra(EXTRA_URL);
            newTab(url, true);
            mHandler.postDelayed(mRunner, 1500);
        }
	}

	@Override
	public void updateCookiePreference() {
		if (mPreferences == null) {
			mPreferences = getSharedPreferences(PreferenceConstants.PREFERENCES, 0);
		}
		mCookieManager = CookieManager.getInstance();
		CookieSyncManager.createInstance(this);
		mCookieManager.setAcceptCookie(mPreferences.getBoolean(PreferenceConstants.COOKIES, true));
		super.updateCookiePreference();
	}

	@Override
	public synchronized void initializeTabs() {
		super.initializeTabs();
		restoreOrNewTab();
		// if incognito mode use newTab(null, true); instead
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_browser, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleNewIntent(intent);
		super.onNewIntent(intent);

	}

	@Override
	protected void onPause() {
		super.onPause();
		saveOpenTabs();
        mHandler.removeCallbacks(mRunner);
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunner);
    }


	@Override
	public void updateHistory(String title, String url) {
		super.updateHistory(title, url);
		addItemToHistory(title, url);
	}

	@Override
	public boolean isIncognito() {
		return false;
	}

	@Override
	public void closeActivity() {
		closeDrawers();
		moveTaskToBack(true);
	}
}
