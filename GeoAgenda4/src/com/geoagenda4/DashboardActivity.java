package com.geoagenda4;


import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class DashboardActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.dashboard_title);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_dashboard, menu);
		return true;
	}
	
	public void onClickEvents(View view){
		Intent intent = new Intent(view.getContext(), EventsListActivity.class);
		startActivity(intent);
	}
	
	public void onClickNewEvent(View view){
		Intent intent = new Intent(view.getContext(), NewEventActivity.class);
		startActivity(intent);
	}
	
	public void onClickSettings(View view){
		Toast.makeText(getApplicationContext(), "Configuraci—n", Toast.LENGTH_SHORT).show();
	}
	
	public void onClickAbout(View view){
		Intent intent = new Intent(view.getContext(), AboutActivity.class);
		startActivity(intent);
	}

}
	