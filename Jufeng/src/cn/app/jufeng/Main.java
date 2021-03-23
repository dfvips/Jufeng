package cn.app.jufeng;

import Utils.ImgTask;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.app.jufeng.R;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import adapter.ContactAdapter;
import bean.ContactInfo;
import bean.LoginInfo;
import tools.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Main extends Activity implements View.OnClickListener {
	
	private ImageButton img_msg;
	private ImageButton img_find;
	private ImageButton img_my;
	private ImageButton img_contact;
	private Button btn_add;
	private TextView tv_title;
	private View my;
	private View address_list;
	private View find;
	private ImageView img_head;
	private ImageView img_logo;
	private TextView btn_relogin;
	private TextView btn_logout;
	private TextView btn_repwd;
	private TextView tv_id;
	private TextView about;
	public static TextView tv_username;
	public static TextView tv_useremail;
	public static TextView tv_userphone;
	private ImageView btn_changename;
	private ImageView btn_changephone;
	private ImageView btn_changeemail;
	public static Activity main;
	public static String  u_id;
	private ListView lv_contact;
	private ContactAdapter contactAdapter;
	private List<ContactInfo> contactInfos;
	
	Handler mHandler  = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if(contactInfos != null){
					contactAdapter = new ContactAdapter(contactInfos,Main.this);
					lv_contact.setAdapter(contactAdapter);
				}
				break;
			case 2:
				if(contactAdapter!=null){
					contactAdapter.UpData(contactInfos);
				}else{
					if(contactInfos != null){
						contactAdapter = new ContactAdapter(contactInfos,Main.this);
					}
				}
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		main=this;
		init();
		img_msg.setOnClickListener(this);
		img_find.setOnClickListener(this);
		img_my.setOnClickListener(this);
		img_contact.setOnClickListener(this);
		btn_relogin.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
		btn_changename.setOnClickListener(this);
		btn_changephone.setOnClickListener(this);
		btn_changeemail.setOnClickListener(this);
		btn_repwd.setOnClickListener(this);
		btn_add.setOnClickListener(this);
		address_list.setVisibility(View.VISIBLE);
		about.setOnClickListener(this);
	}

	private void init() {
		// TODO Auto-generated method stub
		img_msg=(ImageButton)findViewById(R.id.img_msg);
		img_find=(ImageButton)findViewById(R.id.img_find);
		img_my=(ImageButton)findViewById(R.id.img_my);
		img_contact=(ImageButton)findViewById(R.id.img_contact);
		btn_add=(Button)findViewById(R.id.btn_add);
		tv_title=(TextView)findViewById(R.id.tv_title);
		img_head=(ImageView)findViewById(R.id.img_head);
		img_logo=(ImageView)findViewById(R.id.btn_cancle);
		btn_relogin=(TextView)findViewById(R.id.btn_relogin);
		btn_logout=(TextView)findViewById(R.id.btn_logout);
		tv_id=(TextView)findViewById(R.id.tv_id);
		tv_username=(TextView)findViewById(R.id.tv_username);
		tv_useremail=(TextView)findViewById(R.id.tv_user_email);
		tv_userphone=(TextView)findViewById(R.id.tv_userphone);
		btn_changename=(ImageView)findViewById(R.id.btn_change_name);
		btn_changephone=(ImageView)findViewById(R.id.btn_changephone);
		btn_changeemail=(ImageView)findViewById(R.id.btn_changeemail);
		btn_repwd=(TextView)findViewById(R.id.btn_repwd);
		address_list=findViewById(R.id.address_list);
		about=(TextView)findViewById(R.id.about);
		startlistactivity();
		SharedPreferences mysharedPreferences= getSharedPreferences("usermsg", 
				Activity.MODE_PRIVATE); 
		String img1 =mysharedPreferences.getString("img", ""); 
    	ImgTask imgtask1 = new ImgTask(img_logo);
    	imgtask1.execute(img1);
		my=findViewById(R.id.my);
		find=findViewById(R.id.find);
		
	}

	private void startlistactivity() {
		// TODO Auto-generated method stub
		lv_contact = (ListView) findViewById(R.id.lv_contact);
		contactInfos = new ArrayList<ContactInfo>();
		new Thread(){
			public void run(){
				String url = HttpUtil.BASE_URL+"OnloadContactJSON?userId="+LoginInfo.UserId;
				JSONObject jsonobject = JSONObject.fromObject(HttpUtil.Post(url));
				JSONArray jsonArray = jsonobject.getJSONArray("contact");
				
				if (jsonArray.size()!=0) {
					for(int i = 0; i<jsonArray.size(); i++){
						String sql = "select * from user_info where u_id='"+jsonArray.getJSONObject(i).getString("id")+"'";
						String url1="";
						try {
							url1 = HttpUtil.BASE_URL+"SelectUser?sql="+URLEncoder.encode(sql);
							System.out.println(url1);
						} catch (Exception e) {}
						String jsonString = HttpUtil.Post(url1);
						System.out.println(jsonString );
						JSONArray array = JSONArray.fromObject(jsonString);
						ContactInfo contactInfo = new ContactInfo(jsonArray.getJSONObject(i).getString("id"), array.getJSONObject(0).getString("u_headurl"), array.getJSONObject(0).getString("u_name"), jsonArray.getJSONObject(i).getJSONArray("chat"));
						contactInfos.add(contactInfo);
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
		            		String url = HttpUtil.BASE_URL+"OnloadContactJSON?userId="+LoginInfo.UserId;
		    				JSONObject jsonobject = JSONObject.fromObject(HttpUtil.Post(url));
		    				JSONArray jsonArray = jsonobject.getJSONArray("contact");
		    				contactInfos = new ArrayList<ContactInfo>();
		    				if (jsonArray.size()!=0) {
		    					for(int i = 0; i<jsonArray.size(); i++){
		    						String sql = "select * from user_info where u_id='"+jsonArray.getJSONObject(i).getString("id")+"'";
		    						String url1="";
		    						try {
		    							url1 = HttpUtil.BASE_URL+"SelectUser?sql="+URLEncoder.encode(sql);
		    						} catch (Exception e) {}
		    						String jsonString = HttpUtil.Post(url1);
		    						JSONArray array = JSONArray.fromObject(jsonString);
		    						ContactInfo contactInfo = new ContactInfo(jsonArray.getJSONObject(i).getString("id"), array.getJSONObject(0).getString("u_headurl"), array.getJSONObject(0).getString("u_name"), jsonArray.getJSONObject(i).getJSONArray("chat"));
		    						contactInfos.add(contactInfo);
		    					}
		    				}
		    				mHandler.sendEmptyMessage(2);
		            }
		        }, 1000, 1000);
			}
		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	 public void onClick(View v) {
	        int id=v.getId();
	        switch (id){
	            case R.id.img_msg:
	            	initpic();
	            	img_msg.setImageDrawable(getResources().getDrawable(R.drawable.pic_02));
	            	tv_title.setText("消息");
	            	address_list.setVisibility(View.VISIBLE);
	                break;
	            case R.id.img_contact:
	            	initpic();
	            	img_contact.setImageDrawable(getResources().getDrawable(R.drawable.pic_04));
	            	tv_title.setText("通讯录");
	            	address_list.setVisibility(View.VISIBLE);
	                break;
	            case R.id.img_find:
	            	initpic();
	            	tv_title.setText("发现");
	            	img_find.setImageDrawable(getResources().getDrawable(R.drawable.pic_06));
	            	find.setVisibility(View.VISIBLE);
	            	break;
	            case R.id.img_my:
	            	initpic();
	            	tv_title.setText("个人中心");
	        		SharedPreferences sharedPreferences= getSharedPreferences("usermsg", 
	        				Activity.MODE_PRIVATE); 
	        				// 使用getString方法获得value，注意第2个参数是value的默认值 
	        		u_id =sharedPreferences.getString("u_id", ""); 
	        		String u_name =sharedPreferences.getString("u_name", ""); 
	        		String u_email =sharedPreferences.getString("u_email", "");; 
	        		String u_phone =sharedPreferences.getString("u_phone", ""); 
	        		String u_password =sharedPreferences.getString("u_password", ""); 
	        		String img =sharedPreferences.getString("img", ""); 
	        		tv_id.setText("飓风号："+u_id);
	        		tv_username.setText(u_name);
	        		tv_userphone.setText(u_phone);
	        		tv_useremail.setText(u_email);
	            	ImgTask imgtask = new ImgTask(img_head);
	            	imgtask.execute(img);
	            	
	            	btn_add.setVisibility(View.GONE);
	            	tv_title.setGravity(Gravity.CENTER);
	            	img_my.setImageDrawable(getResources().getDrawable(R.drawable.pic_08));
	            	my.setVisibility(View.VISIBLE);
	            	img_logo.setVisibility(View.INVISIBLE);
	                break;
	            case R.id.btn_relogin:
	            	Login.backflag=1;
					Intent Intent=new Intent(Main.this,Login.class);
					startActivity(Intent);	
					finish();
	            	break;
	            case R.id.btn_logout:
	            	Login.backflag=0;
	            	SharedPreferences mySharedPreferences= getSharedPreferences("usermsg", 
	    		 	Activity.MODE_PRIVATE); 
	    		 	//实例化SharedPreferences.Editor对象（第二步） 
	    		 	SharedPreferences.Editor editor = mySharedPreferences.edit(); 
	    		 	//用putString的方法保存数据 
	    		 	editor.putString("u_id", ""); 
	    		 	editor.putString("u_name", ""); 
	    		 	editor.putString("u_email", ""); 
	    		 	editor.putString("u_phone", ""); 
	    		 	editor.putString("u_password", ""); 
	    		 	editor.putString("img", ""); 
	    		 	//提交当前数据 
	    		 	editor.commit(); 
	    		 	Intent Intent1=new Intent(Main.this,Login.class);
					startActivity(Intent1);	
					finish();
	            	break;
	            case R.id.btn_change_name:
	            	Change.state="用户名";
	            	Intent Intent2=new Intent(Main.this,Change.class);
					startActivity(Intent2);	
	                break;
	            case R.id.btn_changeemail:
	            	Change.state="邮箱";
	            	Intent Intent3=new Intent(Main.this,Change.class);
					startActivity(Intent3);	
	                break;
	            case R.id.btn_changephone:
	            	Change.state="手机号";
	            	Intent Intent4=new Intent(Main.this,Change.class);
					startActivity(Intent4);	
	                break;
	            case R.id.btn_repwd:
	            	Change.state="密码";
	            	Intent Intent5=new Intent(Main.this,Change.class);
					startActivity(Intent5);	
	                break;
	            case R.id.btn_add:
	            	Intent Intent6=new Intent(Main.this,SearchActivity.class);
					startActivity(Intent6);	
					break;
	            case R.id.about:
	            	Intent Intent7=new Intent(Main.this,Copyright.class);
					startActivity(Intent7);	
					break;
	            default:
	                break;
	        }
	    }
	 public void initpic(){
     	img_msg.setImageDrawable(getResources().getDrawable(R.drawable.pic_01));
     	img_contact.setImageDrawable(getResources().getDrawable(R.drawable.pic_03));
     	img_find.setImageDrawable(getResources().getDrawable(R.drawable.pic_05));
     	img_my.setImageDrawable(getResources().getDrawable(R.drawable.pic_07));
     	btn_add.setVisibility(View.VISIBLE);
     	tv_title.setGravity(Gravity.LEFT);
     	my.setVisibility(View.GONE);
     	address_list.setVisibility(View.GONE);
     	img_logo.setVisibility(View.VISIBLE);
     	find.setVisibility(View.GONE);
	 }
}
