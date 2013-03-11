package com.geoagenda4;

import com.geoagenda4.R;

import utils.Time;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.TimePicker;

public class TimePickerActivity extends Activity {
	
	TimePicker timePicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_time_picker);
	}


	
	public void onClick(View view){
		timePicker = (TimePicker)findViewById(R.id.timePicker1);
		
		timePicker.clearFocus();
		int h = timePicker.getCurrentHour();
		int m = timePicker.getCurrentMinute();
		
		Time t = new Time(h,m);
		
		String s = t.toString();
		
		Intent i = new Intent();
		i.setData(Uri.parse(s));
		setResult(RESULT_OK, i);
		
		finish();
	}

}
