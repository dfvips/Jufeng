package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.widget.ImageView;
import cn.app.jufeng.R;

public class ImgTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;
    public ImgTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String...  strings) {
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
        	Bitmap sqbitmap=centerSquareScaleBitmap(bitmap,100);
        	Bitmap circlbitmap=createRoundCorner(sqbitmap);
            imageView.setImageBitmap(circlbitmap);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    public static Bitmap createRoundCorner(Bitmap bitmap) { 
      int width = bitmap.getWidth(); 
      int height = bitmap.getHeight(); 
      int left = 0, top = 0;
      float roundPx = height/2; 
      
      Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888); 
      Canvas canvas = new Canvas(output); 
      int color = 0xff424242; 
      Paint paint = new Paint(); 
      Rect rect = new Rect(left, top, width, height); 
      RectF rectF = new RectF(rect); 
      
      paint.setAntiAlias(true); 
      canvas.drawARGB(0, 0, 0, 0); 
      paint.setColor(color); 
      canvas.drawRoundRect(rectF, roundPx, roundPx, paint); 
      paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN)); 
      canvas.drawBitmap(bitmap, rect, rect, paint); 
      return output; 
    } 

    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength){
    	
     if(null == bitmap || edgeLength <= 0){
    	 return  null;
     }

     Bitmap result = bitmap;
     int widthOrg = bitmap.getWidth();
     int heightOrg = bitmap.getHeight();

     if(widthOrg > edgeLength && heightOrg > edgeLength)
     {
      //压缩到一个最小长度是edgeLength的bitmap
      int longerEdge = (int)(edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
      int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
      int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
      Bitmap scaledBitmap;

            try{
             scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            }
            catch(Exception e){
             return null;
            }

         //从图中截取正中间的正方形部分。
         int xTopLeft = (scaledWidth - edgeLength) / 2;
         int yTopLeft = (scaledHeight - edgeLength) / 2;

         try{
          result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
          scaledBitmap.recycle();
         }
         catch(Exception e){
          return null;
         }       
     }

     return result;
    }
}
