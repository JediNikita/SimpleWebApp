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

import com.simplewebapp.beans.Product;
import com.simplewebapp.utils.DbUtils;
import com.simplewebapp.utils.MyUtils;


@WebServlet("/createProduct")
public class CreateProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public CreateProductServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher= request.getServletContext().getRequestDispatcher("/WEB_INF/views/createProductView.jsp");
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn= MyUtils.getStoredConnection(request);
			
		String code=(String)request.getParameter("code");
		String name=(String)request.getParameter("name");
		String priceStr=(String)request.getParameter("price");
		
		float price=0;
		try {
			price= Float.parseFloat(priceStr);
		}
		catch (Exception e) {
			
		}
		
		Product product=new Product(code,name,price);
		
		String errorString=null;
		String regex="\\w+";
		
		if(code==null | !code.matches(regex))
		{
			errorString="Invalid product code.";
		}
		
		if(errorString==null) {
			try {
				DbUtils.insertProduct(conn, product);
			} catch (SQLException e) {
				errorString=e.getMessage();
				e.printStackTrace();
			}
		}
		
		request.setAttribute("errorString", errorString);
		request.setAttribute("product", product);
		
		if(errorString!=null) {
			RequestDispatcher dispatcher= request.getServletContext().getRequestDispatcher("/WEB_INF/views/createProductView.jsp");
			dispatcher.forward(request, response);
		}
		else {
            response.sendRedirect(request.getContextPath() + "/productList");
        }	
	}

}
