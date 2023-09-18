package com.techBlog.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import com.techBlog.dao.UserDao;
import com.techBlog.entities.Message;
import com.techBlog.entities.User;
import com.techBlog.helper.ConnectionProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet{
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
	        /* TODO output your page here. You may use following sample code. */
			  String userEmail = request.getParameter("email");
	          String userPassword = request.getParameter("password");
	
	          UserDao dao = new UserDao(ConnectionProvider.getConnection());
	
	          User u = dao.getUserByEmailAndPassword(userEmail, userPassword);
	
	          if (u == null) {
	              //login.................
	//              error///
	//              out.println("Invalid Details..try again");
	              Message msg = new Message("Invalid Details ! try with another", "error", "alert-danger");
	              HttpSession s = request.getSession();
	              s.setAttribute("msg", msg);
	
	              response.sendRedirect("login_page.jsp");
	          } else {
	              //......
	//              login success
	              HttpSession s = request.getSession();
	              s.setAttribute("currentUser", u);
	              response.sendRedirect("profile.jsp");
	
	          }
	
	          out.println("</body>");
	          out.println("</html>");
	      }
	}
	
	

}