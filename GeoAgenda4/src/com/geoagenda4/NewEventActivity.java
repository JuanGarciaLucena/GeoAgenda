package com.geoagenda4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification.Builder;
import utils.CurrentLocationListener;
import utils.Event;
import utils.EventsSQLiteHelper;
import utils.Utils;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import fragments.CalendarDialogFragment;
import fragments.ReminderCalendarDialogFragment;
import fragments.ReminderTimeDialogFragment;
import fragments.ReminderToneDialogFragment;
import fragments.TimeDialogFragment;
import fragments.ReminderTimeDialogFragment.ReminderTimeInterface;

public class NewEventActivity extends Activity implements TimeDialogFragment.TimeInterface, 
CalendarDialogFragment.DateInterface,
ReminderCalendarDialogFragment.ReminderDateInterface,
ReminderToneDialogFragment.ReminderInterface,
ReminderTimeDialogFragment.ReminderTimeInterface{
	
	private TextView eventTitle;
	private TextView eventAddress;
	private TextView eventCity;
	private Date eventDate;
	private Time eventHour;
	private Date reminderEventDate;
	private Time reminderEventTime;
	private String uri;
	private Context context;
	AlarmManager alarmManager;
	AlarmReceiver receiver;
	InputStream is = null;
	String result = "";
	JSONObject jsonObject = null;
	String routeTime;
	Long routeTimeInMs;
	
	//onCreate -> se llama cada vez que llamamos a la activity
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event);
		receiver = new AlarmReceiver();
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    eventTitle = (TextView)findViewById(R.id.idEventName);
	    eventAddress = (TextView)findViewById(R.id.idEventAddress);
	    eventCity = (TextView)findViewById(R.id.idEventCity);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
		context = this;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_event, menu);
		return true;
	}
	
	
	/*--------------------------------------------------------------------------------
	 * -----------------------------DATE----------------------------------------------
	 */
	public void onClickButtonDate(View view){
		showCalendarDialogFragment();
	}
	
	//Muestra el fragment para seleccionar la fecha del evento
	public void showCalendarDialogFragment(){
		DialogFragment calendarFragment = new CalendarDialogFragment();
		calendarFragment.show(getFragmentManager(), "CalendarFragment");
	}
	
	public void returnDate(Date date) {
		setEventDate(date);
	}
	
	public Date getEventDate(){
		return eventDate;
	}
	
	public void setEventDate(Date eventDate){
		this.eventDate = eventDate;
	}
	
	
	/*--------------------------------------------------------------------------------
	 * -----------------------------TIME----------------------------------------------
	 */
	
	public void onClickButtonTime(View view){
		showTimeDialogFragment();
	}
	
	public void showTimeDialogFragment(){
		DialogFragment timeFragment = new TimeDialogFragment();
		timeFragment.show(getFragmentManager(), "TimeFragment");
	}
	
	public void returnTime(Time hour) {
		setEventHour(hour);
		
	}
	
	public Time getEventHour() {
		return eventHour;
	}

	public void setEventHour(Time eventHour) {
		this.eventHour = eventHour;
	}
	
	
	/*--------------------------------------------------------------------------------
	 * -----------------------------REMINDER DATE-------------------------------------
	 */
	
	
	public void onClickReminderButtonDate(View view){
		showReminderCalendarDialogFragment();
	}
	
	public void showReminderCalendarDialogFragment(){
		DialogFragment reminderCalendarDialogFragment = new ReminderCalendarDialogFragment();
		reminderCalendarDialogFragment.show(getFragmentManager(), "ReminderDate");
	}
	
	public void returnReminderDate(Date date){
		setReminderEventDate(date);
	}
	
	public Date getReminderEventDate(){
		return reminderEventDate;
	}
	
	public void setReminderEventDate(Date date){
		reminderEventDate = date;
	}
	
	
	/*--------------------------------------------------------------------------------
	 * -----------------------------REMINDER TIME-------------------------------------
	 */
	
	public void onClickReminderButtonTime(View view){
		showReminderTimeDialogFragment();
	}
	
	public void showReminderTimeDialogFragment(){
		DialogFragment reminderTimeDialogFragment = new ReminderTimeDialogFragment();
		reminderTimeDialogFragment.show(getFragmentManager(), "ReminderTime");
	}
	
	public void returnReminderTime(Time time){
		setReminderEventTime(time);
	}
	
	public Time getReminderEventTime(){
		return reminderEventTime;
	}
	
	public void setReminderEventTime(Time time){
		reminderEventTime = time;
	}
	
	
	/*--------------------------------------------------------------------------------
	 * -----------------------------REMINDER TONE-------------------------------------
	 */
	
	public void onClickRingtone(View view){
		showReminderToneDialogFragment();
	}
	
	public void showReminderToneDialogFragment(){
		DialogFragment reminderToneFragment = new ReminderToneDialogFragment();
		reminderToneFragment.show(getFragmentManager(), "ReminderFragment");
	}
	
	public void returnReminder(Uri uri) {
		setUri(uri.toString());
	}
	
	public String getUri(){
		return uri;
	}
	
	public void setUri(String uri){
		this.uri = uri;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	            Intent intent = new Intent(this, DashboardActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        case R.id.new_event_menu_save:
	        	
	        	boolean validateInfo = Utils.validateEventInfo(eventTitle.getText().toString(), eventAddress.getText().toString(), eventCity.getText().toString());
	        	boolean validateEmptyDateTime = Utils.validateEmptyDateTime(eventDate, reminderEventDate, eventHour, reminderEventTime);
	        	boolean validateDate = Utils.validateEventDate(eventDate);
	        	boolean validateReminderDateCurrentDate = Utils.validateEventReminderDate(reminderEventDate);
	        	boolean validateReminderDate = Utils.validateReminderDate(eventDate, reminderEventDate);
	        	boolean validateTime = Utils.validateTime(eventDate, reminderEventDate, eventHour, reminderEventTime);
	        	boolean validateReminderTone = Utils.validateReminderTone(uri);
	        	
	        	if(!validateInfo){
	        		Log.d("validateLOG", "INFO NO VALIDADA");
	        		AlertDialog.Builder builder = new AlertDialog.Builder(context);
	        		builder.setTitle(R.string.new_event_alert_title);
	        	    builder.setMessage(R.string.new_event_alert_info);
	        	    builder.setPositiveButton(R.string.app_ok, new OnClickListener() {
	        	        public void onClick(DialogInterface dialog, int which) {
	        	            dialog.cancel();
	        	        }
	        	    });	        	    
	        	    builder.show();
	        	}
	        	
	        	if(!validateEmptyDateTime){
	        		Log.d("validateLOG", "CAMPOS DE FECHA VACIOS");
	        		AlertDialog.Builder builder = new AlertDialog.Builder(context);
	        		builder.setTitle(R.string.new_event_alert_title);
	        	    builder.setMessage(R.string.new_event_alert_empty_date_time);
	        	    builder.setPositiveButton(R.string.app_ok, new OnClickListener() {
	        	        public void onClick(DialogInterface dialog, int which) {
	        	            dialog.cancel();
	        	        }
	        	    });
	        	    builder.show();
	        	}
	        	
	        	
	        	if(!validateDate){
	        		Log.d("validateLOG", "FECHA ANTERIOR A LA ACTUAL");
	        		AlertDialog.Builder builder = new AlertDialog.Builder(context);
	        		builder.setTitle(R.string.new_event_alert_title);
	        	    builder.setMessage(R.string.new_event_alert_event_previous);
	        	    builder.setPositiveButton(R.string.app_ok, new OnClickListener() {
	        	        public void onClick(DialogInterface dialog, int which) {
	        	            dialog.cancel();
	        	        }
	        	    });
	        	    builder.show();
	        	}
	        	
	        	if(!validateReminderDateCurrentDate){
	        		Log.d("validateLOG", "FECHA RECORDATORIO ANTERIOR A LA ACTUAL");
	        		AlertDialog.Builder builder = new AlertDialog.Builder(context);
	        		builder.setTitle(R.string.new_event_alert_title);
	        	    builder.setMessage(R.string.new_event_alert_reminder_previous);
	        	    builder.setPositiveButton(R.string.app_ok, new OnClickListener() {
	        	        public void onClick(DialogInterface dialog, int which) {
	        	            dialog.cancel();
	        	        }
	        	    });
	        	    builder.show();
	        	}
	        	
	        	if(!validateReminderDate){
	        		Log.d("validateLOG", "FECHA RECORDATORIO POSTERIOR FECHA EVENTO");
	        		AlertDialog.Builder builder = new AlertDialog.Builder(context);
	        		builder.setTitle(R.string.new_event_alert_title);
	        	    builder.setMessage(R.string.new_event_alert_reminder_previous_event);
	        	    builder.setPositiveButton(R.string.app_ok, new OnClickListener() {
	        	        public void onClick(DialogInterface dialog, int which) {
	        	            dialog.cancel();
	        	        }
	        	    });
	        	    builder.show();
	        	}
	        	
	        	if(!validateTime){
	        		Log.d("validateLOG", "HORA RECORDATORIO POSTERIOR O MISMA HORA EVENTO");
	        		AlertDialog.Builder builder = new AlertDialog.Builder(context);
	        		builder.setTitle(R.string.new_event_alert_title);
	        	    builder.setMessage(R.string.new_event_alert_time_reminder_previous);
	        	    builder.setPositiveButton(R.string.app_ok, new OnClickListener() {
	        	        public void onClick(DialogInterface dialog, int which) {
	        	            dialog.cancel();
	        	        }
	        	    });
	        	    builder.show();
	        	}
	        	
	        	if(!validateReminderTone){
	        		Log.d("validateLOG", "DEBE SELECCIONAR UN TONO DE ALERTA");
	        		AlertDialog.Builder builder = new AlertDialog.Builder(context);
	        		builder.setTitle(R.string.new_event_alert_title);
	        	    builder.setMessage(R.string.new_event_alert_tone_reminder);
	        	    builder.setPositiveButton(R.string.app_ok, new OnClickListener() {
	        	        public void onClick(DialogInterface dialog, int which) {
	        	            dialog.cancel();
	        	        }
	        	    });
	        	    builder.show();
	        	}
	        	
	        	if(validateInfo && validateDate && validateReminderDateCurrentDate && validateEmptyDateTime && validateReminderDate && validateTime && validateReminderTone){
	        		
	        		//Creamos la alerta
	        		
	        		Long d = reminderEventDate.getTime();
	        		Long t = reminderEventTime.getTime();
	        		Long finalDate = d + t;
	        		Date showDate = new Date();
	        		showDate.setTime(finalDate);
	        		
	        		Intent in = new Intent(NewEventActivity.this, AlarmReceiver.class);
	        		Bundle b = new Bundle();
	        		b.putString("eventTitle", eventTitle.getText().toString());
	        		b.putString("notificationTone", uri);
	        		in.putExtras(b);
	        		PendingIntent appIntent = PendingIntent.getBroadcast(NewEventActivity.this, 0, in, 0);
	        		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
	        		am.set(AlarmManager.RTC, finalDate, appIntent);
	        		
	        		
	        		//Alerta si no llegamos al destino a partir de la hora del recordatorio
	        		//Obtenemos la ubicaci—n actual
	        		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
					CurrentLocationListener locationListener = new CurrentLocationListener();
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
					
					Criteria criteria = new Criteria();
					String provider = locationManager.getBestProvider(criteria, true);
					
					Location currentLocation = locationManager.getLastKnownLocation(provider);
					if(currentLocation!=null){
		                locationListener.onLocationChanged(currentLocation);
		            }
		            locationManager.requestLocationUpdates(provider, (long)20000, (float)0, locationListener);
		        
		            
					Double latitude = currentLocation.getLatitude();
					Double longitude = currentLocation.getLongitude();
					
					//Obtenemos la ubicaci—n del evento
					String completeAddress = eventAddress.getText().toString() + ", " + eventCity.getText().toString();
					List<Address> addresses = Utils.getLocationInfo(completeAddress, this);
					
					Double latitude_dest = addresses.get(0).getLatitude();
					Double longitude_dest = addresses.get(0).getLongitude();
					
					//Obtenemos la info de la ruta
					
					Location locationOrigin = new Location("origin");
					locationOrigin.setLatitude(latitude);
					locationOrigin.setLongitude(longitude);
					
					
					Location locationDestination = new Location("destination");
					locationDestination.setLatitude(latitude_dest);
					locationDestination.setLongitude(longitude_dest);
					
					String origin = locationOrigin.getLatitude() + "," + locationOrigin.getLongitude();
					String destination = locationDestination.getLatitude() + "," + locationDestination.getLongitude();
					
					//Montamos la url
					String url = "http://maps.googleapis.com/maps/api/directions/json?origin=" + origin + "&destination=" + destination + "&region=es&sensor=false";
					new Connection().execute(url);
					while(true){
						if(routeTime != null){
							routeTimeInMs = new Long(routeTime) * 1000;
							break;
						}
					}
					Long l1 = eventDate.getTime();
					Long l2 = eventHour.getTime();
					Long eventMs = l1 + l2;
					Long rememberEventMs = finalDate;
					Long timeDiference = eventMs - rememberEventMs + 3600000;
					System.out.println("dsadasdasa" + routeTimeInMs.toString());

					//timeDiference es el tiempo que tenemos para llegar al evento. Si ese tiempo es menor al tiempo del trayecto -> alerta
				if (timeDiference < routeTimeInMs) {
					Long delayAlertTime = rememberEventMs - 3600000;
					Bundle bundle = new Bundle();
	        		bundle.putString("eventTitle", eventTitle.getText().toString());
	        		bundle.putString("notificationTone", uri);
					Intent intentDelay = new Intent(getBaseContext(), AlarmRouteReceiver.class);
					intentDelay.putExtras(bundle);
					PendingIntent pendingIntentDelay = PendingIntent.getBroadcast(getBaseContext(), 0, intentDelay, 0);
					AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
					alarmManager.set(AlarmManager.RTC, delayAlertTime, pendingIntentDelay);
				}
	        		
	        		//Guardamos el evento en la base de datos
	        		Log.d("validateLOG", "EVENTO VALIDADO");
	        		Event event = new Event(eventTitle.getText().toString(), eventAddress.getText().toString(), eventCity.getText().toString(), eventDate, eventHour, reminderEventDate, reminderEventTime, uri);
	        		EventsSQLiteHelper eventsDB = new EventsSQLiteHelper(this, "eventsDB", null, 1);
	        		SQLiteDatabase db = eventsDB.getWritableDatabase();
	        		
	        		if(db != null){
	        			db.execSQL("INSERT INTO Events(id, title, address, city, date, time, reminderDate, reminderTime, alert)" + 
	        		"VALUES (" + event.hashCode() + ", '" + eventTitle.getText().toString() +"', '" + eventAddress.getText().toString() +"', '" + eventCity.getText().toString() +"', " +
	        				"'" + eventDate.toString() +"', '" + eventHour.toString() +"', '" + reminderEventDate.toString() +"', '" + reminderEventTime.toString() +"', '" + uri + "')");
	        		}
	        		
	        		db.close();
	        		
	        		Toast toast = Toast.makeText(context, R.string.new_event_alert_event_saved, Toast.LENGTH_SHORT);
	        		toast.show();
	        		
	        	    Intent i = new Intent(this, DashboardActivity.class);
	        	    startActivity(i);
	        	}
	        	
	        	
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
	//Clase privada para conocer el tiempo que se tarda en llegar de un lugar a otro
	
	private class Connection extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
        	System.out.println(params.length);
        	String url = params[0];
            connect(url);
            return null;
        }
        
        private void connect(String url) {
            try {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet(url);
                HttpResponse response = client.execute(request);
                HttpEntity entity = response.getEntity();
    			is = entity.getContent();
    			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);
    			StringBuilder sb = new StringBuilder();
    			String line = null;
    			while ((line = reader.readLine()) != null) {
    				sb.append(line + "\n");
    			}
    			is.close();
    			result = sb.toString();
    			try {
					jsonObject = new JSONObject(result);
					JSONArray routesArray = null;
					JSONArray legsArray = null;
					JSONObject duration = null;
					
					routesArray = jsonObject.getJSONArray("routes");
					JSONObject routes = routesArray.getJSONObject(0);
					legsArray = routes.getJSONArray("legs");
					JSONObject legs = legsArray.getJSONObject(0);
					duration = legs.getJSONObject("duration");
					String tiempo = duration.getString("value");
					routeTime = tiempo;
					Log.d("HTTPCLIENT", tiempo);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
            } catch (ClientProtocolException e) {
                Log.d("HTTPCLIENT", e.getLocalizedMessage());
            } catch (IOException e) {
                Log.d("HTTPCLIENT", e.getLocalizedMessage());
            }
        }
        
        public int paco(){
        	return 7;
        }
    }
}
