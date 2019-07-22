package com.virtusa.Finoswatcher;

import java.util.Date;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class serverMonitor extends AbstractVerticle {
	
	static Date startTime ;
	static MainThread t1 = null;


	@Override
	public void start(Future<Void> fut) {
	 // Create a router object.
	 Router router = Router.router(vertx);

	 // Bind "/" to our hello message - so we are still compatible.
	 router.route("/").handler(routingContext -> {
	   HttpServerResponse response = routingContext.response();
	   response
	       .putHeader("content-type", "text/html")
	       .end("<h1>finOS Watcher</h1>");
	 });
	 
	 
	 router.get("/status").handler(this::getStatus);
	 router.get("/startWatcher").handler(this::startWatcher);
	 router.get("/stopWatcher").handler(this::stopWatcher);
	 

	 // Create the HTTP server and pass the "accept" method to the request handler.
	 vertx
	     .createHttpServer()
	     .requestHandler(router::accept)
	     .listen(
	         // Retrieve the port from the configuration,
	         // default to 8080.
	         config().getInteger("http.port", 8080),
	         result -> {
	           if (result.succeeded()) {
	             fut.complete();
	           } else {
	             fut.fail(result.cause());
	           }
	         }
	     );
	}
	private void startWatcher(RoutingContext routingContext) {
		  routingContext.response()
		      .putHeader("content-type", "application/json; charset=utf-8")
		      .end(Json.encodePrettily(startServer()));
		     // .end(Json.encodePrettily(status.values()));
	}
	
	private void stopWatcher(RoutingContext routingContext) {
		  routingContext.response()
		      .putHeader("content-type", "application/json; charset=utf-8")
		      .end(Json.encodePrettily(stopServer()));
		     // .end(Json.encodePrettily(status.values()));
	}
	
	
	private void getStatus(RoutingContext routingContext) {
		  routingContext.response()
		      .putHeader("content-type", "application/json; charset=utf-8")
		      .end(Json.encodePrettily(serverStatus()));
		     // .end(Json.encodePrettily(status.values()));
		}
	
	 private static Serverstatus serverStatus() {
		 	Serverstatus st = new Serverstatus();
		 	st.setStartTime("");
	    	st.setUpTime("");
	    	try {
	    		
	    		
	    		if (t1.exit) {
	    			st.setStatus("Not Running");
	    		}else {
		    		long difference = new Date().getTime() - startTime.getTime();
		    		System.out.println(difference/1000);
		    		st.setStatus("Running");
		    		st.setStartTime(startTime.toString());
		    		st.setUpTime(String.valueOf(difference/1000) );
		    	}
	    		
	    		return st;
		    		//return "Server Running since " + difference/1000 + " seconds";
		    }catch(Exception e) {
		    	
		    	st.setStatus("Not Running");
		   		return st;
		    }
	    }
	    
	   
	    
	    private static Serverstatus stopServer() {
	    	Serverstatus st = new Serverstatus();
	    	st.setStartTime("");
	    	st.setUpTime("");
	    	 try { 
	             Thread.sleep(500); 
	             t1.stop(); // stopping thread t1 
	          //   t2.stop(); // stopping thread t2 
	             Thread.sleep(500); 
	         }catch(NullPointerException NE) {
	        	 st.setStatus("Not Running");
		    	return st;
	    	 }catch (Exception e) { 
	             System.out.println("Caught:" + e); 
	         } 
	         System.out.println("Exiting the main Thread"); 
	         
	        
	         st.setStatus("Stopped");
	    	return st;
	    }
	    
	    private static Serverstatus startServer() {
	    	Serverstatus st = new Serverstatus();
	    	st.setStartTime("");
	    	st.setUpTime("");
	    	try {
		    	if (t1.exit) {
		    		startTime = new Date();
		    		t1 = new MainThread("First  thread");   
		    	}
		    	else {
		    		st.setStatus("Already Running");
		             return st;
		    	}
	    	}catch(NullPointerException NPE) {
	    		startTime = new Date();
	    		t1 = new MainThread("First  thread"); 
	    	}
	    	catch(Exception e) {
	    		st.setStatus(e.toString());
	    		System.out.print(e);
	    	}
	    	st.setStatus("Started");
	    	return st;
	    }
}
