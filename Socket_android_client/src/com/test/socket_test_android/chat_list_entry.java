package com.test.socket_test_android;

public class chat_list_entry {

		private String content;
	//	private String time;   //add later
		private boolean isLeft;
		
		public chat_list_entry(String content,boolean isLeft){

			this.content = content;
//			this.time = time;
			this.isLeft = isLeft;
		}
		

		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
/*		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}*/
		public boolean isLeft() {
			return isLeft;
		}
		public void setLeft(boolean isLeft) {
			this.isLeft = isLeft;
		}
	}
