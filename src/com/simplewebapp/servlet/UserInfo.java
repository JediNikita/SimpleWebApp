package com.simplewebapp.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.simplewebapp.beans.UserAccount;
import com.simplewebapp.utils.MyUtils;


@WebServlet("/userInfo")
public class UserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public UserInfo() {
        super();
        
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession();
		
		UserAccount logineduser= MyUtils.getLoginedUser(session);
		
		if(logineduser==null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		else {
			request.setAttribute("user", logineduser);
			
			RequestDispatcher dispatcher= request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/userInfoView.jsp");
			dispatcher.forward(request, response);
		}
			
		
	}

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
