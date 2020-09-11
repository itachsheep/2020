package com.example.ecliptest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.example.ecliptest.R;
public class MainActivity extends Activity {
	static {
		System.loadLibrary("myaudio");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView tv = (TextView)findViewById(R.id.textView);
		MyDest myDest = new MyDest();
		tv.setText(myDest.stringFromJNI());
	}
	
}
