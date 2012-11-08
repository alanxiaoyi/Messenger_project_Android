package testpack;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;


class socket_email_pair{			//locate socket through email address
	public Socket socket;
	public String email;
	
	socket_email_pair(Socket socket, String email){
		this.socket=socket;
		this.email=email;
	}
	
}

public class socket_main {
	
//	public static Socket socket_array[]=new Socket[10];   
	
	public static List<socket_email_pair> online_list=new ArrayList<socket_email_pair>();
	static int maxConn=10;
    static int port = 8888;   
    static int flag=0;
    
    public static void main(String[] args) {
    	int i=0;
    	ServerSocket ss = null;
        Socket socket=null;
 //       socket_array[1]=null;
        
        try{
            ss = new ServerSocket(port);				//begin to listen the port
            
            while((i<maxConn)){
	            socket = ss.accept();					//block main thread until get a client to socket
	            doComms conn=new doComms(socket,i);  
	            Thread t=new Thread(conn);
	            t.start(); 
	            i++;
            }
        }
        catch(IOException e){
 
        		System.out.println("failed to create socket");
        }

    }
}







class doComms implements Runnable{			//it is the thread that process one socket
	
	private Socket socket;
	private int id;
	private boolean stop;

	doComms(Socket socket, int id){
		
		this.socket=socket;
		this.id=id;
		stop=false;

	}
	
	public void sendmessage(Socket send_socket,serial_message message){
		
		ObjectOutputStream oos;
		try{
			oos=new ObjectOutputStream(send_socket.getOutputStream());
			oos.writeObject(message);
			
		}catch(Exception e){
			
		}
			
	}
	
	
	public socket_email_pair find_in_list(String s){
   		
		
		for(Iterator<socket_email_pair> it=socket_main.online_list.iterator(); it.hasNext();){	
   			socket_email_pair tmp = it.next();
   			if(tmp.email.equals(s)){
   				return tmp;
   			}
   			else continue;
   		}   	
		
		return null;
		
		
	}
	
	
	
	public void run(){
			//I need two socket since we need to establish the connection of two users
			// so the question is how to find "yoursocket", that is which friend that "I" want to talk with
		   	 Socket mysocket=socket;	
		   	 Socket yoursocket=null;

		   	 //below is the first time socket communication
		   	 //it must be the one that establish the communication
		   	 //on the client, it should be login action
/*		   	 try{		   		 
			       DataInputStream dis = new DataInputStream(mysocket.getInputStream());
		           byte[] receive_b=new byte[1024];
		           dis.read(receive_b,0,1024);  
		           String received_string=new String(receive_b, "UTF-16").trim();	
		           socket_email_pair new_online=new socket_email_pair(mysocket,received_string ); // create a email/socket pair
		           socket_main.online_list.add(new_online);
		           System.out.println(received_string);
			          
		   	 }
		   	 catch(IOException e){
		   		e.printStackTrace(); 		   		
		   	 }		   	 */
		   	 
		   	 
		   	System.out.println("new connect");	   	 
		//then the while loop is for the message continually sent to server
        while(!stop){
        	
        	
        	try{
        		
        		ObjectInputStream ois;
        		
        		ois=new ObjectInputStream(mysocket.getInputStream());  		
        		serial_message m=null;
        		try{
        			m=(serial_message)ois.readObject();
        		}catch(IOException e){
        			String error=e.getMessage();
        			System.out.println(error);
        		}
        	
        		String msg_type=m.getType();
        		String sender=m.getSender();
        		String receiver=m.getReceiver();
        		String content=m.getContent();    
 //       		System.out.println(msg_type);
//        		System.out.println(sender);
        	 // String time=m.getString("time");  					//add later
        	        		      		
        		if(msg_type.equals("loginmsg")){	       			
        			socket_email_pair new_online=new socket_email_pair(mysocket,sender ); // create a email/socket pair
  		            socket_main.online_list.add(new_online);
  		            System.out.println("loginmsg: "+sender);      			      			       			
        		}
        		
        		else if(msg_type.equals("logoutmsg")){	  
        			socket_email_pair removed_one=find_in_list(sender);
        			removed_one.socket.close();
        			socket_main.online_list.remove(removed_one);
        			stop=true;         			      			       			
        		}
        		
        		else if(msg_type.equals("chatmsg")){
 
        			socket_email_pair result;
        			result=find_in_list(receiver);
               		
               		if(result==null){
          
               			serial_message smsg=new serial_message();
               			smsg.setType("errmsg");
               			smsg.setSender(sender);
               			smsg.setReceiver(receiver);
               			smsg.setContent(content);
               			sendmessage(mysocket, smsg); 
               			System.out.println("errer: "+content);  
               		}
               		else{	
               			yoursocket=result.socket;
               			serial_message smsg=new serial_message();
               			smsg.setType("chatmsg");
               			smsg.setSender(sender);
               			smsg.setReceiver(receiver);
               			smsg.setContent(content);
               			sendmessage(yoursocket, smsg);
               			System.out.println("content: "+content);
               			System.out.println("receiver: "+receiver);
               	
               		}
               		
        			
        		}
        	}catch(Exception e){
        		
        		
        		String error=e.getMessage();
    			System.out.println(error);	
    			try{
    				mysocket.close();
    				
    			}catch(Exception se){
    				
    			}
    			stop=true;
        		
        	}
        	
        	
       }	
		
	}
}










