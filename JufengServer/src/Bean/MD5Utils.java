package Bean;

import java.security.MessageDigest;

public class MD5Utils {
	public static String encode(String text) {
		try {
			MessageDigest digest=MessageDigest.getInstance("md5");
			byte[] result=digest.digest(text.getBytes());
			StringBuilder sb=new StringBuilder();
			for (byte b : result) {
				int number=b&0xff;
				String hex=Integer.toHexString(number);
				if (hex.length()==1) {
					sb.append("0"+hex);
				} else {
					sb.append(hex);
				}
			}
			return sb.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}		
	}
}
