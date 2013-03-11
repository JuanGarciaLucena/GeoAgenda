package com.geoagenda4;

import com.geoagenda4.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import fragments.CalendarDialogFragment;
import fragments.TimeDialogFragment;

public class NewEventActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_event, menu);
		return true;
	}
	
	public void onClickButtonDate(View view){
		showCalendarDialogFragment();
		/*Intent intent = new Intent(view.getContext(), CalendarActivity.class);
		startActivityForResult(intent, 1);*/
	}
	
	public void onClickButtonTime(View view){
		showTimeDialogFragment();
		/*Intent intent = new Intent(view.getContext(), TimePickerActivity.class);
		startActivityForResult(intent, 2);*/
	}
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == 1){
			if(resultCode == RESULT_OK){
				TextView textView = (TextView)findViewById(R.id.idTextViewDate);
				String date = data.getData().toString();
				textView.setText(date);
			}
		}else if(requestCode == 2){
			if(resultCode == RESULT_OK){
				TextView textView = (TextView)findViewById(R.id.idTextViewTime);
				String time = data.getData().toString();
				textView.setText(time);
				
			}
		}
	}
	
	public void showCalendarDialogFragment(){
		DialogFragment calendarFragment = new CalendarDialogFragment(this);
		calendarFragment.show(getFragmentManager(), "CalendarFragment");
	}
	
	public void showTimeDialogFragment(){
		DialogFragment timeFragment = new TimeDialogFragment();
		timeFragment.show(getFragmentManager(), "TimeFragment");
	}

}
