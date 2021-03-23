package adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.app.jufeng.R;
import cn.app.jufeng.SearchActivity;
import bean.ChatInfo;
import bean.ContactInfo;
import bean.LoginInfo;
import tools.ImgTask;

public class ChatAdapter extends BaseAdapter {
	private List<ChatInfo> chatInfos;
	private Context context;
	
	public ChatAdapter(List<ChatInfo> chatInfos, Context context) {
		super();
		this.chatInfos = chatInfos;
		this.context = context;
	}

	public void setData(List<ChatInfo> list){
		chatInfos = list;
		this.notifyDataSetChanged();
	}
	public int addData(ChatInfo c){
		chatInfos.add(c);
		this.notifyDataSetChanged();
		return chatInfos.size();
	}
	public void UpData(List<ChatInfo> list){
		chatInfos.clear();
		this.notifyDataSetChanged();
		chatInfos = list;
		this.notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return chatInfos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return chatInfos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View converView, ViewGroup parent) {
		ViewHolder holder= null;
		String who = chatInfos.get(position).getWho();
//		System.out.println(who);
//		if(who.equals("other")){
//			if(converView==null){
//				converView = View.inflate(context, R.layout.item_chat_left, null);
//				holder = new ViewHolder();
//				holder.tv_time = (TextView) converView.findViewById(R.id.chat_tv_time_left);
//				holder.img_head = (ImageView) converView.findViewById(R.id.chat_img_left);
//				holder.et_content = (TextView) converView.findViewById(R.id.chat_tv_left);
//				converView.setTag(holder);
//			}else{
//				holder = (ViewHolder) converView.getTag();
//			}
//		}else if(who.equals("cache")){
//			if(converView==null){
//				converView = View.inflate(context, R.layout.item_chat_send, null);
//				holder = new ViewHolder();
//				holder.tv_time = (TextView) converView.findViewById(R.id.chat_tv_time_rightt);
//				holder.img_head = (ImageView) converView.findViewById(R.id.chat_img_rightt);
//				holder.et_content = (TextView) converView.findViewById(R.id.chat_tv_rightt);
//				converView.setTag(holder);
//			}else{
//				holder = (ViewHolder) converView.getTag();
//			}
//		}else{
//			if(converView==null){
//				converView = View.inflate(context, R.layout.item_chat_right, null);
//				holder = new ViewHolder();
//				holder.tv_time = (TextView) converView.findViewById(R.id.chat_tv_time_right);
//				holder.img_head = (ImageView) converView.findViewById(R.id.chat_img_right);
//				holder.et_content = (TextView) converView.findViewById(R.id.chat_tv_right);
//				converView.setTag(holder);
//			}else{
//				holder = (ViewHolder) converView.getTag();
//			}
//		}
		
		if(converView==null){
			if(chatInfos.get(position).getWho().equals("other")){
					converView = View.inflate(context, R.layout.item_chat_left, null);
					holder = new ViewHolder();
					holder.tv_time = (TextView) converView.findViewById(R.id.chat_tv_time_left);
					holder.img_head = (ImageView) converView.findViewById(R.id.chat_img_left);
					holder.et_content = (TextView) converView.findViewById(R.id.chat_tv_left);
					converView.setTag(holder);
					holder.tv_time.setText(chatInfos.get(position).getTime());
					holder.et_content.setText(chatInfos.get(position).getContent());
					ImgTask imgtask = new ImgTask(holder.img_head);
					imgtask.execute(chatInfos.get(position).getHeadurl());
					return converView;
			}else if(who.equals("cache")){
				converView = View.inflate(context, R.layout.item_chat_send, null);
				holder = new ViewHolder();
				holder.tv_time = (TextView) converView.findViewById(R.id.chat_tv_time_rightt);
				holder.img_head = (ImageView) converView.findViewById(R.id.chat_img_rightt);
				holder.et_content = (TextView) converView.findViewById(R.id.chat_tv_rightt);
				converView.setTag(holder);
				holder.tv_time.setText(chatInfos.get(position).getTime());
				holder.et_content.setText(chatInfos.get(position).getContent());
				ImgTask imgtask = new ImgTask(holder.img_head);
				imgtask.execute(LoginInfo.UserHead);
				return converView;
			}else{
				converView = View.inflate(context, R.layout.item_chat_right, null);
				holder = new ViewHolder();
				holder.tv_time = (TextView) converView.findViewById(R.id.chat_tv_time_right);
				holder.img_head = (ImageView) converView.findViewById(R.id.chat_img_right);
				holder.et_content = (TextView) converView.findViewById(R.id.chat_tv_right);					
				converView.setTag(holder);
				converView.setTag(holder);
				holder.tv_time.setText(chatInfos.get(position).getTime());
				holder.et_content.setText(chatInfos.get(position).getContent());
				ImgTask imgtask = new ImgTask(holder.img_head);
				imgtask.execute(LoginInfo.UserHead);
				return converView;
			}
		}else{
			holder = (ViewHolder) converView.getTag();
			if(chatInfos.get(position).getWho().equals("other")){
				holder.tv_time.setText(chatInfos.get(position).getTime());
				holder.et_content.setText(chatInfos.get(position).getContent());
				ImgTask imgtask = new ImgTask(holder.img_head);
				imgtask.execute(chatInfos.get(position).getHeadurl());
				return converView;
			}else if(who.equals("cache")){
				holder.tv_time.setText(chatInfos.get(position).getTime());
				holder.et_content.setText(chatInfos.get(position).getContent());
				ImgTask imgtask = new ImgTask(holder.img_head);
				imgtask.execute(LoginInfo.UserHead);
				return converView;
			}else{
				holder.tv_time.setText(chatInfos.get(position).getTime());
				holder.et_content.setText(chatInfos.get(position).getContent());
				ImgTask imgtask = new ImgTask(holder.img_head);
				imgtask.execute(LoginInfo.UserHead);
				return converView;
			}
		}

		
	}
	
	class ViewHolder{
		TextView tv_time;
		ImageView img_head;
		TextView et_content;
	}
}
