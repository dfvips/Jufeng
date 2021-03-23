package adapter;

import java.util.List;

import Utils.ImgTask;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import bean.UserInfo;
import cn.app.jufeng.FriendAddActivity;
import cn.app.jufeng.R;

public class SearchAdapter extends BaseAdapter {
	private List<UserInfo> userInfos;
	private Context context;
	
	
	public SearchAdapter(List<UserInfo> userInfos, Context context) {
		super();
		this.userInfos = userInfos;
		this.context = context;
	}
	
	public void UpDate(List<UserInfo> list){
		userInfos.clear();
		this.notifyDataSetChanged();
		userInfos = list;
		this.notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return userInfos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return userInfos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView==null){
			convertView = View.inflate(context, R.layout.item_list_friend, null);
			holder = new ViewHolder();
			holder.img_view = (ImageView) convertView.findViewById(R.id.img_view);
			holder.tv_view = (TextView) convertView.findViewById(R.id.tv_view);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		convertView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				 Intent intent = new Intent(context,FriendAddActivity.class);
				 Bundle bundle = new Bundle();
				 bundle.putString("headurl", userInfos.get(position).getHeadurl());
				 bundle.putString("id", userInfos.get(position).getId());
				 bundle.putString("name", userInfos.get(position).getName());
				 bundle.putString("phone", userInfos.get(position).getPhone());
				 bundle.putString("email", userInfos.get(position).getEmail());
				 
				 intent.putExtras(bundle);
				 context.startActivity(intent);
			}
		});
		holder.tv_view.setText(userInfos.get(position).getName());
		ImgTask imgtask = new ImgTask(holder.img_view);
		imgtask.execute(userInfos.get(position).getHeadurl());
		return convertView;
	}
	class ViewHolder{
		ImageView img_view;
		TextView tv_view;
	}

}
