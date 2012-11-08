package com.test.socket_test_android;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;
 
public class send_request {
 
    private json_receive json_Receive;
    private Context context;
    private JSONObject json;
    
    
    // Testing in localhost using wamp or xampp
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    public static String loginURL = "http://10.0.1.8/php_server/index.php";
 //   public static String registerURL = "http://10.0.1.8/php_server/index.php";
 
    public static String login_tag = "login";
    public static String register_tag = "register";
    public static String addfriend_tag = "addfriend";
    public static String showfriends_tag = "showfriends";
    public static String deletefriends_tag = "deletefriend";
    
 /*   Handler myhandler=new Handler()
	{
		@Override
		public void handleMessage(Message msg) 
		{
			json=(JSONObject)msg.obj;
			if(progressDialog!=null)
				popUp.dismiss();
		}
	};// handler*/
    
    // constructor
    public send_request(Context c){
    	      
    	this.context=c;
    	this.json_Receive = new json_receive();  //try
    }
 
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject loginUser(String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        
    //    CharSequence text = "Hello toast!";
    //    int duration = Toast.LENGTH_SHORT;

    //     Toast toast = Toast.makeText(context, text, duration);
    //    toast.show();
        JSONObject json = json_Receive.getJSONFromUrl(loginURL, params);

    
        return json;
    }
 
    /**
     * function make Login Request
     * @param name
     * @param email
     * @param password
     * */
    public JSONObject registerUser(String name, String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
  //      progressDialog = ProgressDialog.show(context, "Loading...", "Please wait...", true, false); 
 //       Mythread m=new Mythread(json,params );
 //       new Thread(m).start(); 
        // getting JSON Object
        JSONObject json = json_Receive.getJSONFromUrl(loginURL, params);
        // return json
        return json;
    }
    /**
     * function to add friend
     * @param myemail
     * @param friendemail
     * */
    public JSONObject addFriend(String myemail, String friendemail){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", addfriend_tag));
        params.add(new BasicNameValuePair("myemail", myemail));
        params.add(new BasicNameValuePair("friendemail", friendemail));
        
  //      progressDialog = ProgressDialog.show(context, "Loading...", "Please wait...", true, false); 
  //      Mythread m=new Mythread(json,params );
   //     new Thread(m).start(); 
        JSONObject json = json_Receive.getJSONFromUrl(loginURL, params);
        return json;
    }
    /**
     * function to show friends
     * @param myemail
     * @param friendemail
     * */
    public JSONObject showFriends(String myemail){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", showfriends_tag));
        params.add(new BasicNameValuePair("myemail", myemail));
        
        
        
  //      progressDialog = ProgressDialog.show(context, "Loading...", "Please wait...", true, false); 
  //      Mythread m=new Mythread(json,params );
  //      new Thread(m).start(); 
        JSONObject json = json_Receive.getJSONFromUrl(loginURL, params);
        return json;
    }
    
    /**
     * function to show friends
     * @param myemail
     * @param friendemail
     * */
    public JSONObject deleteFriends(String myemail, String friendemail){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", deletefriends_tag));
        params.add(new BasicNameValuePair("myemail", myemail));
        params.add(new BasicNameValuePair("friendemail", friendemail));
        
  //      progressDialog = ProgressDialog.show(context, "Loading...", "Please wait...", true, false); 
 //       Mythread m=new Mythread(json,params );
  //      new Thread(m).start(); 
        JSONObject json = json_Receive.getJSONFromUrl(loginURL, params);
        return json;
    }   
    
    
    
/*    public class Mythread implements Runnable {
    	JSONObject json_t;
    	List<NameValuePair> params;
    	Message msg;

	      public Mythread( JSONObject j,  List<NameValuePair> p) {
	    	  msg=new Message();	
	    	  this.json_t=j;
	           this.params=p;
	       }
	      
	       public void run(){
	    	   
	    	   json_t = json_Receive.getJSONFromUrl(loginURL, params);
	    	   msg.obj=json_t;
	    	   myhandler.sendMessage(msg); 
	       }
       
    }*/
  
    
    
    
    /**
     * Function get Login status
     * */
    /*
    public boolean isUserLoggedIn(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }*/
 
    /**
     * Function to logout user
     * Reset Database
     * */
    /*
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }*/
 
}



