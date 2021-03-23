package adapter;

import Utils.MD5Utils;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a=MD5Utils.encode(MD5Utils.encode("admin"));
		System.out.println(a);
	}

}
