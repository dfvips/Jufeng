package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//import com.sun.crypto.provider.RSACipher;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class db {
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://101.132.190.178:3306/Jufeng?useUnicode=true&characterEncoding=utf-8";
	private static final String user = "itdhsc";
	private static final String password = "Zx981216!";
	private static Connection con;
	
	static {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,password);
		} catch (Exception e) {}
	}
	
	public static JSONArray Query(String sql){
		PreparedStatement pr = null;
		JSONArray jsonArray=null;
		ResultSet rs=null;
		if(con==null){
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url,user,password);
			} catch (Exception e) {}
		}
		try {
			pr = (PreparedStatement) con.prepareStatement(sql);
			rs = pr.executeQuery();
			jsonArray = new JSONArray();
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("u_id", rs.getString("u_id"));
				json.put("u_headurl", rs.getString("u_headurl"));
				json.put("u_name", rs.getString("u_name"));
				json.put("u_password", rs.getString("u_password"));
				json.put("u_phone", rs.getString("u_phone"));
				json.put("u_email", rs.getString("u_email"));
				jsonArray.add(json);
			}
			return jsonArray;
		} catch (Exception e) {
			return jsonArray;
		} finally{
			try {
				if (rs!=null) {
					rs.close();
				}
				if(pr!=null){
					pr.close();
				}
			} catch (Exception e) {}
		}
	}
	
	public static Boolean Update(String sql){
		PreparedStatement pr = null;
		Boolean flag = true;
		if(con ==null){
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url,user,password);
			} catch (Exception e) {}
		}
		try {
			pr = (PreparedStatement) con.createStatement();
			int rs = pr.executeUpdate(sql);
			if(rs==0){
				flag = false;
			}
		} catch (Exception e) {
			
		} finally{
			try {
				if(pr!=null){
					pr.close();
				}
			} catch (Exception e) {}
		}
		return flag;
	}
	
}
