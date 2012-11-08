package com.test.socket_test_android;

import android.app.Application;
//for save global variable

public class Myapp extends Application{
	
	private Socket_com mysocket;
	private String youremail;
	private String myemail;
	
	   public Socket_com get_socket() {  
	        return mysocket;  
	    }  
	   
	   public String get_myemail(){
		   return myemail;
		  
	   }
	   
	   public String get_youremail(){
		   return youremail;
		  
	   }
	    public void set_socket(Socket_com socket) {  
	        this.mysocket = socket;  
	    }  
	    
	    public void set_myemail(String email){
	    	
	    	this.myemail=email;
	    }
	    public void set_youremail(String email){
	    	
	    	this.youremail=email;
	    }
	    
}
