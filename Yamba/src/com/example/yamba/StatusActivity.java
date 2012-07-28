package com.example.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StatusActivity extends Activity implements OnClickListener  {
	static final String TAG = "StatusActivitiy";
	EditText editStatus;
	String statusText;
	Button updateButton;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreated with Bundle: " + savedInstanceState);
        setContentView(R.layout.status);
        
        editStatus = (EditText) findViewById(R.id.editText1);
        updateButton = (Button) findViewById(R.id.button1);
        updateButton.setOnClickListener(this);
        
        
    }
    
    @Override
	public void onClick(View v) {
    	statusText = editStatus.getText().toString();
    	Log.d(TAG, "onClick update: " + statusText);
    	
		new PostToTwitter().execute(statusText);
	   
    }	

    
public class PostToTwitter extends AsyncTask <String, Void, String> {

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			Twitter twitter = new Twitter ("student", "password");
			twitter.setAPIRootUrl("http://yamba.marakana.com/api");
			twitter.setStatus(params[0]);
			return "Sucessfully Posted: " + params[0];
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "Died", e);
			e.printStackTrace();
			return "Failed Posting: " +params[0];
		}
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG).show();
	}
	
 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, UpdaterService.class);
		switch(item.getItemId()) {
		case R.id.item_start_service:
			startService(intent);
			return true;
			
		case R.id.item_stop_service:
			stopService(intent);
			return true;
		default:
			return false;
		}
		
	}
}
