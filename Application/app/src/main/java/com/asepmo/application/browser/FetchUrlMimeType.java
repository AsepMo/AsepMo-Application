package com.asepmo.application.browser;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Environment;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpHead;

import java.io.IOException;

/**
 * This class is used to pull down the http headers of a given URL so that we
 * can analyse the mimetype and make any correction needed before we give the
 * URL to the download manager. This operation is needed when the user
 * long-clicks on a link or image and we don't know the mimetype. If the user
 * just clicks on the link, we will do the same steps of correcting the mimetype
 * down in android.os.webkit.LoadListener rather than handling it here.
 */
public class FetchUrlMimeType extends Thread {

	private Context mContext;

	private DownloadManager.Request mRequest;

	private String mUri;

	private String mCookies;

	private String mUserAgent;

	public FetchUrlMimeType(Context context, DownloadManager.Request request, String uri,
			String cookies, String userAgent) {
		mContext = context.getApplicationContext();
		mRequest = request;
		mUri = uri;
		mCookies = cookies;
		mUserAgent = userAgent;
	}

	@Override
	public void run() {
		// User agent is likely to be null, though the AndroidHttpClient
		// seems ok with that.
	
		// Start the download
		DownloadManager manager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
		manager.enqueue(mRequest);
	}
}
