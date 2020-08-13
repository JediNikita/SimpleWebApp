package com.simplewebapp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simplewebapp.beans.Product;
import com.simplewebapp.utils.DbUtils;
import com.simplewebapp.utils.MyUtils;


@WebServlet("/productList")
public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ProductListServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection= MyUtils.getStoredConnection(request);
		
		String errorString=null;
		List<Product> productList= null;
		try {
			productList=DbUtils.queryProduct(connection);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			errorString=e.getMessage();
		}
		
		request.setAttribute("errorString", errorString);
		request.setAttribute("productList", productList);
		
		RequestDispatcher dispatcher= request.getServletContext().getRequestDispatcher("/WEB-INF/views/productView.jsp");
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
