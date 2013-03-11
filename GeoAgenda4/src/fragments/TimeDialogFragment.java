package fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.geoagenda4.R;

public class TimeDialogFragment extends DialogFragment{
	
	public Dialog onCreateDialog(Bundle savedInstanceState){
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		final View v = inflater.inflate(R.layout.fragment_time, null);
		
		return new AlertDialog.Builder(getActivity())
			.setTitle(getString(R.string.time_fragment_title))
			.setView(v)
			.setPositiveButton(R.string.gA_ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//TODO
				}
			})

			.setNegativeButton(R.string.gA_cancel, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//TODO
				}
			})
			.create();
		
	}
	
	
}

