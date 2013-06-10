package utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class EventsSQLiteHelper extends SQLiteOpenHelper{
	
	String sqlCreate = "CREATE TABLE IF NOT EXISTS Events (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, address TEXT, city TEXT, date TEXT, time TEXT, reminderDate TEXT, reminderTime TEXT, alert TEXT)";
	
	public EventsSQLiteHelper (Context context, String name, CursorFactory factory, int version){
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sqlCreate);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS Events");
		db.execSQL(sqlCreate);
	}

}
