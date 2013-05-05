package utils;

import java.util.Date;
import java.sql.Time;

public class Event {
	
	private String title;
	private String address;
	private String city;
	private Date date;
	private Time time;
	private Date reminderDate;
	private Time reminderTime;
	private String alert;
	
	public Event(String title, String address, String city, Date date, Time time, Date reminderDate, Time reminderTime, String alert){
		this.title = title;
		this.address = address;
		this.city = city;
		this.date = date;
		this.time = time;
		this.reminderDate = reminderDate;
		this.reminderTime = reminderTime;
		this.alert = alert;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Date getReminderDate() {
		return reminderDate;
	}

	public void setReminderDate(Date reminderDate) {
		this.reminderDate = reminderDate;
	}

	public Time getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(Time reminderTime) {
		this.reminderTime = reminderTime;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}
	
	
	
	
	

}
