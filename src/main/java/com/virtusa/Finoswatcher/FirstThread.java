package com.virtusa.Finoswatcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FirstThread implements Runnable {

	@Override
	public synchronized void run() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		//System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
		System.out.println("Hello First Thread -> " + dateFormat.format(date));
	}
	

}
