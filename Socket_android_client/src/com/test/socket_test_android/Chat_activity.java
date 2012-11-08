package com.test.socket_test_android;
import java.util.ArrayList;
import java.util.List;

import testpack.serial_message;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import  android.widget.ListView;

public class Chat_activity extends Activity {
	public List<chat_list_entry> chatentrylist=new ArrayList<chat_list_entry>();// save all the chat content
	ListView chatListView;
	Socket_com my_socket;
	String my_email;
	String your_email;
	TextView mtextview=null;  
//	EditText redittext=null; 
	EditText medittext=null;
	MyBroadcastReceiver br;
//	static Handler mHandler;
	private Myapp myapp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  
        myapp=(Myapp)getApplication();
    	mtextview=(TextView)findViewById(R.id.textView2);
//    	redittext=(EditText)findViewById(R.id.editText2);
    	medittext=(EditText)findViewById(R.id.editText1);
    	my_socket=myapp.get_socket();
    	my_email=myapp.get_myemail();
    	your_email=myapp.get_youremail();
    	
    	//register the receiver   	
		IntentFilter myIntentFilter = new IntentFilter(); 
        myIntentFilter.addAction("com.mymessenger.msg");
        br=new MyBroadcastReceiver();
        registerReceiver(br, myIntentFilter);
    }
    
    //broadcast receiver re-implementation
    
	public class MyBroadcastReceiver extends BroadcastReceiver {			//in-class class, so donot need to register in manifest.xml
		@Override
		public void onReceive(Context context, Intent intent) {
			String[] msg = intent.getStringArrayExtra("MSG");
			if(msg[0].equals("chatmsg")){		
			    updateChatView(new chat_list_entry(msg[3],true));
			}
		}
	}
	
	public void updateChatView(chat_list_entry chatentry){
		
		chatentrylist.add(chatentry);
		chatListView=(ListView) findViewById(R.id.chat_list);
		chatListView.setAdapter(new chat_list_adapter(this,chatentrylist));		

	}
	
	public void sendmessage(View view){	
		
		serial_message smsg=new serial_message();
		smsg.setContent(medittext.getText().toString());
		smsg.setReceiver(your_email);
		smsg.setSender(my_email);
		smsg.setType("chatmsg");
	    updateChatView(new chat_list_entry(medittext.getText().toString(),false));
		my_socket.sendmessage(smsg);
		
	}
	
	 @Override  
	    public void onDestroy() {  
	        super.onDestroy();  
	        unregisterReceiver(br);
	    }  
	      
    
    
}
    
    
    
    
    
    
    
/*        mHandler = new Handler(){			//main thread get the message sent from subthread
public void handleMessage(Message msg){
	String text=(String)(msg.obj);
	redittext.setText(text);
}   
};  */	   
    
    /*
	Thread t = new Thread() {
		public void run(){
    	try{
    		while(true){
    				byte[] receive_b=new byte[1024];
    	    		DataInputStream dis=new DataInputStream(my_socket.socket.getInputStream());
    	    		dis.read(receive_b,0,1024);
    	    		int i=1023;
    	    		for(; i>=0; i--){
    	    			if(receive_b[i]!=0){
    	    				break;				
    	    			}			
    	    		}
    	    		byte[] receive_bs;
    	    		if((i&1)!=0){
    	    			receive_bs=new byte[i+1];
    	    			System.arraycopy(receive_b, 0,receive_bs, 0, i+1);	
    	    		}
    	    		else{
    	    			receive_bs=new byte[i+2];
    	    			System.arraycopy(receive_b, 0,receive_bs, 0, i+2);		    	    			
    	    		}
    	    		String received_text = new String(receive_bs,"UTF-16");
	    			Message message = new Message();
	    	        message.obj = received_text;
	    	        mHandler.sendMessage(message);		//sent message to main thread
    			}

    	}
    	catch(IOException e){
    		Message excp=new Message();
    		excp.obj="failed";
    		mHandler.sendMessage(excp);
    		e.printStackTrace();
    	}

		
		}
	};
	
	t.start();
	
}


public void sendmessage(View view){
    JSONObject message;
	String message_string = medittext.getText().toString();  	
    message=wrap_message(myapp.get_youremail(), message_string);      
	my_socket.sendmessage(message.toString());
}

*/  


