package cn.app.jufeng;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import bean.LoginInfo;
import tools.HttpUtil;
import tools.ImgTask;

public class FriendAddActivity extends Activity {
	private ImageView img_black;
	private Button btn_send;
	private ImageView img_head;
	private TextView tv_id;
	private TextView tv_name;
	private TextView tv_phone;
	private TextView tv_email;
	private String head;
	private String id;
	private String name;
	private String phone;
	private String email;
	
	Handler mHandler  = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				ImgTask imgtask = new ImgTask(img_head);
				imgtask.execute(head);
				tv_id.setText(id);
				tv_name.setText(name);
				tv_phone.setText(phone);
				tv_email.setText(email);
				break;
			case 2:
				Toast.makeText(FriendAddActivity.this, "添加成功", 1).show();;
				break;
			case 3:
				Toast.makeText(FriendAddActivity.this, "添加失败", 1).show();
				break;
			case 4:
				Toast.makeText(FriendAddActivity.this, "对方已是您的好友", 1).show();
				break;
			}
		};
	};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_friend_add);
		initView();
	}
	
	private void initView(){
		img_black = (ImageView) findViewById(R.id.friend_add_img_black);
		btn_send = (Button) findViewById(R.id.friend_add_btn_send);
		img_head = (ImageView) findViewById(R.id.friend_add_img_head);
		tv_id = (TextView) findViewById(R.id.friend_add_tv_id);
		tv_name = (TextView) findViewById(R.id.friend_add_tv_name);
		tv_phone = (TextView) findViewById(R.id.friend_add_tv_phone);
		tv_email = (TextView) findViewById(R.id.friend_add_tv_email);
		Bundle bundle = this.getIntent().getExtras();
		head = bundle.getString("headurl");
		id = bundle.getString("id");
		name = bundle.getString("name");
		phone = bundle.getString("phone");
		email = bundle.getString("email");
		System.out.println(head+id+name+phone+email);
		new Thread(){
			public void run(){
				mHandler.sendEmptyMessage(1);
				
			}
		}.start();
		
		img_black.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FriendAddActivity.this,Main.class);
            	startActivity(intent);
			}
		});
		
		btn_send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(){
					public void run(){
						String url = HttpUtil.BASE_URL+"AddFriend?otherId="+id+"&myId="+LoginInfo.UserId;
						System.out.println(url);
						String rs = HttpUtil.Post(url);
						if(rs.equals("true")){
							mHandler.sendEmptyMessage(2);
							Timer nTimer = new Timer();
					        nTimer.schedule(new TimerTask() {
					            @Override
					            public void run() {
					            	Intent intent = new Intent(FriendAddActivity.this,Main.class);
					            	startActivity(intent);
					            }
					        },1000);
							
						}else if(rs.equals("exist")){
							mHandler.sendEmptyMessage(4);
						}else{
							mHandler.sendEmptyMessage(3);
						}
					}
				}.start();
			}
		});
	}
	
	protected void onPause() {		
		super.onPause();
		finish();
	}
}
