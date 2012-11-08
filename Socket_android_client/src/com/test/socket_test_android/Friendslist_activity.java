package com.test.socket_test_android;

import org.json.JSONException;
import org.json.JSONObject;

import testpack.serial_message;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;



public class Friendslist_activity extends Activity {
	String[] PROJECTION;
    static String KEY_SUCCESS = "success";
    static String KEY_ERROR_MSG="error_msg";
    static String my_email;
    static Socket_com mysocket;
    static String received_message=new String();
    private Myapp myapp;
    ArrayAdapter<String> adapter;
    ListView listView;
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);  
        myapp=(Myapp)getApplication();
        my_email=myapp.get_myemail();
        mysocket=myapp.get_socket();
        getlist();					//send HTTP request to get the list of friends
        setContentView(R.layout.friendslist_activity);
        listView = (ListView) findViewById(R.id.mylist);
        registerForContextMenu(listView);
        adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, PROJECTION);
        listView.setAdapter(adapter); 	        
        listView.setOnItemClickListener( new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            	myapp.set_youremail(PROJECTION[arg2]);     	
               	Intent intent=new Intent(getApplicationContext(), Chat_activity.class);
               	startActivity(intent);            		
            }
            });
    }
    
    @Override
    public void onResume(){
    	
    	super.onResume();
    	update_friendlist();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.friendlist_menu, menu);
    }  
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
                .getMenuInfo();
     
        switch (item.getItemId()) {
        case R.id.delete_friend:
            int row_id=(int) info.id;
    		send_request request=new send_request(this);
    		JSONObject json=request.deleteFriends( my_email, PROJECTION[row_id]);
    		try{
	    		if(json.getString(KEY_SUCCESS)=="1"){
	    		        CharSequence text = "add success";
	    		        int duration = Toast.LENGTH_SHORT;
	    		        Toast toast = Toast.makeText(this, text, duration);
	    		        toast.show();
	    		}
	    		else{
    		        CharSequence text = json.getString(KEY_ERROR_MSG);
    		        int duration = Toast.LENGTH_SHORT;
    		        Toast toast = Toast.makeText(this, text, duration);
    		        toast.show();	    				
	    		}
    		}catch(Exception e){
    			e.printStackTrace();   			
    		}
    		
    		update_friendlist();
    		
    		
            return true;
        }
        return false;
    }    
    
    
	 @Override  
	    public void onDestroy() {  
	        super.onDestroy();  
	        
			serial_message smsg=new serial_message();
			smsg.setContent("");
			smsg.setReceiver("");
			smsg.setSender(my_email);
			smsg.setType("logoutmsg");
			mysocket.sendmessage(smsg);
	        
	        try{
	        	mysocket.socket.close();
	        }catch(Exception e){
	        	e.printStackTrace();
	        	
	        }
	        manage_thread.thread_flag_container.put(my_email, false);
 
	    }  
	public void update_friendlist(){
		
    	getlist();	
        adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, PROJECTION);
        listView.setAdapter(adapter); 				
	}
	      
    
    public void getlist(){
		send_request request=new send_request(this);
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
    
    
    
    public void add_friend(View view){
   	 	Intent intent = new Intent(this, Addfriend_activity.class);
   	 	startActivity(intent);  	
    }
    

}
