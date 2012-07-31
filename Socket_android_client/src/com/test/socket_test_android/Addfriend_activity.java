package com.test.socket_test_android;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Addfriend_activity extends Activity{
    private static String KEY_SUCCESS = "success";
    
    static String my_email;
    static String friend_email;
	EditText friend_email_text;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfriend_activity);
        friend_email_text = (EditText) findViewById(R.id.friendemail);
        
		Bundle extras = getIntent().getExtras();
		my_email = extras.getString(Login_Activity.EXTRA_MESSAGE);		
	}
		
	public void click_add_friend(View view){
        friend_email=friend_email_text.getText().toString();	
		send_request request=new send_request();
		JSONObject json=request.addFriend(my_email, friend_email);
		try{
			if(json.getString(KEY_SUCCESS)!=null){
				  String res = json.getString(KEY_SUCCESS);
                  if(Integer.parseInt(res) == 1){               	  
                 	 String usr=json.getString(KEY_SUCCESS);
                  }
                  else{
                  }
				
			}			
			
			
		}
		catch(JSONException e){
			e.printStackTrace();
		}
	}
}
	
