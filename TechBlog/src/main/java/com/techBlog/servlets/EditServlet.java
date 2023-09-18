package com.techBlog.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.techBlog.dao.UserDao;
import com.techBlog.entities.Message;
import com.techBlog.entities.User;
import com.techBlog.helper.ConnectionProvider;
import com.techBlog.helper.Helper;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;

@MultipartConfig
public class EditServlet extends HttpServlet{
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
        ServletContext context = getServletContext();
		try (PrintWriter out = response.getWriter()) {
	        /* TODO output your page here. You may use following sample code. */

//          fetch all data
          String userEmail = request.getParameter("user_email");
          String userName = request.getParameter("user_name");
          String userPassword = request.getParameter("user_password");
          String userAbout = request.getParameter("user_about");
          Part part = request.getPart("image");

          String imageName = part.getSubmittedFileName();

          //get the user from the session...
          HttpSession s = request.getSession();
          User user = (User) s.getAttribute("currentUser");
          user.setEmail(userEmail);
          user.setName(userName);
          user.setPassword(userPassword);
          user.setAbout(userAbout);
          String oldFile = user.getProfile();

          user.setProfile(imageName);

          //update database....
          UserDao userDao = new UserDao(ConnectionProvider.getConnection());

          boolean ans = userDao.updateUser(user);
          if (ans) {

              String path = context.getRealPath("/") + "pics" + File.separator + user.getProfile();

              //start of photo work
              //delete code
              String pathOldFile = context.getRealPath("/") + "pics" + File.separator + oldFile;

              if (!oldFile.equals("default.png")) {
                  Helper.deleteFile(pathOldFile);
              }

              if (Helper.saveFile(part.getInputStream(), path)) {
                  out.println("Profile updated...");
                  Message msg = new Message("Profile details updated...", "success", "alert-success");
                  s.setAttribute("msg", msg);

              } else {
                  //////////////
                  Message msg = new Message("Something went wrong..", "error", "alert-danger");
                  s.setAttribute("msg", msg);
              }

              //end of phots work
          } else {
              out.println("not updated..");
              Message msg = new Message("Something went wrong..", "error", "alert-danger");
              s.setAttribute("msg", msg);

          }

          response.sendRedirect("profile.jsp");
		}
	}
}
