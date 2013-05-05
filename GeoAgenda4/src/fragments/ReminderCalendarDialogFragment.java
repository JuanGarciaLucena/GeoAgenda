package fragments;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.geoagenda4.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class ReminderCalendarDialogFragment extends DialogFragment{

	private DatePicker datePicker;
	private Button reminderButtonDate;
	private ReminderDateInterface mCallback;
	public Context context;
	
	public interface ReminderDateInterface{
		public void returnReminderDate(Date date);
	}
	
	public void onAttach(Activity activity){
		super.onAttach(activity);
		context = getActivity();
		mCallback = (ReminderDateInterface)context;
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState){
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		final View v = inflater.inflate(R.layout.fragment_reminder_date, null);
		datePicker = (DatePicker)v.findViewById(R.id.reminderDatePicker);
		datePicker.setCalendarViewShown(false);
		
		return new AlertDialog.Builder(getActivity())
		.setTitle(getString(R.string.calendar_fragment_date))
		.setView(v)
		.setPositiveButton(R.string.app_ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				reminderButtonDate = (Button)getActivity().findViewById(R.id.idSelectReminderDate);
				String s = datePicker.getDayOfMonth() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getYear();
				reminderButtonDate.setText(s);
				
				try {
					String dateString = datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth();
					Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
					mCallback.returnReminderDate(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
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
