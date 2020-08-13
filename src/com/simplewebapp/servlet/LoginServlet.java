package com.simplewebapp.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.simplewebapp.beans.UserAccount;
import com.simplewebapp.utils.DbUtils;
import com.simplewebapp.utils.MyUtils;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public LoginServlet() {
        super();
        
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher= this.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/loginView.jsp");
		
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userName= request.getParameter("userName");
		String password= request.getParameter("password");
		String rememberMeStr= request.getParameter("rememberMe");
		
		boolean remember= "Y".equals(rememberMeStr);
		
		UserAccount user= null;
		boolean hasError= false;
		String errorString=null;
		
		if(userName.length()==0 | password.length()==0 | userName==null | password==null) {
			hasError=true;
			errorString= "Required user name and password";
		}
		else {
			Connection connection= MyUtils.getStoredConnection(request);
			try {
				user= DbUtils.findUser(connection, userName,password);
				if(user==null) {
					hasError=true;
					errorString="User name or password invalid";
					
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				hasError=true;
				errorString=e.getMessage();
			}
		}
		
		if(hasError) {
			user= new UserAccount();
			user.setName(userName);
			user.setPassword(password);
			
			request.setAttribute("errorString", errorString);
			request.setAttribute("user", user);
			
			RequestDispatcher dispatcher= request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/loginView.jsp");
			
			dispatcher.forward(request, response);
		}
		else {
			HttpSession session= request.getSession();
			MyUtils.storeLoginedUser(session, user);
			
			if(remember) {
				MyUtils.storeUserCookie(response, user);
			}
			else {
				MyUtils.deleteUserCookie(response);
			}
			
			response.sendRedirect(request.getContextPath()+"/userInfo");
		}
		
	}

}
