package testpack;


import java.util.Iterator;
import java.util.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class socket_email_pair{
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
            ss = new ServerSocket(port);
            
            while((i<maxConn)){
	            socket = ss.accept();
//	            socket_main.socket_array[i]=socket;
	            doComms conn=new doComms(socket,i);  
	            Thread t=new Thread(conn);
	            t.start(); 
/*	            if (i==1){
	            	flag=1;
	            }*/
	            i++;
            }
        }
        catch(IOException e){
 
        		System.out.println("failed to create socket");
        }

    }
}

class doComms implements Runnable{
	
	private Socket socket;
	private int id;

	doComms(Socket socket, int id){
		
		this.socket=socket;
		this.id=id;

	}
	
	public void run(){
		
		   	 Socket mysocket=socket;
		   	 Socket yoursocket=null;

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
		   	 try{		   		 
			       DataInputStream dis = new DataInputStream(mysocket.getInputStream());
		           byte[] receive_b=new byte[1024];
		           dis.read(receive_b,0,1024);  
		           String received_string=new String(receive_b, "UTF-16").trim();	
		           socket_email_pair new_online=new socket_email_pair(mysocket,received_string );
		           socket_main.online_list.add(new_online);
		           	System.out.println(received_string);
			          
		   	 }
		   	 catch(IOException e){
		   		e.printStackTrace(); 		   		
		   	 }		   	 
		   	 
		   	 
		   	 

        while(true){
        	
	         try {             	
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
	           				yoursocket=tmp.socket;
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
	        }
       }		
		
	}
}