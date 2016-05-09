package com.jerry.bean.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date {
	private int year;
	private int month;
	private int day;
	private java.util.Date date;
	
	public Date(java.util.Date d){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH)+1;
		day = c.get(Calendar.DAY_OF_MONTH);
		this.date = d;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}
	
	public String getDate(){
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
}
