package cn.app.jufeng;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Utils.MD5Utils;
import Web.WebServicePost;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import bean.LoginInfo;
import cn.app.jufeng.R;
import net.sf.json.JSONObject;

public class Login extends Activity {
	private Button btn_login;
	private Button btn_findpwd;
	private Button btn_register;
	private ImageView btn_back;
	private TextView username;
	private TextView password;
	private ProgressDialog dialog;
    private String infoString;
    public static String info;
    public static int backflag=0;
    private ConnectivityManager cm ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login=(Button)findViewById(R.id.btn_login);
        username=(TextView)findViewById(R.id.et_username);
        password=(TextView)findViewById(R.id.et_pwd);
        btn_back=(ImageView)findViewById(R.id.btn_back);
        if(backflag==0){
	    	btn_back.setVisibility(View.GONE);
	    }else{
	    	btn_back.setVisibility(View.VISIBLE);
	    }
        btn_login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				login();
			}
		});
        btn_findpwd=(Button)findViewById(R.id.btn_findpwd);
		btn_findpwd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent Intent=new Intent(Login.this,Findpwd.class);
				startActivity(Intent);	
			}
		});
		btn_register=(Button)findViewById(R.id.btn_register);
		btn_register.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent Intent=new Intent(Login.this,Register.class);
					startActivity(Intent);
				}
			});
		btn_back=(ImageView)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Login.this,Main.class);
				startActivity(intent);
				finish();
			}
		});
	}
	 @Override
	    //��׿��д���ؼ��¼�
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if(keyCode==KeyEvent.KEYCODE_BACK){
				finish();
	        }
	        return true;
	    }

	protected void login() {
		// TODO Auto-generated method stub
    	if(username.getText().toString()==null||password.getText().toString()==null||username.getText().toString().equals("")||password.getText().toString().equals("")){
    		Toast.makeText(Login.this,"�û��������벻��Ϊ��", Toast.LENGTH_SHORT).show();
    	}else{
			dialog = new ProgressDialog(Login.this);
	        dialog.setTitle("���ڵ�¼");
	        dialog.setMessage("���Ժ�");
	        dialog.setCancelable(false);//���ÿ���ͨ��back��ȡ��
	        dialog.show();
	        //�������̣߳��ֱ����Get��Post��������
            new Thread(new MyThread()).start();
    	}
	}
	 public class MyThread implements Runnable{
	        @Override
	        public void run() {
	            //����UI��ʹ��runOnUiThread()����
	        	String userName=username.getText().toString();
	        	String passWord=MD5Utils.encode(MD5Utils.encode(password.getText().toString()));
            	try {
					infoString = WebServicePost.executeHttpPost("username=" + URLEncoder.encode(userName,"UTF-8") + "&password=" + URLEncoder.encode(passWord,"UTF-8"),"Login");
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
	            	boolean flag=isConn(Login.this);

	            	if(flag==false){
	            		Toast.makeText(Login.this,"�����쳣", Toast.LENGTH_SHORT).show();
	            		setNetworkMethod(Login.this);
	            	}else{
	            		
		                if(response.equals("false")){
		                    Toast.makeText(Login.this,"�û������������", Toast.LENGTH_SHORT).show();
		                }else {
		                	Toast.makeText(Login.this,"��¼�ɹ�", Toast.LENGTH_SHORT).show();
		                    info=response;
		                    if(!(info.equals(null))){
	            			JSONObject jsonobj = new JSONObject();
	            		 	JsonObject obj = new JsonParser().parse(info).getAsJsonObject();
	            		 	JsonArray msg=new JsonArray();
	            		 	String u_id="";
	            		 	String u_name="";
	            		 	String u_email="";
	            		 	String u_phone="";
	            		 	String img="";
	            		 	String u_password="";
	            		 	try{
	            		 		msg=(JsonArray) obj.get("data").getAsJsonArray();
	            		 		u_id=((JsonObject)msg.get(0)).get("u_id").toString().replace("\"", "");
	            		 		u_name=((JsonObject)msg.get(0)).get("u_name").toString().replace("\"", "");
	            		 		u_email=((JsonObject)msg.get(0)).get("u_email").toString().replace("\"", "");
	            		 		u_phone=((JsonObject)msg.get(0)).get("u_phone").toString().replace("\"", "");
	            		 		img=((JsonObject)msg.get(0)).get("img").toString().replace("\"", "");
	            		 		u_password=((JsonObject)msg.get(0)).get("u_password").toString().replace("\"", "");
	            		 		LoginInfo.UserId=((JsonObject)msg.get(0)).get("u_id").toString().replace("\"", "");
	            		 		LoginInfo.UserHead=((JsonObject)msg.get(0)).get("img").toString().replace("\"", "");
	            	        }catch(Exception e){
	            	        	
	            	        }
//		                    Toast.makeText(Login.this,u_id+":"+u_name+":"+u_email+":"+u_phone+":"+img+":"+pwd, Toast.LENGTH_SHORT).show();
	            		 	//ʵ����SharedPreferences���󣨵�һ���� 
	            		 	SharedPreferences mySharedPreferences= getSharedPreferences("usermsg", 
	            		 	Activity.MODE_PRIVATE); 
	            		 	//ʵ����SharedPreferences.Editor���󣨵ڶ����� 
	            		 	SharedPreferences.Editor editor = mySharedPreferences.edit(); 
	            		 	//��putString�ķ����������� 
	            		 	editor.putString("u_id", u_id); 
	            		 	editor.putString("u_name", u_name); 
	            		 	editor.putString("u_email", u_email); 
	            		 	editor.putString("u_phone", u_phone); 
	            		 	editor.putString("u_password", u_password); 
	            		 	editor.putString("img", img); 

	            		 	//�ύ��ǰ���� 
	            		 	editor.commit(); 
	                        Intent intent = new Intent(Login.this,Main.class);
	                        startActivity(intent);
	                        finish();
		                    }
		                }
	                }
	                
	                dialog.dismiss();
	            }
	        });
	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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

