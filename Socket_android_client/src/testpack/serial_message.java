package testpack;





public class serial_message implements java.io.Serializable{

	String type;
	String sender;
	String receiver;
	String content;
	
	public String getType() {
		return type;
	}
	public void setType(String Type) {
		this.type = Type;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String Sender) {
		this.sender = Sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String Receiver) {
		this.receiver = Receiver;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String Content) {
		this.content = Content;
	}
	
}







/*	public serial_message(String t, String s, String r, String c){

this.type=t;
this.sender=s;
this.receiver=r;
this.content=c;

}



public JSONObject wrap_message(){

JSONObject message = new JSONObject(); 
try{
	message.put("type", type);  
	message.put("sender", sender); 
	message.put("receiver", receiver);
	message.put("content", content);  
//message.add(new BasicNameValuePair("time", time));   			//add later
}
catch (JSONException e) {
   Log.e("JSON wraping", "Error wraping data " + e.toString());
}   	
return message;    		
    	
}
*/