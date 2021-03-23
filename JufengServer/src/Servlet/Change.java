package Servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.Service;

public class Change extends HttpServlet {

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
		String u_id=request.getParameter("u_id");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String phone=request.getParameter("phone");
		String email=request.getParameter("email");
		boolean result = false;
		PrintWriter out = response.getWriter();
		if((username!=null||password!=null||phone!=null||email!=null)&&u_id!=null){
			if(username!=null){
				Service service=new Service();
				result=service.changname(u_id, username);
			}
			if(password!=null){
				Service service=new Service();
				result=service.changpwd(u_id, password);
			}
			if(phone!=null){
				Service service=new Service();
				result=service.changphone(u_id, phone);
			}
			if(email!=null){
				Service service=new Service();
				result=service.changemail(u_id, email);
			}
		}
		if(result){
			out.print("Ok");
		}else{
			out.print("false");
		}
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
