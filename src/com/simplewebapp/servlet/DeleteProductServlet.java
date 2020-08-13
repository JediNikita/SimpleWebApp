package com.simplewebapp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simplewebapp.utils.DbUtils;
import com.simplewebapp.utils.MyUtils;


@WebServlet("/deleteProduct")
public class DeleteProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public DeleteProductServlet() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Connection conn = MyUtils.getStoredConnection(request);
		 
	        String code = (String) request.getParameter("code");
	 
	        String errorString = null;
	 
	        try {
	            DbUtils.deleteProduct(conn, code);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            errorString = e.getMessage();
	        } 
	        
	        if (errorString != null) {
	           
	            request.setAttribute("errorString", errorString);
	            RequestDispatcher dispatcher = request.getServletContext()
	                    .getRequestDispatcher("/WEB-INF/views/deleteProductErrorView.jsp");
	            dispatcher.forward(request, response);
	        }
	       
	        else {
	            response.sendRedirect(request.getContextPath() + "/productList");
	        }
	 
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
