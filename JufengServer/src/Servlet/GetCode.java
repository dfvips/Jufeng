package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Bean.MD5Utils;
import Bean.SendEmail;

public class GetCode extends HttpServlet {

	/**
		 * The doGet method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to get.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String email = request.getParameter("email");
		int code = Integer.parseInt(request.getParameter("code"));
		char[] chars = ("0123456789abcdefghijkmnopqrstuvwxyzABCDEFG" + "HIJKLMNPQRSTUVWXYZ").toCharArray();
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			int pos = r.nextInt(chars.length);
			char c = chars[pos];
			sb.append(c);
		}
		PrintWriter out = response.getWriter();
		SendEmail.sendEmail(email,sb.toString(),code);
		out.print(MD5Utils.encode(MD5Utils.encode(sb.toString())));
		out.flush();
		out.close();
	}

	/**
		 * The doPost method of the servlet. <br>
		 *
		 * This method is called when a form has its tag value method equals to post.
		 * 
		 * @param request the request send by the client to the server
		 * @param response the response send by the server to the client
		 * @throws ServletException if an error occurred
		 * @throws IOException if an error occurred
		 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
