package utils;

public class Event {
	
	private String title;
	private String subtitle;
	

	public Event(String t, String st){
		title = t;
		subtitle = st;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	
	
}
