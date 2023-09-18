package com.techBlog.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import com.techBlog.entities.Message;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet{

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
			HttpSession s = request.getSession();

            s.removeAttribute("currentUser");

            Message m = new Message("Logout Successfully", "success", "alert-success");

            s.setAttribute("msg", m);

            response.sendRedirect("login_page.jsp");

            out.println("</body>");
            out.println("</html>");
        }
    }
}
