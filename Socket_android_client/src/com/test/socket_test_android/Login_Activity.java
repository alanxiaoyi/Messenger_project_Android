package com.test.socket_test_android;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login_Activity extends Activity {
	public final static String EXTRA_MESSAGE = "EMAIL_MESSAGE";
	Button signinbutton;
	Button signupbutton;
	EditText inputEmail;
	EditText inputPassword;
	TextView loginErrorMsg;
	
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";	
	
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        signinbutton=(Button)findViewById(R.id.button1);
        signinbutton=(Button)findViewById(R.id.button2);   
        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);
	}
	
	
	public void signin(View view){
		
		String email=inputEmail.getText().toString();
		String password = inputPassword.getText().toString();
		send_request request=new send_request();
		JSONObject json=request.loginUser(email, password);
		
		try{
			if(json.getString(KEY_SUCCESS)!=null){
				loginErrorMsg.setText("");
				  String res = json.getString(KEY_SUCCESS);
                  if(Integer.parseInt(res) == 1){
                	  
                	 JSONObject usr=json.getJSONObject("user");
                	 
                	 loginErrorMsg.setText(usr.getString(KEY_NAME));
                	 
                	 Intent intent = new Intent(this, Friendslist_activity.class);
                	 intent.putExtra(EXTRA_MESSAGE, usr.getString(KEY_EMAIL));
                	 startActivity(intent); 
     	  
                  }
                  else{
                	  loginErrorMsg.setText("Incorrect username");
                  }
				
			}
		}
		catch(JSONException e){
			e.printStackTrace();
		}
		
		
	}
	public void signup(View view){
		
		String email=inputEmail.getText().toString();
		String password = inputPassword.getText().toString();
		send_request request=new send_request();
		JSONObject json=request.registerUser("name", email, password);
		
		try{
			if(json.getString(KEY_SUCCESS)!=null){
				loginErrorMsg.setText("");
				  String res = json.getString(KEY_SUCCESS);
                  if(Integer.parseInt(res) == 1){
                	  
                 	 JSONObject usr=json.getJSONObject("user");
                	 
                 	 loginErrorMsg.setText(usr.getString(KEY_NAME));
                  }
                  else{
                	  loginErrorMsg.setText(json.getString(KEY_ERROR_MSG));
                  }
				
			}
		}
		catch(JSONException e){
			e.printStackTrace();
		}
		
		
	}	
	
	
	
	

}
