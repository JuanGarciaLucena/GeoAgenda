package utils;

import java.util.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
}
