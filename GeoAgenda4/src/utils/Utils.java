package utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.maps.GeoPoint;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

public class Utils {
	
	//Validamos t’tulo, direcci—n y ciudad del evento
	public static boolean validateEventInfo(String title, String address, String city) {
		if(title.isEmpty() || address.isEmpty() || city.isEmpty()){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean validateEventDate(Date date){
		Utils utils = new Utils();
		Date currentDate = utils.getCurrentDate();
		if(date != null && date.compareTo(currentDate) < 0){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean validateEventReminderDate(Date reminderDate){
		Utils utils = new Utils();
		Date currentDate = utils.getCurrentDate();
		if(reminderDate != null && reminderDate.compareTo(currentDate) < 0){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean validateEmptyDateTime(Date date, Date reminderDate, Time time, Time reminderTime){
		if(date == null || reminderDate == null || time == null || reminderTime == null){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean validateReminderDate(Date date, Date reminderDate){
		if(date != null && reminderDate != null && reminderDate.compareTo(date) > 0){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean validateTime(Date date, Date reminderDate, Time time, Time reminderTime){
		if(date != null && reminderDate != null && time != null && reminderTime != null && date.compareTo(reminderDate) == 0 && reminderTime.compareTo(time) >= 0){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean validateReminderTone(String uri){
		if(uri != null && uri.isEmpty()){
			return false;
		}else{
			return true;
		}
	}
	
	public Date getCurrentDate(){
		Date res = null;
		try {
    		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar c1 = Calendar.getInstance();
        	String currentDate = sdf.format(c1.getTime());
			res = new SimpleDateFormat("dd-MM-yyyy").parse(currentDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
	public static Dialog createDialog(Context context, String title, String message){
			
	    AlertDialog.Builder builder = new AlertDialog.Builder(context);
	 
	    builder.setTitle(title);
	    builder.setMessage(message);
	    builder.setPositiveButton("OK", new OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	            dialog.cancel();
	        }
	    });
	 
	    return builder.create();
	}
	
	
	public static List<Address> getLocationInfo(String address, Context context){
		Geocoder coder = new Geocoder(context);
		List<Address> addresses = new ArrayList<Address>();
		try {
		    addresses = coder.getFromLocationName(address, 5);
		    Log.d("locations", "Entra en el try");
		    if(address.isEmpty()){
		    	Log.d("locations", "No existen direcciones");
		    	Toast.makeText(context, "No existen direcciones", Toast.LENGTH_SHORT).show();
		    }
		}catch(IOException e){
			Log.d("locations", "PASO4");
			e.printStackTrace();
		}
		return addresses;
	}
}
