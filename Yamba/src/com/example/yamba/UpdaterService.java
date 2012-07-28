package com.example.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {
	static final String TAG = "UpdaterService";
	static final int DELAY = 30; // In seconds
	Twitter twitter;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		twitter = new Twitter ("student", "password");
		twitter.setAPIRootUrl("http://yamba.marakana.com/api");
		Log.d(TAG, "onCreated");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		new Thread() {
			public void run() {
				try {
					List<Status> timeline = twitter.getPublicTimeline();
					for (Status status : timeline) {
						Log.d(TAG, String.format("%s: %s", status.user.name,
								status.text));
					}
					
					Thread.sleep(DELAY*1000);
					
				} catch (TwitterException e) {
					Log.e(TAG, "Failed duw to network error", e);
				} catch (InterruptedException e) {
					Log.e(TAG, "Updater interrupted", e);
				}
			}
		}.start();
		Log.d(TAG, "onStarted");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		Log.d(TAG, "OnDestroyed");
		super.onDestroy();
	}

	

}
