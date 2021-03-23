package cn.app.jufeng;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import Utils.MD5Utils;
import Web.WebServicePost;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import cn.app.jufeng.R;

public class Findpwd extends Activity {
	
	private int i=20;
	private int flag=0;	
	private TextView btn_code;
	private EditText et_name;
	private EditText et_email;
	private EditText et_pwd;
	private EditText et_repwd;
	private EditText et_code;
	private Button btn_findpwd;
	private ProgressDialog dialog;
	private ImageView btn_back;
	
	private String infoString;
	private String FindinfoString;
	myThread th;
	Handler mHandler  = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 10:
				btn_code.setText(i--+"s������");
				btn_code.setTextColor(Color.parseColor("#808080"));
				break;
			case 1:
				btn_code.setText("��ȡ��֤��");
				btn_code.setTextColor(Color.parseColor("#000038"));
				i=20;
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_findpwd);
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
			    		Toast.makeText(Findpwd.this,"������]����д����", Toast.LENGTH_SHORT).show();
			    		flag=0;
			    	}
					else{
						if(checkEmail(et_email.getText().toString())==false){
							Toast.makeText(Findpwd.this,"��������ȷ������", Toast.LENGTH_SHORT).show();
							flag=0;
						}else{
							th = new myThread();
							th.start();
						}
			    	}
					
				}
			}
			
		});
		btn_findpwd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(checkEmail(et_email.getText().toString())==false){
					Toast.makeText(Findpwd.this,"��������ȷ������", Toast.LENGTH_SHORT).show();
				}
				if(et_pwd.getText().toString().equals("")||et_repwd.getText().toString().equals("")){
					Toast.makeText(Findpwd.this,"���벻һ�¡����벻��Ϊ��", Toast.LENGTH_SHORT).show();
				}
				if(et_pwd.getText().toString().equals(et_repwd.getText().toString())==false){
					Toast.makeText(Findpwd.this,"�������벻һ��", Toast.LENGTH_SHORT).show();
				}
				if(et_code.getText().toString().equals("")){
					Toast.makeText(Findpwd.this,"��֤�벻��Ϊ��", Toast.LENGTH_SHORT).show();
				}
				if(MD5Utils.encode(MD5Utils.encode(et_code.getText().toString())).endsWith(infoString)==false){
					Toast.makeText(Findpwd.this,"��֤�벻��ȷ", Toast.LENGTH_SHORT).show();
				}
				if(et_name.getText().toString().equals("")){
					Toast.makeText(Findpwd.this,"쫷�Ų���Ϊ��", Toast.LENGTH_SHORT).show();
				}
				if(checkEmail(et_email.getText().toString())!=false&&et_pwd.getText().toString().equals("")==false&&et_repwd.getText().toString().equals("")==false&&et_pwd.getText().toString().equals(et_repwd.getText().toString())!=false&&et_code.getText().toString().equals("")==false&&MD5Utils.encode(MD5Utils.encode(et_code.getText().toString())).endsWith(infoString)!=false&&et_name.getText().toString().equals("")==false){
					Findpwd();
				}
			}
			
		});
	}
	
	private void Findpwd() {
		// TODO Auto-generated method stub
		dialog = new ProgressDialog(Findpwd.this);
        dialog.setTitle("�����޸���...");
        dialog.setMessage("���Ժ�");
        dialog.setCancelable(false);//���ÿ���ͨ��back��ȡ��
        dialog.show();
        //�������̣߳��ֱ����Get��Post��������
        new Thread(new FindThread()).start();
	}
	
	public class FindThread implements Runnable{
        @Override
        public void run() {
            //����UI��ʹ��runOnUiThread()����
        	String userName=et_name.getText().toString();
        	String passWord=MD5Utils.encode(MD5Utils.encode(et_pwd.getText().toString()));
        	String email=et_email.getText().toString();
        	try {
        		FindinfoString = WebServicePost.executeHttpPost("username=" + URLEncoder.encode(userName,"UTF-8") + "&password=" + URLEncoder.encode(passWord,"UTF-8") + "&email=" + URLEncoder.encode(email,"UTF-8"),"Findpwd");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//��ȡ���������ص�����
        	showFindResponse(FindinfoString);
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
		btn_findpwd=(Button)findViewById(R.id.btn_findpwd);
		btn_back=(ImageView)findViewById(R.id.btn_back);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.findpwd, menu);
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
	            //����UI��ʹ��runOnUiThread()����
	        	String email=et_email.getText().toString();
            	try {
					infoString = WebServicePost.executeHttpPost("email=" + URLEncoder.encode(email,"UTF-8") + "&code=0","GetCode");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//��ȡ���������ص�����
	            showResponse(infoString);
	        }
	    }
	 private void showResponse(final String response){
	        runOnUiThread(new Runnable() {
	            //����UI
	            @Override
	            public void run() {
	            	boolean flag=Login.isConn(Findpwd.this);

	            	if(flag==false){
	            		Toast.makeText(Findpwd.this,"�����쳣", Toast.LENGTH_SHORT).show();
	            		Login.setNetworkMethod(Findpwd.this);
	            	}else{
	            		
		                if(response.equals("false")){
		                    Toast.makeText(Findpwd.this,"����ʧ�ܣ�������", Toast.LENGTH_SHORT).show();
		                }else {
		                	Toast.makeText(Findpwd.this,"���ͳɹ�", Toast.LENGTH_SHORT).show();
	                    }
		            }
	            }
	        });
	    }

	 private void showFindResponse(final String response){
	        runOnUiThread(new Runnable() {
	            //����UI
	            @Override
	            public void run() {
	            	boolean flag=Login.isConn(Findpwd.this);

	            	if(flag==false){
	            		Toast.makeText(Findpwd.this,"�����쳣", Toast.LENGTH_SHORT).show();
	            		Login.setNetworkMethod(Findpwd.this);
	            	}else{
	            		
		                if(response.equals("false")){
		                	dialog.dismiss();
		                    Toast.makeText(Findpwd.this,"�޸�ʧ�ܣ�����������Ϣ", Toast.LENGTH_SHORT).show();
		                }else {
		                	dialog.dismiss();
		                	Toast.makeText(Findpwd.this,"�޸ĳɹ�", Toast.LENGTH_SHORT).show();
		                	
		                	Timer timer=new Timer();
		             	    TimerTask task=new TimerTask(){
		             	        public void run(){
		             	        	overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
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
			//��ĸ��ͷ��@�����ĸ�����֣�����ӵ㣬������ĸ������
			String regex2 = "[a-zA-Z]+[a-zA-Z0-9_]*@[a-zA-Z0-9]+[.][a-zA-Z0-9]+[.][a-zA-Z0-9]+";
			//..........��regex1�����ϣ������.��������ĸ��ɵĺ�׺
			String regex3 = "[0-9_]*@[a-zA-Z0-9]+[.][a-zA-Z0-9]+";
			
			if(email.matches(regex1) || email.matches(regex2)||email.matches(regex3)){
				
				return true;
			}
			else{
				
				return false;
			}
		}
}
