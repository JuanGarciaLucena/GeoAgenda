package com.geoagenda4;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.CalendarView;

public class CalendarActivity extends Activity {
	
	CalendarView calendarView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_calendar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_test, menu);
		return true;
	}
	
	public void onClick(View view){
		calendarView = (CalendarView)findViewById(R.id.calendarView);
		long dateLong = calendarView.getDate();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
		String dateString = formatter.format(new Date(dateLong));
		
		Intent i = new Intent();
		
		i.setData(Uri.parse(dateString));
		setResult(RESULT_OK, i);
		
		finish();
	}

}
