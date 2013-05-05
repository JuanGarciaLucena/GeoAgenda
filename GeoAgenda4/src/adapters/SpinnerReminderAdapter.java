package adapters;

import java.util.List;

import utils.Event;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geoagenda4.R;

public class SpinnerReminderAdapter extends ArrayAdapter<String>{
	
	private int resource;
	private LayoutInflater inflater;
	private Context context;
	
	public SpinnerReminderAdapter(Context ctx, int resourceId, String[] objects){
		super(ctx, resourceId, objects);
		resource = resourceId;
		inflater = LayoutInflater.from(ctx);
		context = ctx;
	}
	
	
	/*public View getView(int position, View convertView, ViewGroup parent){
		
		/* create a new view of my layout and inflate it in the row */
		//convertView = (RelativeLayout)inflater.inflate(resource, null);
		
		/* take the TextView from layout and set the event's name */
		/*TextView label = (TextView)convertView.findViewById(R.id.textspinner1);
		label.setText(objects[position]);
		return convertView;
	}*/
}
