package cn.app.jufeng;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Images.ImageColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import adapter.ChatAdapter;
import bean.ChatInfo;
import bean.LoginInfo;
import tools.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ChatActivity extends Activity {
	private ListView chat_lv_message;
	private EditText chat_et_message;
	private Button chat_btn_send;
	private List<ChatInfo> chatInfos;
	private ChatAdapter chatAdapter;
	private String other_id;
	private String other_headurl;
	private String chat;
	private Boolean UpLoadflag=false;
	private ImageView btn_back;
	Handler mHandler  = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if(chatInfos != null){
					chatAdapter = new ChatAdapter(chatInfos,ChatActivity.this);
					chat_lv_message.setAdapter(chatAdapter);
				}
				break;
			case 2:
				UpLoadflag = true;
//				ChatInfo chat = new ChatInfo("cache", "", chat_et_message.getText().toString(), UserInfo.UserHead);
//				chat_lv_message.smoothScrollToPosition(chatAdapter.addData(chat)-1);
				chat_et_message.setText("");
				break;
			case 3:
				if(chatAdapter!=null){
					chatAdapter.UpData(chatInfos);
				}else{
					chatAdapter = new ChatAdapter(chatInfos,ChatActivity.this);
					chat_lv_message.setAdapter(chatAdapter);
				}
				break;
			case 4:
				Toast.makeText(ChatActivity.this, "未知错误", 1).show();
				break;
			case 5:
				UpLoadflag = false;
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		initView();
		
	}
	private void initView(){
		Bundle bundle = this.getIntent().getExtras();
		other_id = bundle.getString("other_id");
		other_headurl = bundle.getString("other_headurl");
		chat = bundle.getString("chat");
		chat_lv_message = (ListView) findViewById(R.id.chat_lv_message);
		chat_et_message = (EditText) findViewById(R.id.chat_et_message);
		chat_btn_send = (Button) findViewById(R.id.chat_btn_send);
		btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		chatInfos = new ArrayList<ChatInfo>();
		
		chat_btn_send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!chat_et_message.getText().toString().equals("")||chat_et_message.getText().toString()==null){
				new Thread(){
					public void run(){
						String url = HttpUtil.BASE_URL+"UpLoadContactJSON?otherId="+other_id+"&myId="+LoginInfo.UserId+"&myMessage="+chat_et_message.getText().toString();
						String rs = HttpUtil.Post(url);
						mHandler.sendEmptyMessage(2);
						if(rs.equals("true")){
//							Timer nTimer = new Timer();
//					        nTimer.schedule(new TimerTask() {
//					            @Override
//					            public void run() {
//					            }
//					        },2000);
					        mHandler.sendEmptyMessage(5);
						}else{
							mHandler.sendEmptyMessage(4);
						}
						
					}
				}.start();
				}else{
					Toast.makeText(ChatActivity.this, "您还没输入内容", 1).show();
				}	
			}
		});
		
		new Thread(){
			public void run(){
				JSONArray jsonArray = JSONArray.fromObject(chat);
				if (jsonArray.size()!=0) {
					for(int i = 0; i<jsonArray.size(); i++){
						ChatInfo chatInfo = new ChatInfo(jsonArray.getJSONObject(i).getString("who"), jsonArray.getJSONObject(i).getString("time"), jsonArray.getJSONObject(i).getString("content"),other_headurl);
						chatInfos.add(chatInfo);
					}
					mHandler.sendEmptyMessage(1);
				}
			}
		}.start();
		
		new Thread(){
			public void run(){
				Timer timer = new Timer();
		        timer.schedule(new TimerTask() {
		            public void run() {
		            	if(!UpLoadflag){
		            		String url = HttpUtil.BASE_URL+"SelectChat?otherId="+other_id+"&myId="+LoginInfo.UserId;
		            		String rs = HttpUtil.Post(url);
		            		JSONObject json = JSONObject.fromObject(rs);
		            		String other_HeadUrl = json.getString("other_url");
		            		JSONArray json_array = json.getJSONArray("chat");
		            		chatInfos = new ArrayList<ChatInfo>();
		            		if(json_array.size()!=0){
		            			for(int i=0; i<json_array.size(); i++){
		            				JSONObject temp = json_array.getJSONObject(i);
		            				ChatInfo chat = new ChatInfo(temp.getString("who"), temp.getString("time"), temp.getString("content"), other_HeadUrl);
		            				chatInfos.add(chat);
		            			}
		            		}
		            		mHandler.sendEmptyMessage(3);
		            	}
		            }
		        }, 1000, 1000);
			}
		}.start();
		
	}
//	protected void onPause() {		
//		super.onPause();
//		finish();
//	}
}
