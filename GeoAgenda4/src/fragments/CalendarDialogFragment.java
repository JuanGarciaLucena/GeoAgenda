package fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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

public class CalendarDialogFragment extends DialogFragment{
	
	private DatePicker datePicker;
	private Button buttonDate;
	private DateInterface mCallback;
	public Context context;
	
	public interface DateInterface{
		public void returnDate(Date date);
	}
	
	public void onAttach(Activity activity){
		super.onAttach(activity);
		context = getActivity();
		mCallback = (DateInterface)context;
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState){
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		final View v = inflater.inflate(R.layout.fragment_date, null);
		datePicker = (DatePicker)v.findViewById(R.id.datePicker1);
		datePicker.setCalendarViewShown(false);
		
		return new AlertDialog.Builder(getActivity())
			.setTitle(getString(R.string.calendar_fragment_date))
			.setView(v)
			.setPositiveButton(R.string.app_ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					buttonDate = (Button)getActivity().findViewById(R.id.idButtonDate);
					String s = datePicker.getDayOfMonth() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getYear();
					buttonDate.setText(s);
					try {
						String dateString = datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth();
						Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
						mCallback.returnDate(date);
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
