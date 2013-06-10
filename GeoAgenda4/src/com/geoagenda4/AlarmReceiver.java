package com.geoagenda4;


import com.geoagenda4.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver{

	private String eventTitle;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent i = new Intent(context,EventsListActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);		
		//Creamos la notificacion	
		Notification notification = createNotification();
		CharSequence cs = intent.getStringExtra("eventTitle");
		CharSequence cs2 = "Evento pr—ximo";
		String notificationTone = intent.getStringExtra("notificationTone");
		Uri u = Uri.parse(notificationTone);
		RingtoneManager rM = new RingtoneManager(context);
		rM.setType(RingtoneManager.TYPE_NOTIFICATION);
		Ringtone r = rM.getRingtone(context, u);
		notification.setLatestEventInfo(context, cs2, cs, pendingIntent);
		//Notificamos
		notification.vibrate = new long[]{100, 100}; 
		notificationManager.notify(0, notification);
		r.play();
		while(true){
			if(!r.isPlaying()){
				r.stop();
				break;
			}
		}
		
		
	}
	
	private Notification createNotification() {
		Notification notification = new Notification();
		
		notification.icon = R.drawable.geoa;
		notification.when = System.currentTimeMillis();
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		
		//notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		
		notification.defaults = Notification.DEFAULT_VIBRATE;
		//notification.defaults |= Notification.DEFAULT_LIGHTS;
		
		
		notification.ledOnMS = 1500;
		notification.ledOffMS = 1500;
		
		return notification;
	}
}
