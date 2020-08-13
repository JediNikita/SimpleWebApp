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


@WebServlet("/editProduct")
public class EditProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public EditProductServlet() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn= MyUtils.getStoredConnection(request);
		
		String code= request.getParameter("code");
		Product product= null;
		String errorString=null;
		
		try {
			product= DbUtils.findProduct(conn, code);
		}
		catch (Exception e) {
			e.printStackTrace();
			errorString=e.getMessage();
		}
		
		if(errorString!=null | product==null) {
			response.sendRedirect(request.getServletPath() +"/productList");
		}
		
		request.setAttribute("errorString", errorString);
		request.setAttribute("product", product);
		
		RequestDispatcher dispatcher= request.getServletContext().getRequestDispatcher("/WEB-INF/views/editProductView.jsp");
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);

		String code = (String) request.getParameter("code");
		String name = (String) request.getParameter("name");
		String priceStr = (String) request.getParameter("price");
		float price = 0;
		try {
			price = Float.parseFloat(priceStr);
		} catch (Exception e) {
		}
		Product product = new Product(code, name, price);

		String errorString = null;

		try {
			DbUtils.updateProduct(conn, product);
		} catch (SQLException e) {
			e.printStackTrace();
			errorString = e.getMessage();
		}
		
		request.setAttribute("errorString", errorString);
		request.setAttribute("product", product);

		
		if (errorString != null) {
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/editProductView.jsp");
			dispatcher.forward(request, response);
		}
		
		else {
			response.sendRedirect(request.getContextPath() + "/productList");
		}
	}
		
	

}
