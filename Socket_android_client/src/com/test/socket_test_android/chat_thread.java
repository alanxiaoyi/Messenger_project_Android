package com.test.socket_test_android;

import android.content.Context;

//thread for waiting serial message come based on socket
public class chat_thread extends Thread{

	private Context context;
	private Socket_com my_socket;
	private String myemail;
	private Boolean flag;
	
	
	public chat_thread(String m, Socket_com s, Context c){
		
		this.context=c;
		this.my_socket=s;
		this.myemail=m;
		flag=true;		
		manage_thread.thread_flag_container.put(myemail,true);
				
	}
	
	@Override
	public void run(){  
		while(flag){
			my_socket.receivemessage(myemail, context);	
			flag=(Boolean)manage_thread.thread_flag_container.get(myemail);
		}
		
	}

}
