package com.test.socket_test_android;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import testpack.serial_message;
import android.content.Context;
import android.content.Intent;

public class Socket_com {
	
	public String IP_addr="10.0.1.8";
	public int port_num=8888;
	public Socket socket;
	public String received_handle_message=new String();
	
	
	void create_connect(){		
		try{
		socket=new Socket(IP_addr, port_num);
		}		
		catch(IOException e){
			e.printStackTrace();
		}
	}
		
//this was called by the chat_thread
public void receivemessage(String myemail, Context context){
	
		
		ObjectInputStream ois = null;
		serial_message m;
		try{
			ois = new ObjectInputStream(socket.getInputStream());
			m=(serial_message)(ois.readObject());
			String[] message_string=new String[]{
				m.getType(),
				m.getSender(),
				m.getReceiver(),
				m.getContent(),
		//		m.getString("time")				add later
			};
			
			
			Intent intent=new Intent("com.mymessenger.msg");
			intent.putExtra("MSG", message_string);
			
			Context appcontext;
			appcontext=context.getApplicationContext();
			appcontext.sendBroadcast(intent);
			
		}catch(Exception e){
	        manage_thread.thread_flag_container.put(myemail, false);
			e.printStackTrace();
		}
	
}

// this will be called whenever send a JSON message
public void sendmessage(serial_message message){
	
	
	ObjectOutputStream oos=null;
	
	try {
		oos = new ObjectOutputStream(socket.getOutputStream());
		oos.writeObject(message);
	}catch(Exception e){
		e.printStackTrace();	
		
	}
	
}
	
	
	
	
	
/*	public void receiveonemessage(){
		try{
		byte[] receive_b=new byte[1024];
		DataInputStream dis=new DataInputStream(socket.getInputStream());
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
		received_handle_message = new String(receive_bs,"UTF-16");
		}
    	catch(IOException e){

    		e.printStackTrace();
    	}
	}
	
	
		

    public void sendmessage(String message){
    	try{    		
    		byte[] send_b= new byte[1024];
	    	send_b=message.getBytes("UTF-16");
    		DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
    		dos.write(send_b);
    		
    		
    	}    	
    	catch (Exception ex) {	   
        ex.printStackTrace();
    }
   }	
	
	*/

}
