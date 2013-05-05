package fragments;

import java.sql.Time;

import com.geoagenda4.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class ReminderTimeDialogFragment extends DialogFragment{

	private Button reminderTimeButton;
	private TimePicker reminderTimePicker;
	private ReminderTimeInterface mCallback;
	public Context context;
	
	public interface ReminderTimeInterface{
		public void returnReminderTime(Time t);
	}
	
	public void onAttach (Activity activity){
		super.onAttach(activity);
		context = getActivity();
		mCallback = (ReminderTimeInterface)context;
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState){
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		final View v = inflater.inflate(R.layout.fragment_reminder_time, null);
		
		return new AlertDialog.Builder(getActivity())
			.setTitle(getString(R.string.time_fragment_title))
			.setView(v)
			.setPositiveButton(R.string.app_ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					reminderTimePicker = (TimePicker)v.findViewById(R.id.idReminderTimePicker);
					reminderTimeButton = (Button)getActivity().findViewById(R.id.idSelectReminderTime);
					reminderTimePicker.clearFocus();
					Integer h = reminderTimePicker.getCurrentHour();
					Integer m = reminderTimePicker.getCurrentMinute();
					String timeString = h.toString() + ":" + m.toString() + ":" + "00";					
					Time t = Time.valueOf(timeString);
					mCallback.returnReminderTime(t);
					reminderTimeButton.setText(h.toString() + ":" + m.toString());
					
				}
			})

			.setNegativeButton(R.string.app_cancel, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			})
			.create();
	}
}
