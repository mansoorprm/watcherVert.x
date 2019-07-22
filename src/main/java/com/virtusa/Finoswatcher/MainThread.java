package com.virtusa.Finoswatcher;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class MainThread implements Runnable { 
	  
    // to stop the thread 
    public boolean exit; 
  
    private String name; 
    Thread t; 
  
    MainThread(String threadname) 
    { 
        name = threadname; 
        t = new Thread(this, name); 
        System.out.println("New thread: " + t); 
        exit = false; 
        t.start(); // Starting the thread 
    } 
  
    // execution of thread starts from run() method 
    public void run() 
    { 
        int i = 0; 
        int delay=1;
        
        FirstThread firstThread = new FirstThread();
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(firstThread, 0, delay, TimeUnit.SECONDS);
        
        while (!exit) { 
           //System.out.println(name + ": " + i); 
            i++; 
             
            try { 
                Thread.sleep(100); 
            } 
            catch (InterruptedException e) { 
                System.out.println("Caught:" + e); 
            } 
        } 
        
        if (exit) {
        	try {
				Thread.sleep(500);
				service.shutdown();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
      
        
        System.out.println(name + " Stopped."); 
    } 
  
    // for stopping the thread 
    public void stop() 
    { 
        exit = true; 
    } 
} 
  

