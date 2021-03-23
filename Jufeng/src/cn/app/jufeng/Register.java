package cn.app.jufeng;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Utils.MD5Utils;
import Web.WebServicePost;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import tools.HttpUtil;

public class Register extends Activity {

	private int i=20;
	private int flag=0;	
	private TextView btn_code;
	private EditText et_name;
	private EditText et_email;
	private EditText et_pwd;
	private EditText et_repwd;
	private EditText et_code;
	private EditText et_phone;
	private Button btn_register;
	private ProgressDialog dialog;
	private ImageView btn_back;
	
	private String infoString;
	private String RegisterinfoString;
	myThread th;
	Handler mHandler  = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 10:
				btn_code.setText(i--+"s后重试");
				btn_code.setTextColor(Color.parseColor("#808080"));
				break;
			case 1:
				btn_code.setText("获取验证码");
				btn_code.setTextColor(Color.parseColor("#000038"));
				i=20;
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		init();
		btn_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		btn_code.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(flag==0){
					flag=1;
					if(et_email.getText().toString()==null||et_email.getText().toString().equals("")){
			    		Toast.makeText(Register.this,"您好像沒有填写邮箱", Toast.LENGTH_SHORT).show();
			    		flag=0;
			    	}
					else{
						if(checkEmail(et_email.getText().toString())==false){
							Toast.makeText(Register.this,"请输入正确的邮箱", Toast.LENGTH_SHORT).show();
							flag=0;
						}else{
							th = new myThread();
							th.start();
						}
			    	}
					
				}
			}
			
		});
		btn_register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(checkEmail(et_email.getText().toString())==false){
					Toast.makeText(Register.this,"请输入正确的邮箱", Toast.LENGTH_SHORT).show();
				}
				if(et_pwd.getText().toString().equals("")||et_repwd.getText().toString().equals("")){
					Toast.makeText(Register.this,"密码不一致、密码不能为空", Toast.LENGTH_SHORT).show();
				}
				if(et_pwd.getText().toString().equals(et_repwd.getText().toString())==false){
					Toast.makeText(Register.this,"两次密码不一致", Toast.LENGTH_SHORT).show();
				}
				if(et_code.getText().toString().equals("")){
					Toast.makeText(Register.this,"验证码不能为空", Toast.LENGTH_SHORT).show();
				}
				else if(MD5Utils.encode(MD5Utils.encode(et_code.getText().toString())).endsWith(infoString)==false){
					Toast.makeText(Register.this,"验证码不正确", Toast.LENGTH_SHORT).show();
				}
				if(et_phone.getText().toString().equals("")){
					Toast.makeText(Register.this,"手机号不能为空", Toast.LENGTH_SHORT).show();
				}
				if(checkPhone(et_phone.getText().toString())==false){
					Toast.makeText(Register.this,"手机号格式不正确", Toast.LENGTH_SHORT).show();
				}
				if(checkEmail(et_email.getText().toString())!=false&&et_pwd.getText().toString().equals("")==false&&et_repwd.getText().toString().equals("")==false&&et_pwd.getText().toString().equals(et_repwd.getText().toString())!=false&&et_code.getText().toString().equals("")==false&&MD5Utils.encode(MD5Utils.encode(et_code.getText().toString())).endsWith(infoString)!=false&&et_phone.getText().toString().equals("")==false&&checkPhone(et_phone.getText().toString())!=false){
					Register();
				}
			}
			
		});
	}
	
	private void Register() {
		// TODO Auto-generated method stub
		dialog = new ProgressDialog(Register.this);
        dialog.setTitle("正在注册中...");
        dialog.setMessage("请稍后");
        dialog.setCancelable(false);//设置可以通过back键取消
        dialog.show();
        //设置子线程，分别进行Get和Post传输数据
        new Thread(new RegisterThread()).start();
	}
	
	public class RegisterThread implements Runnable{
        @Override
        public void run() {
            //更新UI，使用runOnUiThread()方法
        	String userName=et_name.getText().toString();
        	String passWord=MD5Utils.encode(MD5Utils.encode(et_pwd.getText().toString()));
        	String email=et_email.getText().toString();
        	String phone=et_phone.getText().toString();
        	try {
        		RegisterinfoString = WebServicePost.executeHttpPost("username=" + URLEncoder.encode(userName,"UTF-8") + "&password=" + URLEncoder.encode(passWord,"UTF-8") + "&email=" + URLEncoder.encode(email,"UTF-8") + "&phone=" + URLEncoder.encode(phone,"UTF-8"),"Register");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//获取服务器返回的数据
        	showFindResponse(RegisterinfoString);
        }
    }
	private void init() {
		// TODO Auto-generated method stub
		btn_code=(TextView)findViewById(R.id.tv_code);
		et_email=(EditText)findViewById(R.id.et_email);
		et_code=(EditText)findViewById(R.id.et_code);
		et_name=(EditText)findViewById(R.id.et_name);
		et_pwd=(EditText)findViewById(R.id.et_pwd);
		et_repwd=(EditText)findViewById(R.id.et_repwd);
		et_phone=(EditText)findViewById(R.id.et_phone);
		btn_register=(Button)findViewById(R.id.btn_register);
		btn_back=(ImageView)findViewById(R.id.btn_back);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
	public int Countdown(int lin)throws InterruptedException{
		
        while (lin > 0){
            TimeUnit.SECONDS.sleep(1);
        }
       return lin;
    }
	class myThread extends Thread{
		@Override
		public void run() {
			new Thread(new GetCode()).start();
			Timer timer = new Timer();
	        timer.schedule(new TimerTask() {
	            public void run() {		
	            	mHandler.sendEmptyMessage(10);
	            	if(i==0){
	            		mHandler.sendEmptyMessage(1);
	            		flag=0;
	            		this.cancel();
		        		th.currentThread().interrupt();
		        		th.interrupted();
		        	}
	            }
	        }, 0, 1000);
		};
	}
	 public class GetCode implements Runnable{
	        @Override
	        public void run() {
	            //更新UI，使用runOnUiThread()方法
	        	String email=et_email.getText().toString();
            	try {
					infoString = WebServicePost.executeHttpPost("email=" + URLEncoder.encode(email,"UTF-8") + "&code=1","GetCode");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//获取服务器返回的数据
	            showResponse(infoString);
	        }
	    }
	 public class Success implements Runnable{
	        @Override
	        public void run() {
	            //更新UI，使用runOnUiThread()方法
	        	String email=et_email.getText().toString();
         	try {
				WebServicePost.executeHttpPost("email=" + URLEncoder.encode(email,"UTF-8") + "&code=2","GetCode");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//获取服务器返回的数据
	        }
	    }
	 private void showResponse(final String response){
	        runOnUiThread(new Runnable() {
	            //更新UI
	            @Override
	            public void run() {
	            	boolean flag=Login.isConn(Register.this);

	            	if(flag==false){
	            		Toast.makeText(Register.this,"网络异常", Toast.LENGTH_SHORT).show();
	            		Login.setNetworkMethod(Register.this);
	            	}else{
	            		
		                if(response.equals("false")){
		                    Toast.makeText(Register.this,"发送失败，请重试", Toast.LENGTH_SHORT).show();
		                }else {
		                	Toast.makeText(Register.this,"发送成功", Toast.LENGTH_SHORT).show();
	                    }
		            }
	            }
	        });
	    }

	 private void showFindResponse(final String response){
	        runOnUiThread(new Runnable() {
	            //更新UI
	            @Override
	            public void run() {
	            	boolean flag=Login.isConn(Register.this);

	            	if(flag==false){
	            		Toast.makeText(Register.this,"网络异常", Toast.LENGTH_SHORT).show();
	            		Login.setNetworkMethod(Register.this);
	            	}else{
	            		
		                if(response.equals("false")){
		                	dialog.dismiss();
		                    Toast.makeText(Register.this,"注冊失败，请重试", Toast.LENGTH_SHORT).show();
		                }else {
		                	dialog.dismiss();
		                	Toast.makeText(Register.this,"注册成功", Toast.LENGTH_SHORT).show();
//		                	new Thread(){
//		                		public void run(){
//		                			String url = HttpUtil.BASE_URL+"res_json?userId="+注册成功的id;
//		                			HttpUtil.Post(url);
//		                		}
//		                		
//		                	}.start();
		                	new Thread(new Success()).start();
		                	Timer timer=new Timer();
		             	    TimerTask task=new TimerTask(){
		             	        public void run(){
		             	        	overridePendingTransition(android.R.anim.linear_interpolator,android.R.anim.overshoot_interpolator);
		             	        	finish();
		             	        }
		             	    };
		             	    timer.schedule(task, 2000);
	                    }
		            }
	            }
	        });
	    }

	 
		public static boolean checkEmail(String email){
			
			String regex1 = "[a-zA-Z]+[a-zA-Z0-9_]*@[a-zA-Z0-9]+[.][a-zA-Z0-9]+";
			String regex2 = "[a-zA-Z]+[a-zA-Z0-9_]*@[a-zA-Z0-9]+[.][a-zA-Z0-9]+[.][a-zA-Z0-9]+";
			String regex3 = "[0-9_]*@[a-zA-Z0-9]+[.][a-zA-Z0-9]+";
			
			if(email.matches(regex1) || email.matches(regex2)||email.matches(regex3)){
				
				return true;
			}
			else{
				
				return false;
			}
		}
		public static boolean checkPhone(String phone){
			String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
	        if(phone.length() != 11){
	        	return false;
	        }else{
	            Pattern p = Pattern.compile(regex);
	            Matcher m = p.matcher(phone);
	            boolean isMatch = m.matches();
	            if(isMatch){
	            	return true;
	            } else {
	            	return false;
	            }
	        }
		}
}
