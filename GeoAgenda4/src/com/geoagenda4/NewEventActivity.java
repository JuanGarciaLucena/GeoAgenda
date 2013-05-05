package com.geoagenda4;

import java.sql.Time;
import java.util.Date;

import utils.Event;
import utils.Utils;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import fragments.CalendarDialogFragment;
import fragments.ReminderCalendarDialogFragment;
import fragments.ReminderTimeDialogFragment;
import fragments.ReminderToneDialogFragment;
import fragments.TimeDialogFragment;

public class NewEventActivity extends Activity implements TimeDialogFragment.TimeInterface, 
CalendarDialogFragment.DateInterface,
ReminderCalendarDialogFragment.ReminderDateInterface,
ReminderToneDialogFragment.ReminderInterface,
ReminderTimeDialogFragment.ReminderTimeInterface{
	
	private TextView eventTitle;
	private TextView eventAddress;
	private TextView eventCity;
	public Date eventDate;
	private Time eventHour;
	private Date reminderEventDate;
	private Time reminderEventTime;
	private String uri;
	
	//onCreate -> se llama cada vez que llamamos a la activity
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event);
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    eventTitle = (TextView)findViewById(R.id.idEventName);
	    eventAddress = (TextView)findViewById(R.id.idEventAddress);
	    eventCity = (TextView)findViewById(R.id.idEventCity);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
	        	}
	        	
	        	if(!validateEmptyDateTime){
	        		Log.d("validateLOG", "CAMPOS DE FECHA VACIOS");
	        	}
	        	
	        	
	        	if(!validateDate){
	        		Log.d("validateLOG", "FECHA ANTERIOR A LA ACTUAL");
	        	}
	        	
	        	if(!validateReminderDateCurrentDate){
	        		Log.d("validateLOG", "FECHA RECORDATORIO ANTERIOR A LA ACTUAL");
	        	}
	        	
	        	if(!validateReminderDate){
	        		Log.d("validateLOG", "FECHA RECORDATORIO POSTERIOR FECHA EVENTO");
	        	}
	        	
	        	if(!validateTime){
	        		Log.d("validateLOG", "HORA RECORDATORIO POSTERIOR O MISMA HORA EVENTO");
	        	}
	        	
	        	if(!validateReminderTone){
	        		Log.d("validateLOG", "DEBE SELECCIONAR UN TONO DE ALERTA");
	        	}
	        	
	        	if(validateInfo && validateDate && validateReminderDateCurrentDate && validateEmptyDateTime && validateReminderDate && validateTime && validateReminderTone){
	        		Log.d("validateLOG", "EVENTO VALIDADO");
	        		Event event = new Event(eventTitle.getText().toString(), eventAddress.getText().toString(), eventCity.getText().toString(), eventDate, eventHour, reminderEventDate, reminderEventTime, uri);
	        	}
	        	
	        	
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	
	
	
	

}
