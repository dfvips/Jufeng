package cn.app.jufeng;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import adapter.SearchAdapter;
import bean.UserInfo;
import tools.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SearchActivity extends Activity {
	private ImageView img_search_back;
	private EditText et_search;
	private ListView lv_search;
	private RelativeLayout rl_search;
	private List<UserInfo> userInfos;
	private SearchAdapter searchAdapter;
	private String ss="";
	
	Handler mHandler  = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if(userInfos != null){
					System.out.println(123);
					searchAdapter = new SearchAdapter(userInfos,SearchActivity.this);
					lv_search.setAdapter(searchAdapter);
				}
				break;
			case 2:
				searchAdapter.UpDate(userInfos);
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search);
		initView();
	}
	
	private void initView(){
		img_search_back = (ImageView) findViewById(R.id.img_search_back);
		et_search = (EditText) findViewById(R.id.et_search);
		lv_search = (ListView) findViewById(R.id.lv_search);
		rl_search = (RelativeLayout) findViewById(R.id.rl_search);
		userInfos = new ArrayList<UserInfo>();
		
		new Thread(){
			public void run(){
					mHandler.sendEmptyMessage(1);
			}
		}.start();
		
		img_search_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		et_search.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				ss = s.toString();
				
				if(ss.toString().equals("")){
					rl_search.setBackgroundColor(rl_search.getResources().getColor(R.drawable.light_gray));
					userInfos = new ArrayList<UserInfo>();
					mHandler.sendEmptyMessage(2);
				}else{
					rl_search.setBackgroundColor(Color.WHITE);
					new Thread(){
						public void run(){
							String sql = "select * from user_info where u_id like '%"+ss+"%' or u_name like '%"+ss+"%' or u_phone like '%"+ss+"%' or u_email like '%"+ss+"%'";
							System.out.println(sql);
							String url = HttpUtil.BASE_URL+"SelectUser?sql="+ URLEncoder.encode(sql);
							String jsonString = HttpUtil.Post(url);
							JSONArray jsonArray = JSONArray.fromObject(jsonString);
							userInfos = new ArrayList<UserInfo>();
							if(jsonArray.size()!=0){
								for(int i=0; i<jsonArray.size(); i++){
									JSONObject json = jsonArray.getJSONObject(i);
									UserInfo userInfo = new UserInfo(json.getString("u_id"), json.getString("u_headurl"), json.getString("u_name"), json.getString("u_phone"), json.getString("u_email"));
									userInfos.add(userInfo);
								}
							}
							mHandler.sendEmptyMessage(2);
						}
					}.start();
				}
			}
		});
	}
	
	protected void onPause() {		
		super.onPause();
		finish();
	}
}
