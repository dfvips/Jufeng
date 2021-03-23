package adapter;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import Utils.ImgTask;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import bean.ContactInfo;
import cn.app.jufeng.ChatActivity;
import cn.app.jufeng.R;

public class ContactAdapter extends BaseAdapter {
	private List<ContactInfo> contactInfos;
	private Context context;
	private ContactCallBack callBack;
	public ContactAdapter(List<ContactInfo> contactInfos,Context context) {
		super();
		this.contactInfos = contactInfos;
		this.context = context;
		
	}
	
	public void UpData(List<ContactInfo> list){
		contactInfos.clear();
		this.notifyDataSetChanged();
		contactInfos = list;
		this.notifyDataSetChanged();
	}
	
	public void setData(List<ContactInfo> list){
		contactInfos = list;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return contactInfos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return contactInfos.get(arg0);
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
			convertView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					 Intent intent = new Intent(context,ChatActivity.class);
					 Bundle bundle = new Bundle();
					 bundle.putString("other_id", contactInfos.get(position).getId());
					 bundle.putString("other_headurl", contactInfos.get(position).getHeadurl());
					 bundle.putString("chat", contactInfos.get(position).getChat().toString());
					 intent.putExtras(bundle);
					 context.startActivity(intent);
				}
			});
			holder.tv_view.setText(contactInfos.get(position).getName());
			ImgTask imgtask = new ImgTask(holder.img_view);
			imgtask.execute(contactInfos.get(position).getHeadurl());
			return convertView;
		}else{
			holder = (ViewHolder) convertView.getTag();
			convertView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					 Intent intent = new Intent(context,ChatActivity.class);
					 Bundle bundle = new Bundle();
					 bundle.putString("other_id", contactInfos.get(position).getId());
					 bundle.putString("other_headurl", contactInfos.get(position).getHeadurl());
					 bundle.putString("chat", contactInfos.get(position).getChat().toString());
					 intent.putExtras(bundle);
					 context.startActivity(intent);
				}
			});
			holder.tv_view.setText(contactInfos.get(position).getName());
			ImgTask imgtask = new ImgTask(holder.img_view);
			imgtask.execute(contactInfos.get(position).getHeadurl());
			return convertView;
		}
		
	}
	
	public interface ContactCallBack {
		void DataSizeChanged();
	}
	

	public Bitmap getImgBitmap(String url) {
	    URL imgUrl = null;
	    Bitmap bitmap = null;
	    try {
	        imgUrl = new URL(url);

	        InputStream is = imgUrl.openStream();
	        bitmap = BitmapFactory.decodeStream(is);
	        is.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return bitmap;
	}
	
	class ViewHolder{
		ImageView img_view;
		TextView tv_view;
	}

}
