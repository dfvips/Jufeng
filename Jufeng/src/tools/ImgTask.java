package tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import cn.app.jufeng.R;

public class ImgTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;

    public ImgTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            urlconnection.setReadTimeout(5000);
            urlconnection.setConnectTimeout(5000);
            //判断结果码
            if(urlconnection.getResponseCode()==200){
                //得到资源
                InputStream inputstream = urlconnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputstream);
                return bitmap;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(bitmap==null){
            imageView.setImageResource(R.drawable.user_head_temp);
        }else{
            imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
