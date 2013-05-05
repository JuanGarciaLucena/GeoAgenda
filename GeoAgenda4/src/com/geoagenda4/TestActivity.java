package com.geoagenda4;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class TestActivity extends android.support.v4.app.FragmentActivity {
	
	private GoogleMap map = null;
	private int vista = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		
		//map = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_test, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){	
		switch(item.getItemId()){
			
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	private void alternMapView(){
		vista = (vista + 1) % 4;
		
		switch(vista){
		case 0:
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case 1:
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;
		case 2:
			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;
		case 3:
			map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;
		}
	}

}
