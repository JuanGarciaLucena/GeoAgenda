package com.geoagenda4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.maps.GeoPoint;
//import utils.CurrentLocationListener;
import utils.EventsSQLiteHelper;
import utils.Utils;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ShowEventActivity extends Activity {
	
	private TextView textViewAddress;
	private TextView textViewCity;
	private TextView textViewDate;
	private TextView textViewReminder;
	private TextView textViewAlertTone;
	
	private String rowTitle;
	private String address;
	private String city;
	private String date;
	private String time;
	private String reminderDate;
	private String reminderTime;
	private String alertTone;
	private Context context;
	
	String completeAddress;
	static List<Address> addresses;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_event);
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    context = this;
	    
	    textViewAddress = (TextView)findViewById(R.id.idShowEventAddress);
	    textViewCity = (TextView)findViewById(R.id.idShowEventCity);
	    textViewDate = (TextView)findViewById(R.id.idShowEventDate);
	    textViewReminder = (TextView)findViewById(R.id.idShowEventReminder);
	    textViewAlertTone = (TextView)findViewById(R.id.idShowEventTone);
	    
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			rowTitle = extras.getString("rowTitle");
		}
		
		
		
		actionBar.setTitle(rowTitle);
		
		EventsSQLiteHelper eventsDB = new EventsSQLiteHelper(this, "eventsDB", null, 1);
		SQLiteDatabase db = eventsDB.getReadableDatabase();
		String[] campos = new String[] {"address", "city", "date", "time", "reminderDate", "reminderTime", "alert"};
		String[] args = new String[] {rowTitle};
		Cursor c = db.query("Events", campos, "title=?", args, null, null, null);
		
		if(c.moveToFirst()){
			do{
				address = c.getString(0);
				Log.d("eventLog", address);
				city = c.getString(1);
				Log.d("eventLog", city);
				date = c.getString(2);
				Log.d("eventLog", date);
				time = c.getString(3);
				Log.d("eventLog", time);
				reminderDate = c.getString(4);
				Log.d("eventLog", reminderDate);
				reminderTime = c.getString(5);
				Log.d("eventLog", reminderTime);
				alertTone = c.getString(6);
				Log.d("eventLog", alertTone);
			}while(c.moveToNext());
		}
		
		db.close();
		
		completeAddress = address + ", " + city;
		addresses = Utils.getLocationInfo(completeAddress, this);
		
		textViewAddress.setText(address);
		textViewCity.setText(city);
		
		//Formateamos date y time para obtener la fecha y la hora en una sola cadena
		String[] rawDate = date.split(" ");
		String[] rawTime = time.split(":");
		String finalDate = rawDate[2] + "/" + rawDate[1] + "/" + rawDate[5] + "  " + rawTime[0] + ":" + rawTime[1]; 
		Log.d("validateLOG", finalDate);
		textViewDate.setText(finalDate);
		
		//Formateamos reminderDate y reminderTime para obtener la fecha y la hora en una sola cadena
		String[] rawReminderDate = reminderDate.split(" ");
		String[] rawReminderTime = reminderTime.split(":");
		String finalReminderDate = rawReminderDate[2] + "/" + rawReminderDate[1] + "/" + rawReminderDate[5] + "  " + rawReminderTime[0] + ":" + rawReminderTime[1]; 
		Log.d("validateLOG", finalReminderDate);
		textViewReminder.setText(finalReminderDate);
		
		
		//Formateamos la cadena alertTone para obtener el nombre de la alerta
		if(alertTone == null){
			textViewAlertTone.setText("NINGUNO");
		}else{
			Uri uri = Uri.parse(alertTone);
			Ringtone ringtone = RingtoneManager.getRingtone(this, uri);
			String alertToneName = ringtone.getTitle(this);
			textViewAlertTone.setText(alertToneName);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_event, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case android.R.id.home:
				Intent intent = new Intent(this, EventsListActivity.class);
	            startActivity(intent);
	            return true;
			case R.id.idDeleteEvent:
				Log.d("validateLOG", "Borrar evento");
				
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
        		builder.setTitle(R.string.app_advice);
        	    builder.setMessage(R.string.show_event_delete_confirmation);
        	    EventsSQLiteHelper eventsDB = new EventsSQLiteHelper(this, "eventsDB", null, 1);
				final SQLiteDatabase db = eventsDB.getReadableDatabase();
				final String[] args = new String[]{rowTitle};
				
        	    builder.setPositiveButton(R.string.app_ok, new OnClickListener() {        	    	
        	        public void onClick(DialogInterface dialog, int which) {
        	        	db.execSQL("DELETE FROM Events WHERE title=?", args);
        				db.close();
        				Intent i = new Intent(getApplicationContext(), EventsListActivity.class);
        				startActivity(i);
        	        }
        	    });
        	    
        	    builder.setNegativeButton(R.string.app_cancel, new OnClickListener() {        	    	
        	    	public void onClick(DialogInterface dialog, int which) {
        	        	dialog.cancel();
        	        }
        	    });	   
        	    builder.show();
        	    return true;
			case R.id.idShowMap:
				
				//implementamos nuestro LocationListener
				LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
				CurrentLocationListener locationListener = new CurrentLocationListener();
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
				
				Criteria criteria = new Criteria();
				String provider = locationManager.getBestProvider(criteria, true);
				
				
				
				Location currentLocation = locationManager.getLastKnownLocation(provider);
				if(currentLocation!=null){
	                locationListener.onLocationChanged(currentLocation);
	            }
				locationManager.requestLocationUpdates(provider, (long)20000, (float)100, locationListener);
				
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	
	
	public void showMap(Location currentLocation){
		
		Double latitude = currentLocation.getLatitude();
		Double longitude = currentLocation.getLongitude();
		
		Double latitude_dest = addresses.get(0).getLatitude();
		Double longitude_dest = addresses.get(0).getLongitude();
		
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr="+latitude+","+longitude+"&daddr="+latitude_dest+","+longitude_dest));
        startActivity(i);
	}
	
	
	private class CurrentLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			showMap(location);
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

	}

}
