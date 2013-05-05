package utils;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;

public class RingtoneCollection{
	
	private Activity activity;
	private RingtoneManager rM;
	public Cursor alarmCursor;
	
	public RingtoneCollection(Activity activity, RingtoneManager rM){
		this.activity = activity;
		this.rM = rM;
	}
	
	public Map<Integer,Ringtone> getAllRingtones(){
		Map<Integer,Ringtone> m = new HashMap<Integer, Ringtone>();
		rM.setType(RingtoneManager.TYPE_ALARM);
		alarmCursor = rM.getCursor();
		int alarmsCount = alarmCursor.getCount();
		if(alarmsCount == 0 && !alarmCursor.moveToFirst()){
			return null;
		}
		
		while(!alarmCursor.isAfterLast() && alarmCursor.moveToNext()){
			int currentPosition = alarmCursor.getPosition();
			Ringtone r = rM.getRingtone(currentPosition);
			m.put(currentPosition, r);
		}
		//TODO cerrar cursor
		//alarmCursor.close();
		return m;
	}
}
