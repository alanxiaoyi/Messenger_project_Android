package com.test.socket_test_android;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class chat_list_adapter extends BaseAdapter{
	private Context context;
	private List<chat_list_entry> list;
	LayoutInflater inflater;

	
	public chat_list_adapter(Context context,List<chat_list_entry> list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	public View getView(int position, View convertView, ViewGroup root) {
		TextView content;
//		TextView time;																//add later
		chat_list_entry ce=list.get(position);
		if(ce.isLeft()){
			convertView = inflater.inflate(R.layout.chat_item_left, null);
			
			content=(TextView) convertView.findViewById(R.id.message_chat_left);
//			time=(TextView) convertView.findViewById(R.id.sendtime_chat_left);		//add later
			content.setText(ce.getContent());
//			time.setText(ce.getTime());												//add later
		}else{
			convertView=inflater.inflate(R.layout.chat_item_right, null);
			
			content=(TextView) convertView.findViewById(R.id.message_chat_right);
//			time=(TextView) convertView.findViewById(R.id.sendtime_chat_right);		//add later

			content.setText(ce.getContent());
//			time.setText(ce.getTime());												//add later
		}

		return convertView;
	}
	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
}
