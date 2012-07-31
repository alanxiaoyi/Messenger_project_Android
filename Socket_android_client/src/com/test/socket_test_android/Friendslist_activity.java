package com.test.socket_test_android;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class Friendslist_activity extends Activity {
	String[] PROJECTION;
    static String my_email;
    static Socket_com mysocket=new Socket_com();
    static String received_message=new String();
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);  
        getlist();	
    	mysocket.create_connect();
    	mysocket.sendmessage(my_email); 
        setContentView(R.layout.friendslist_activity);
        ListView listView = (ListView) findViewById(R.id.mylist);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1, android.R.id.text1, PROJECTION);
        listView.setAdapter(adapter); 	
        
        listView.setOnItemClickListener( new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            	mysocket.sendmessage("IIIIlovesugurrrr"+PROJECTION[arg2]);
            	mysocket.receiveonemessage();
            	if(Socket_com.received_handle_message.equals("success")){
               	 Intent intent=new Intent(getApplicationContext(), Chat_activity.class);
               	 startActivity(intent);            		
            	}
            }
            });
    }
    
    public void getlist(){
		Bundle extras = getIntent().getExtras();
		my_email = extras.getString(Login_Activity.EXTRA_MESSAGE);	  
		send_request request=new send_request();
		JSONObject json=request.showFriends(my_email);
		int i=0; 	
		try{
			i=0;
			while(true){				
	        	 json.getJSONObject(String.valueOf(i)); 	 
	        	 i++;
			}
		}
		catch(JSONException e){
			e.printStackTrace();
		}    	
		PROJECTION=new String[i];
		try{
 			i=0;
 			while(true){
 				
 				JSONObject usr=json.getJSONObject(String.valueOf(i));
	         	PROJECTION[i]=usr.getString("email");       	 
	         	i++;
 			}
		}
		catch(JSONException e){
			e.printStackTrace();
		}    	  	
    }
    

}
