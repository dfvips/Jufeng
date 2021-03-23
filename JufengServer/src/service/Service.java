package service;

import java.sql.*;
import java.util.Random;
import Bean.MD5Utils;
import db.DBManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Service {
		public static String usermsg;
		Boolean result=null;
		public boolean register(String username, String password,String email,String phone) {
			// TODO Auto-generated method stub
				String u_id=createUid();
				DBManager sq=new DBManager();
				Connection conn = sq.getConn();
				ResultSet rs = null;
				PreparedStatement s;
				String sql2 = "insert into user_info(u_id,u_name,u_password,u_email,u_phone,u_headurl) values('"
						+ u_id + "'," + "'" + username + "','" + password + "','" + email
						+ "','" + phone + "','https://dreamfly.work/default.png')";
				try {
					s=conn.prepareStatement(sql2);
					s.execute();
					sq.comit();
					result=true;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					result=false;
					e.printStackTrace();
				}
				sq.close();
				
			return result;
		}
	
		private String createUid() {
			// TODO Auto-generated method stub
			StringBuilder sb;
			while(true){
				char[] chars = ("0123456789abcdefghijkmnopqrstuvwxyzABCDEFG" + "HIJKLMNPQRSTUVWXYZ").toCharArray();
				Random r = new Random();
				sb= new StringBuilder();
				for (int i = 0; i < 8; i++) {
					int pos = r.nextInt(chars.length);
					char c = chars[pos];
					sb.append(c);
				}
				if(checkUid(sb.toString())==true){
					break;
				}
			}
			return sb.toString();
		}

		public boolean login(String userloginmsg, String password) {
			// TODO Auto-generated method stub
			Boolean result = null;
			DBManager sq=new DBManager();
			PreparedStatement s;
			Connection conn = sq.getConn();
			ResultSet rs = null;
			String sql = "select * from user_info where (u_id='"+userloginmsg+"' or u_name='"+userloginmsg+"' or u_email='"+userloginmsg+"'or u_phone='"+userloginmsg+"') and u_password='"+password+"'";
			try {
				s=conn.prepareStatement(sql);
				rs = s.executeQuery();
				sq.comit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			try {
				if (rs.next()) {
					JSONObject msg=createJSONObject(rs.getString("u_id").toString(), rs.getString("u_name").toString(), rs.getString("u_email").toString(), rs.getString("u_phone").toString(), rs.getString("u_password").toString(), rs.getString("u_headurl").toString());
					usermsg=msg.toString();
					result=true;
				}else{
					result=false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sq.close();
			return result;
	}
		public boolean find(String u_id, String email, String pwd) {
			// TODO Auto-generated method stub
			Boolean result = null;
			DBManager sq=new DBManager();
			PreparedStatement s;
			Connection conn = sq.getConn();
			ResultSet rs = null;
			String sql = "select * from user_info where u_id='"+u_id+"' and u_email='"+email+"'";
			try {
				s=conn.prepareStatement(sql);
				rs = s.executeQuery();
				sq.comit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			try {
				if (rs.next()) {
					PreparedStatement s1;
					String sql2 = "update user_info set u_password = '"+pwd+"' where u_id='"+u_id+"'";
					try {
						s1=conn.prepareStatement(sql2);
						s1.executeUpdate();
						sq.comit();
						result=true;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						result=false;
					}
					sq.close();
					result=true;
				}else{
					result=false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
		public boolean changname(String u_id,String username) {
			// TODO Auto-generated method stub
			Boolean result=false;
			DBManager sq = new DBManager();
			Connection conn=sq.getConn();
			PreparedStatement s;
			String sql2 = "update user_info set u_name = '"+username+"' where u_id='"+u_id+"'";
			try {
				s=conn.prepareStatement(sql2);
				s.execute();
				sq.comit();
				result=true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				result=false;
			}
			sq.close();
			return result;
		}
		public boolean changpwd(String u_id,String pwd) {
			// TODO Auto-generated method stub
			Boolean result=false;
			DBManager sq = new DBManager();
			Connection conn = sq.getConn();
			PreparedStatement s;
			String sql2 = "update user_info set u_password = '"+pwd+"' where u_id='"+u_id+"'";
			try {
				s=conn.prepareStatement(sql2);
				s.executeUpdate();
				sq.comit();
				result=true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				result=false;
			}
			sq.close();
			return result;
		}
		public boolean changphone(String u_id,String phone) {
			// TODO Auto-generated method stub
			Boolean result=false;
			DBManager sq = new DBManager();
			Connection conn = sq.getConn();
			PreparedStatement s;
			String sql2 = "update user_info set u_phone = '"+phone+"' where u_id='"+u_id+"'";
			try {
				s=conn.prepareStatement(sql2);
				s.executeUpdate();
				sq.comit();
				result=true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				result=false;
			}
			sq.close();
			return result;
		}
		public boolean changemail(String u_id,String email) {
			// TODO Auto-generated method stub
			Boolean result=false;
			DBManager sq = new DBManager();
			Connection conn = sq.getConn();
			PreparedStatement s;
			String sql2 = "update user_info set u_email = '"+email+"' where u_id='"+u_id+"'";
			try {
				s=conn.prepareStatement(sql2);
				s.executeUpdate();
				sq.comit();
				result=true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				result=false;
			}
			sq.close();
			return result;
		}
		public boolean checkUid(String u_id) {
			// TODO Auto-generated method stub
			Boolean result = null;
			DBManager sq=new DBManager();
			Connection conn = sq.getConn();
			PreparedStatement s;
			ResultSet rs = null;
			String sql = "select * from user_info where u_id='"+u_id+"'";
			try {
				s=conn.prepareStatement(sql);
				rs = s.executeQuery();
				sq.comit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			try {
				if (rs.next()) {
					result=false;
				}else{
					result=true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sq.close();
			return result;
	}
	 private static JSONObject createJSONObject(String u_id,String u_name,String u_email,String u_phone,String pwd,String img) {  
	        JSONObject result = new JSONObject();  
	        result.put("success", true);  
	        
	        JSONObject msg = new JSONObject();  
	        msg.put("u_id", u_id);  
	        msg.put("u_name", u_name);  
	        msg.put("u_email", u_email);  
	        msg.put("u_phone", u_phone);
	        msg.put("u_password", pwd);  
	        msg.put("img", img);  
	   
	        JSONArray jsonArray = new JSONArray();  
	          
	        jsonArray.add(msg);  
	        result.element("data", jsonArray);  
	          
	        return result;  
	    } 
}
