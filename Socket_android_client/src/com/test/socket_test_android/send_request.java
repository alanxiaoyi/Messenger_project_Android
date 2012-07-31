package com.test.socket_test_android;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.util.Log;
 
public class send_request {
 
    private json_receive json_Receive;
 
    // Testing in localhost using wamp or xampp
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    private static String loginURL = "http://10.0.1.8/php_server/index.php";
    private static String registerURL = "http://10.0.1.8/php_server/index.php";
 
    private static String login_tag = "login";
    private static String register_tag = "register";
    private static String addfriend_tag = "addfriend";
    private static String showfriends_tag = "showfriends";
    // constructor
    public send_request(){
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
 
        // getting JSON Object
        JSONObject json = json_Receive.getJSONFromUrl(registerURL, params);
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
        JSONObject json = json_Receive.getJSONFromUrl(loginURL, params);
        return json;
    }
 
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
