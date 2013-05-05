package fragments;


import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

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
import android.widget.TimePicker;

import com.geoagenda4.R;

public class TimeDialogFragment extends DialogFragment{
	
	Button timeButton;
	TimePicker timePicker;
	TimeInterface mCallback;
	public Context context;
	
	public interface TimeInterface{
		public void returnTime(Time t);
	}
	
	public void onAttach (Activity activity){
		super.onAttach(activity);
		context = getActivity();
		mCallback = (TimeInterface)context;
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState){
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		final View v = inflater.inflate(R.layout.fragment_time, null);
		
		return new AlertDialog.Builder(getActivity())
			.setTitle(getString(R.string.time_fragment_title))
			.setView(v)
			.setPositiveButton(R.string.app_ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					timePicker = (TimePicker)v.findViewById(R.id.timePicker1);
					timeButton = (Button)getActivity().findViewById(R.id.ButtonHour);
					timePicker.clearFocus();
					Integer h = timePicker.getCurrentHour();
					Integer m = timePicker.getCurrentMinute();
					String timeString = h.toString() + ":" + m.toString() + ":" + "00";					
					Time t = Time.valueOf(timeString);
					mCallback.returnTime(t);
					timeButton.setText(h.toString() + ":" + m.toString());
					
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