/*       try {             	
    DataInputStream dis = new DataInputStream(mysocket.getInputStream());
   	byte[] receive_b=new byte[1024];
   	dis.read(receive_b,0,1024);  
   	String received_string=new String(receive_b, "UTF-16");
   	
   	//选定一个好友
   	if(received_string.indexOf("IIIIlovesugurrrr")==0){
   		
   		String friend_email=received_string.substring(16).trim();
   		for(Iterator<socket_email_pair> it=socket_main.online_list.iterator(); it.hasNext();){	
   	
   			socket_email_pair tmp = it.next();
   		 	System.out.println("1"+tmp.email);
   		 	System.out.println("2"+friend_email);
   			if(tmp.email.equals(friend_email)){
   				yoursocket=tmp.socket;		//find "yoursocket" from the online list
   			}
   			else continue;
   		}
   		if(yoursocket!=null){	           		
	        DataOutputStream dos = new DataOutputStream(mysocket.getOutputStream());
	        String s="success";
	        byte[] send_b=new byte[1024];
	        send_b=s.getBytes("UTF-16");				        
           	dos.write(send_b);
           	
           	System.out.println(received_string);
   		}
   		else {
	        DataOutputStream dos = new DataOutputStream(mysocket.getOutputStream());
	        String f="fail";
	        byte[] send_b=new byte[1024];
	        send_b=f.getBytes("UTF-16");				        
           	dos.write(send_b);
           	System.out.println(received_string);	           				
   		}
   	}
   	else if(yoursocket!=null){
        DataOutputStream dos = new DataOutputStream(yoursocket.getOutputStream());
       	dos.write(receive_b,0,1024);
    	System.out.println("haha");
       	System.out.println(received_string);
		}
   	
  
} catch (IOException e) {
    e.printStackTrace();
    break;
}*/














/*		   	 while(socket_main.flag==0){};
	 try{70
	 Thread.sleep(100);
	 }
	 catch(Exception e){
		 e.printStackTrace();
	 }
	 if(id==0){		   	 
		 yoursocket=socket_main.socket_array[1];   //refer to the global variable
	 }
	 else if (id==1){		  
		 yoursocket=socket_main.socket_array[0];
	 }*/
	 
/*		   	 try{
		 
      DataInputStream dis = new DataInputStream(mysocket.getInputStream());
      	int a= dis.readInt();
	   	 if(id==0){		   	 
	   		 yoursocket=socket_main.socket_array[1];   //refer to the global variable
	   	 }
	   	 else if (id==1){		  
	   		 yoursocket=socket_main.socket_array[0];
	   	 }
	 }
	 catch(IOException e){
		e.printStackTrace(); 		   		
	 }
	 */