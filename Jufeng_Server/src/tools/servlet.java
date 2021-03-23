package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class servlet {
	@RequestMapping("/OnloadContactJSON")
	public void OnloadContactJSON(String userId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String path = request.getServletContext().getRealPath("/user_json");
		String name = userId+".json";
		String json =  stream.InputStream(path, name);
//		try {  
//			FileInputStream fileInputStream = new FileInputStream(path);  
//	        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "gbk");  
//	        reader = new BufferedReader(inputStreamReader);  
//	        String tempString = null;  
//	        while ((tempString = reader.readLine()) != null) {
//	        	json += tempString;  
//	        }  
//	        reader.close();  
//	    } catch (IOException e) {  
//	        e.printStackTrace();  
//	    } finally {  
//	        if (reader != null) {  
//	        	try {  
//	        		reader.close();  
//	            } catch (IOException e) {  
//	            	e.printStackTrace();  
//	            }  
//	        }  
//	    }  
	        out.print(json);
	}
	
	@RequestMapping("/UpLoadContactJSON")
	public void UpLoadContactJSON(String otherId,String myId,String myMessage,HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");//设置日期格式
		String time = df.format(new Date());
		String path = request.getServletContext().getRealPath("/user_json");
System.out.println(path);
		String Myname = myId+".json";
		String MyJsonString = stream.InputStream(path, Myname);
		JSONObject MyObject = JSONObject.fromObject(MyJsonString);
		JSONArray MyContact = MyObject.getJSONArray("contact");
			for (int i = 0; i < MyContact.size(); i++) {
				if (MyContact.getJSONObject(i).get("id").equals(otherId)) {
					JSONObject temp = new JSONObject();
					temp.put("who", "me");
					temp.put("time", time);
					temp.put("content", myMessage);
					MyContact.getJSONObject(i).getJSONArray("chat").add(temp);
					stream.OutStream(path, Myname, MyObject.toString());
					break;
				}
			}
		String Othername = otherId+".json";
		String OtherJsonString = stream.InputStream(path, Othername);
		JSONObject OtherObject = JSONObject.fromObject(OtherJsonString);
		JSONArray OtherContact = OtherObject.getJSONArray("contact");
			for (int i = 0; i < OtherContact.size(); i++) {
				if (OtherContact.getJSONObject(i).get("id").equals(myId)) {
					JSONObject temp = new JSONObject();
					temp.put("who", "other");
					temp.put("time", time);
					temp.put("content", myMessage);
					OtherContact.getJSONObject(i).getJSONArray("chat").add(temp);
					stream.OutStream(path, Othername, OtherObject.toString());
					break;
				}
			}
		out.print("true");
	}  
	
	@RequestMapping("/SelectUser")
	public void SelectUser(String sql,HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = db.Query(sql);
		if (jsonArray!=null) {
			out.print(jsonArray.toString());
			System.out.println(jsonArray.toString());
		}
	}
	
	@RequestMapping("/SelectChat")
	public void SelectChat(String otherId,String myId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String path = request.getServletContext().getRealPath("/user_json");
		String Myname = myId+".json";
		String MyJsonString = stream.InputStream(path, Myname);
		JSONObject MyObject = JSONObject.fromObject(MyJsonString);
		JSONArray MyContact = MyObject.getJSONArray("contact");
		for (int i = 0; i < MyContact.size(); i++) {
			if (MyContact.getJSONObject(i).get("id").equals(otherId)) {
				String sql = "select * from user_info where u_id = '"+otherId+"'";
				JSONArray jsonArray = db.Query(sql);
				JSONObject temp = MyContact.getJSONObject(i);
				temp.put("other_url", jsonArray.getJSONObject(0).get("u_headurl"));
				
				out.print(temp);
				
				break;
			}
		}
	}
	
	@RequestMapping("/AddFriend")
	public void AddFriend(String otherId,String myId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String path = request.getServletContext().getRealPath("/user_json");
		String Myname = myId+".json";
		System.out.println(path+ Myname);
		String MyJsonString = stream.InputStream(path, Myname);
		JSONObject MyObject = JSONObject.fromObject(MyJsonString);
		JSONArray MyContact = MyObject.getJSONArray("contact");
		if(MyContact.size()!=0){
			for(int i=0; i<MyContact.size(); i++) {
				JSONObject temp = MyContact.getJSONObject(i);
				if (temp.get("id").equals(otherId)) {
					out.print("exist");
					return;
				}
			}
		}
		
		
		JSONObject myfriend = new JSONObject();
		myfriend.put("id", otherId);
		JSONArray myfriendChat = new JSONArray();
		myfriend.put("chat", myfriendChat);
		MyContact.add(myfriend);
		System.out.println(MyObject.toString());
		stream.OutStream(path, Myname, MyObject.toString());
		
		
		String Othername = otherId+".json";
		String OtherJsonString = stream.InputStream(path, Othername);
		System.out.println(path+ Othername);
		JSONObject OtherObject = JSONObject.fromObject(OtherJsonString);
		JSONArray OtherContact = OtherObject.getJSONArray("contact");
		JSONObject otherfriend = new JSONObject();
		otherfriend.put("id", myId);
		JSONArray otherfriendChat = new JSONArray();
		otherfriend.put("chat", otherfriendChat);
		OtherContact.add(otherfriend);
		stream.OutStream(path, Othername, OtherObject.toString());
		System.out.println( OtherObject.toString());
		
		out.print("true");
	}
	
	@RequestMapping("/res_json")
	public void res_json(String id,HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String path = request.getServletContext().getRealPath("/user_json");
		
		String Myname = id+".json";
		System.out.println(path+ Myname);
		JSONObject MyObject = new JSONObject();
		JSONArray MyContact = new JSONArray();
		MyObject.put("contact", MyContact);
		
		System.out.println(MyObject.toString());
		stream.OutStream(path, Myname, MyObject.toString());
		
		System.out.println("ok");
	}
	
}
