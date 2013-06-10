package com.geoagenda4;

import java.util.ArrayList;
import java.util.List;

import utils.EventsSQLiteHelper;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EventsListActivity extends Activity {
	
	ListView eventsList;
	Context context;
	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events_list);
		context = this;
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setTitle(R.string.title_events_list);
		eventsList = (ListView)findViewById(R.id.idEventsListView);
		EventsSQLiteHelper eventsDB = new EventsSQLiteHelper(this, "eventsDB", null, 1);
		db = eventsDB.getReadableDatabase();
		String[] campos = new String[] {"title"};
		Cursor c = db.query("Events", campos, null, null, null, null, null);
		final List<String> titles = new ArrayList<String>();
		if (c.moveToFirst()) {
		     do {
		    	 String title = c.getString(0);
		    	 titles.add(title);
		    	 Log.d("databaseLOG", "El titulo es: " + title);
		     } while(c.moveToNext());
		     db.close();  
		}
		
		
		
		final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, titles);
		eventsList.setAdapter(adaptador);
		
		eventsList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> a, View view, int position, long id) {
				String opcionSeleccionada = a.getAdapter().getItem(position).toString();
				Intent i = new Intent(getApplicationContext(), ShowEventActivity.class);
				i.putExtra("rowTitle", opcionSeleccionada);
				startActivity(i);
			}
			
		});
		
		
		//Eliminar una entrada haciendo longClick
		eventsList.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(final AdapterView<?> a, View view, final int position, long id) {
				String opcionSeleccionada = a.getAdapter().getItem(position).toString();
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
        		builder.setTitle(R.string.app_advice);
        	    builder.setMessage(R.string.show_event_delete_confirmation);
        	    final String[] args = new String[]{opcionSeleccionada};
        	    builder.setPositiveButton(R.string.app_ok, new OnClickListener() {        	    	
        	        public void onClick(DialogInterface dialog, int which) {
        	        	db.execSQL("DELETE FROM Events WHERE title=?", args);
        				db.close();
        				Intent i = new Intent(getApplicationContext(), EventsListActivity.class);
        				startActivity(i);
        	        }
        	    });
        	    
        	    builder.setNegativeButton(R.string.app_cancel, new OnClickListener() {        	    	
        	    	public void onClick(DialogInterface dialog, int which) {
        	        	dialog.cancel();
        	        }
        	    });	   
        	    builder.show();
        	    return true;
			}
			
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case android.R.id.home:
				Intent intent = new Intent(this, DashboardActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
}
