package com.test.socket_test_android;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Chat_activity extends Activity {
	static Socket_com my_socket;
	TextView mtextview=null;  
	EditText redittext=null; 
	EditText medittext=null;
	static Handler mHandler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		Intent intent=getIntent();     
    	mtextview=(TextView)findViewById(R.id.textView2);
    	redittext=(EditText)findViewById(R.id.editText2);
    	medittext=(EditText)findViewById(R.id.editText1);
    	mtextview.setText("hello");
        mHandler = new Handler(){
        	public void handleMessage(Message msg){
        		String text=(String)(msg.obj);
        		redittext.setText(text);
        	}   
        };  	
    	my_socket=new Socket_com();

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
		    	        mHandler.sendMessage(message);
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

    	String message = medittext.getText().toString();
    	my_socket.sendmessage(message);
    }
    	
}


