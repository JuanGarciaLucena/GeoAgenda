package utils;

public class Time {
	
	private int h;
	private int m;
	
	public Time(int hour, int minute){
		h = hour;
		m = minute;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}
	
	public String toString(){
		String res = "";
		
		if(m < 10){
			res = h + ":" + "0"+ m;
		}else{
			res = h + ":" + m;
		}
		
		return res;
	}

}
