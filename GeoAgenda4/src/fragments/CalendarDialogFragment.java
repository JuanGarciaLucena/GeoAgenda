package fragments;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.geoagenda4.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

public class CalendarDialogFragment extends DialogFragment{
	
	CalendarView calendarView;
	TextView dateTextView;
	
	private final Activity parent;
	

	public CalendarDialogFragment(Activity parent){
		this.parent = parent;
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState){
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		final View v = inflater.inflate(R.layout.fragment_calendar, null);
		
		return new AlertDialog.Builder(getActivity())
			.setTitle(getString(R.string.calendar_fragment_date))
			.setView(v)
			.setPositiveButton(R.string.gA_ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					calendarView = (CalendarView)v.findViewById(R.id.calendarView);
					dateTextView = (TextView)parent.findViewById(R.id.idTextViewDate);
					long dateLong = calendarView.getDate();
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
					String dateString = formatter.format(new Date(dateLong));
					dateTextView.setText(dateString);
				}
			})

			.setNegativeButton(R.string.gA_cancel, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			})
			.create();
		
	}
	
	
}
