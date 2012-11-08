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

