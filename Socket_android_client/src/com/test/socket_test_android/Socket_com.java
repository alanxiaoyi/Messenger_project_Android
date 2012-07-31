package com.test.socket_test_android;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;

public class Socket_com {
	static String IP_addr="10.0.1.8";
	static int port_num=8888;
	static Socket socket;
	static String received_handle_message=new String();
	
	void create_connect(){		
		try{
		socket=new Socket(IP_addr, port_num);
		}		
		catch(IOException e){
			e.printStackTrace();
		}
	}
		

	
	public void receiveonemessage(){
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
	
	

}
