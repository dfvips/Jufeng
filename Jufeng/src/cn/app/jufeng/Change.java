package cn.app.jufeng;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import Utils.MD5Utils;
import Web.WebServicePost;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.app.jufeng.Login.MyThread;

public class Change extends Activity {
	
	public static String state;
	private View changname;
	private View changpassword;
	private View changemail;
	private View changephone;
	private Button btn_cancle;
	private Button btn_sure;
	private TextView tv_title;
	private EditText et_changname;
	private EditText et_changemail;
	private EditText et_changphone;
	private EditText et_org_pwd;
	private EditText et_new_pwd;
	private EditText et_re_pwd;
	public String infoString;
	public static String info="false";
	public SharedPreferences mySharedPreferences;
	public SharedPreferences.Editor editor;
	public String u_id;
	public String sql="";
	public boolean changeflag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change);
		init();
		mySharedPreferences= getSharedPreferences("usermsg", 
    		 	Activity.MODE_PRIVATE); 
    		 	//实例化SharedPreferences.Editor对象（第二步） 
    		 	editor = mySharedPreferences.edit(); 
    		 	u_id=mySharedPreferences.getString("u_id", ""); 
		if(state.equals("用户名")){
			changname.setVisibility(View.VISIBLE);
		}
		if(state.equals("邮箱")){
			changemail.setVisibility(View.VISIBLE);
		}
		if(state.equals("手机号")){
			changephone.setVisibility(View.VISIBLE);
		}
		if(state.equals("密码")){
			changpassword.setVisibility(View.VISIBLE);
		}
		tv_title.setText("设置新"+state);
		btn_cancle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		btn_sure.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(state.equals("用户名")){
					boolean a=et_changname.getText().toString().equals(Main.tv_username.getText().toString());
					boolean b=et_changname.getText().toString().equals("");
					if(a){
			    		Toast.makeText(Change.this,"您还没有修改用户名", Toast.LENGTH_SHORT).show();
					}
					if(b){
						Toast.makeText(Change.this,"用户名不能为空", Toast.LENGTH_SHORT).show();
					}
					if(!a&&!b){
						new Thread(new MyThread()).start();
						try {
							sql="u_id=" + URLEncoder.encode(u_id,"UTF-8") + "&username=" + URLEncoder.encode(et_changname.getText().toString(),"UTF-8");
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if(!changeflag){
							Main.tv_username.setText(et_changname.getText().toString());
		        		 	editor.putString("u_name", et_changname.getText().toString()); 
		        		 	editor.commit(); 
							finish();
						}else{
				     		Toast.makeText(Change.this,"修改失败", Toast.LENGTH_SHORT).show();
						}
					}
				}
				if(state.equals("邮箱")){
					boolean a=Register.checkEmail(et_changemail.getText().toString());
					boolean b=et_changemail.getText().toString().equals(Main.tv_useremail.getText().toString());
					boolean c=et_changemail.getText().toString().equals("");
					if(b){
			    		Toast.makeText(Change.this,"您还没有修改您的邮箱", Toast.LENGTH_SHORT).show();
					}
					if(!a){
						Toast.makeText(Change.this,"您输入的邮箱格式有误", Toast.LENGTH_SHORT).show();
					}
					if(c){
						Toast.makeText(Change.this,"邮箱不能为空", Toast.LENGTH_SHORT).show();
					}
					if(a&&!b&&!c){
						new Thread(new MyThread()).start();
						try {
							sql="u_id=" + URLEncoder.encode(u_id,"UTF-8") + "&email=" + URLEncoder.encode(et_changemail.getText().toString(),"UTF-8");
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if(!changeflag){
							Main.tv_useremail.setText(et_changemail.getText().toString());
		        		 	editor.putString("u_email", et_changemail.getText().toString()); 
		        		 	editor.commit(); 
							finish();
						}else{
				     		Toast.makeText(Change.this,"修改失败", Toast.LENGTH_SHORT).show();
						}
					}
				}
				if(state.equals("手机号")){
					boolean a=Register.checkPhone(et_changphone.getText().toString());
					boolean b=et_changphone.getText().toString().equals(Main.tv_userphone.getText().toString());
					boolean c=et_changphone.getText().toString().equals("");
					if(b){
			    		Toast.makeText(Change.this,"您还没有修改您的手机号", Toast.LENGTH_SHORT).show();
					}
					if(!a){
						Toast.makeText(Change.this,"您输入的手机号格式有误", Toast.LENGTH_SHORT).show();
					}
					if(c){
						Toast.makeText(Change.this,"手机号不能为空", Toast.LENGTH_SHORT).show();
					}
					if(a&&!b&&!c){
						new Thread(new MyThread()).start();
						try {
							sql="u_id=" + URLEncoder.encode(u_id,"UTF-8") + "&phone=" + URLEncoder.encode(et_changphone.getText().toString(),"UTF-8");
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if(!changeflag){
							Main.tv_userphone.setText(et_changphone.getText().toString());
		        		 	editor.putString("u_phone", et_changphone.getText().toString()); 
		        		 	editor.commit(); 
							finish();
						}else{
				     		Toast.makeText(Change.this,"修改失败", Toast.LENGTH_SHORT).show();
						}
					}
				}
				if(state.equals("密码")){
					String orgpwd=mySharedPreferences.getString("u_password", ""); 
					boolean a=et_org_pwd.getText().toString().equals("");
					boolean b=et_new_pwd.getText().toString().equals("");
					boolean c=et_re_pwd.getText().toString().equals("");
					boolean d=et_re_pwd.getText().toString().equals(et_new_pwd.getText().toString());
					boolean e=MD5Utils.encode(MD5Utils.encode(et_org_pwd.getText().toString())).equals(orgpwd);
					if(a){
						Toast.makeText(Change.this,"原密码不能为空", Toast.LENGTH_SHORT).show();
					}
					if(b){
						Toast.makeText(Change.this,"新密码不能为空", Toast.LENGTH_SHORT).show();
					}
					if(c){
						Toast.makeText(Change.this,"确认密码不能为空", Toast.LENGTH_SHORT).show();
					}
					if(!d){
						Toast.makeText(Change.this,"两次密码不一致", Toast.LENGTH_SHORT).show();
					}
					if(!e){
						Toast.makeText(Change.this,"原密码错误", Toast.LENGTH_SHORT).show();
					}
					if(!a&&!b&&!c&&d&&e){
						Login.backflag=0;
						new Thread(new MyThread()).start();
						try {
							sql="u_id=" + URLEncoder.encode(u_id,"UTF-8") + "&password=" + URLEncoder.encode(MD5Utils.encode(MD5Utils.encode(et_new_pwd.getText().toString())),"utf-8");
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if(!changeflag){
							editor.putString("u_id", ""); 
			    		 	editor.putString("u_name", ""); 
			    		 	editor.putString("u_email", ""); 
			    		 	editor.putString("u_phone", ""); 
			    		 	editor.putString("u_password", ""); 
			    		 	editor.putString("img", ""); 
			    		 	editor.commit(); 
			    		 	Main.main.finish();
							Intent intent=new Intent(Change.this,Login.class);
							startActivity(intent);
							finish();
						}else{
				     		Toast.makeText(Change.this,"修改失败", Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		});
	}

	private void init() {
		// TODO Auto-generated method stub
		changname=findViewById(R.id.changname);
		changpassword=findViewById(R.id.changpassword);
		changemail=findViewById(R.id.changemail);
		changephone=findViewById(R.id.changephone);
		btn_cancle=(Button)findViewById(R.id.btn_cancle);
		btn_sure=(Button)findViewById(R.id.btn_sure);
		tv_title=(TextView)findViewById(R.id.tv_title);
		changname.setVisibility(View.GONE);
		changemail.setVisibility(View.GONE);
		changephone.setVisibility(View.GONE);
		changpassword.setVisibility(View.GONE);
		et_changname=(EditText)findViewById(R.id.et_changname);
		et_changemail=(EditText)findViewById(R.id.et_changemail);
		et_changphone=(EditText)findViewById(R.id.et_changphone);
		et_org_pwd=(EditText)findViewById(R.id.et_org_pwd);
		et_new_pwd=(EditText)findViewById(R.id.et_new_pwd);
		et_re_pwd=(EditText)findViewById(R.id.et_re_pwd);
	}
	 public class MyThread implements Runnable{
	        @Override
	        public void run() {
	            //更新UI，使用runOnUiThread()方法
				infoString = WebServicePost.executeHttpPost(sql,"Change");
	            showResponse(infoString);
	        }
	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu., menu);
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
	 private void showResponse(final String response){
	        runOnUiThread(new Runnable() {
	            //更新UI
	            @Override
	            public void run() {
	            	boolean flag=Login.isConn(Change.this);
	            	if(flag==false){
	            		Toast.makeText(Change.this,"网络异常", Toast.LENGTH_SHORT).show();
	            		Login.setNetworkMethod(Change.this);
	            	} 
	            	if(response.equals("false")){
	                    changeflag=false;
	                }
	            }
	        });
	 }
}
