package cn.app.jufeng;

import java.util.Timer;
import java.util.TimerTask;

import Utils.MD5Utils;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import bean.LoginInfo;
import cn.app.jufeng.R;

public class SplashActivity extends Activity {
	
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		           WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_splash);
		SharedPreferences sharedPreferences= getSharedPreferences("usermsg", 
				Activity.MODE_PRIVATE); 
				// 使用getString方法获得value，注意第2个参数是value的默认值 
		String u_id =sharedPreferences.getString("u_id", ""); 
		String u_name =sharedPreferences.getString("u_name", ""); 
		String u_email =sharedPreferences.getString("u_email", "");; 
		String u_phone =sharedPreferences.getString("u_phone", ""); 
		String u_password =sharedPreferences.getString("u_password", ""); 
		String img =sharedPreferences.getString("img", ""); 
		if(!u_id.equals("")&&!u_name.equals("")&&!u_email.equals("")&&!u_phone.equals("")&&!u_password.equals("")&&!img.equals("")){
		 	LoginInfo.UserId=u_id;
		 	LoginInfo.UserHead=img;
			toMain();
		}else{
			String a=MD5Utils.encode(MD5Utils.encode("admin"));
			System.out.print(a);
			toLogin();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
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
	
	private void toLogin() {
	    Timer timer=new Timer();
	    TimerTask task=new TimerTask(){
	        public void run(){
	        	overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
	            Intent intent = new Intent(SplashActivity.this, Login.class);
	            startActivity(intent);
	            finish();
	        }
	    };
	    timer.schedule(task, 2500);
	}
	
	private void toMain() {
	    Timer timer=new Timer();
	    TimerTask task=new TimerTask(){
	        public void run(){
	        	overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
	            Intent intent = new Intent(SplashActivity.this, Main.class);
	            startActivity(intent);
	            finish();
	        }
	    };
	    timer.schedule(task, 2500);
	}
}