public class BorderTextView extends EditText {
		
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		// ���߿���Ϊ��ɫ
		paint.setColor(android.graphics.Color.BLACK);
		paint.setStrokeWidth(1);
		// ��TextView��4����
		canvas.drawLine(0, 0, this.getWidth() - 1, 0, paint);
		canvas.drawLine(0, 0, 0, this.getHeight() - 1, paint);
		canvas.drawLine(this.getWidth() - 1, 0, this.getWidth() - 1,
				this.getHeight() - 1, paint);
		canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1,
				this.getHeight() - 1, paint);
		setBackgroundColor(Color.TRANSPARENT);				
	}
 
	private Paint mPaint;
 
		public BorderTextView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			mPaint = new Paint();
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setColor(Color.GREEN);
			
	      setBackgroundColor(Color.TRANSPARENT);//͸������
	 
		}
	 
		public BorderTextView(Context context, AttributeSet attrs) {
			super(context, attrs);
			// TODO Auto-generated constructor stub
			mPaint = new Paint();
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setColor(Color.GRAY); 
			setBackgroundColor(Color.TRANSPARENT);//����͸��
		}
	}
	public static boolean isConn(Context context){
	    boolean bisConnFlag=false;
	    ConnectivityManager conManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo network = conManager.getActiveNetworkInfo();
	    if(network!=null){
	        bisConnFlag=conManager.getActiveNetworkInfo().isAvailable();
	    }
	    return bisConnFlag;
	} 
	public static void setNetworkMethod(final Context context){
	    //��ʾ�Ի���  
	    AlertDialog.Builder builder=new AlertDialog.Builder(context);
	    builder.setTitle("����������ʾ").setMessage("�������Ӳ�����,�Ƿ��������?").setPositiveButton("����", new DialogInterface.OnClickListener() {
	  
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            // TODO Auto-generated method stub  
	            Intent intent=null;
	            //�ж��ֻ�ϵͳ�İ汾  ��API����10 ����3.0�����ϰ汾  
	            if(Build.VERSION.SDK_INT>10){
	                intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
	            }else{
	                intent = new Intent();
	                ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
	                intent.setComponent(component);
	                intent.setAction("android.intent.action.VIEW");
	            }
	            context.startActivity(intent);
	        }
	    }).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
	  
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            // TODO Auto-generated method stub  
	            dialog.dismiss();
	        }
	    }).show();
	}
}
