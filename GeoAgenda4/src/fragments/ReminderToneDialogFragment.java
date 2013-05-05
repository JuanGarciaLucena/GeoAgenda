package fragments;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import utils.RingtoneCollection;

import com.geoagenda4.R;

import fragments.CalendarDialogFragment.DateInterface;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class ReminderToneDialogFragment extends DialogFragment{

	private TextView textView;
	private ListView listView;
	private Ringtone r;
	private Ringtone oldRingtone;
	private Uri uri;
	public Context context;
	public ReminderInterface mCallback;
	
	public interface ReminderInterface{
		public void returnReminder(Uri uri);
	}
	
	public void onAttach(Activity activity){
		super.onAttach(activity);
		context = getActivity();
		mCallback = (ReminderInterface)context;
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState){
		
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		final View v = inflater.inflate(R.layout.fragment_reminder, null);
		
		final RingtoneManager rM = new RingtoneManager(getActivity());
		RingtoneCollection rC = new RingtoneCollection(getActivity(), rM);
		List<Ringtone> ringtones = new ArrayList<Ringtone>();
		List<String> ringtonesTitle = new ArrayList<String>();
		
		//Creating a ringtone list from map given by RingtoneCollection
		final Map<Integer, Ringtone> m = rC.getAllRingtones();
		Iterator it = m.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<Integer, Ringtone> e = (Map.Entry<Integer, Ringtone>)it.next();
			ringtones.add(e.getValue());
			ringtonesTitle.add(e.getValue().getTitle(getActivity()));
		}
		
		listView = (ListView)v.findViewById(R.id.idRingtonesListView);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_single_choice, ringtonesTitle);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setAdapter(adapter);
		
		
		//List onClick statement
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if(oldRingtone != null){
					oldRingtone.stop();
				}
				rM.stopPreviousRingtone();
				r = m.get(position);
				uri = rM.getRingtoneUri(position);
				
				Log.d("LOG", "RINGTONE NAME: " + r.getTitle(getActivity()) + "\nRINGTONE URI: " + rM.getRingtoneUri(position).toString());
				
				r.play();
				oldRingtone = r;
				
			}
		});
		
		//Return statement
		return new AlertDialog.Builder(getActivity())
		.setTitle(getString(R.string.time_fragment_title))
		.setView(v)
		.setPositiveButton(R.string.app_ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(r != null){
						Button b = (Button)getActivity().findViewById(R.id.ButtonRingtone);
						b.setText(r.getTitle(getActivity()));
						mCallback.returnReminder(uri);
						r.stop();
					}
				}
			})

			.setNegativeButton(R.string.app_cancel, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					if(r != null){
						r.stop();
					}
					dialog.cancel();
				}
			})
		.create();
	}
	
}
