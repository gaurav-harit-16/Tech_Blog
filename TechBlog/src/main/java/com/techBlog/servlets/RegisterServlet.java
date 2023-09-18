package com.techBlog.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import com.techBlog.dao.UserDao;
import com.techBlog.entities.User;
import com.techBlog.helper.ConnectionProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@MultipartConfig
public class RegisterServlet extends HttpServlet{

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
          

//            fetch all form data
            String check = request.getParameter("check");
            if (check == null) {
                out.println("box not checked");
            } else {
                String name = request.getParameter("user_name");
                String email = request.getParameter("user_email");
                String password = request.getParameter("user_password");
                String gender = request.getParameter("gender");
                String about = request.getParameter("about");
                //create user object and set all data to that object..
                User user = new User(name, password,email, gender, about);

                //create a user daao object..
                UserDao dao = new UserDao(ConnectionProvider.getConnection());
                if (dao.saveUser(user)) {
//                save..
                    out.println("done");
                } else {
                    out.println("error");
                }
            }
         
        }
    }
}
