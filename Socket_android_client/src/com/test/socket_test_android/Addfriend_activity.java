package com.test.socket_test_android;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Addfriend_activity extends Activity{
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR_MSG="error_msg";
    
    private String my_email;
    private String friend_email;
	private EditText friend_email_text;
	private TextView error_msg_view;
	private Myapp myapp;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfriend_activity);
        myapp=(Myapp)getApplication();
        friend_email_text = (EditText) findViewById(R.id.friendemail);
        error_msg_view = (TextView) findViewById(R.id.error_msg);        
		my_email = myapp.get_myemail();	
	}
		
	public void click_add_friend(View view){
        friend_email=friend_email_text.getText().toString();	
		send_request request=new send_request(this);
		JSONObject json=request.addFriend(my_email, friend_email);
		try{
			if(json.getString(KEY_SUCCESS)!=null){
				  String res = json.getString(KEY_SUCCESS);
				  String error_msg=json.getString(KEY_ERROR_MSG);
                  if(res.equals("1")){               	  
                	  error_msg_view.setText("success");
                  }
                  else{
                	  error_msg_view.setText(error_msg);
                  }
				
			}			
		
		}
		catch(JSONException e){
			e.printStackTrace();
		}
	}
}
	
