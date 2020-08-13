package com.simplewebapp.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.simplewebapp.beans.Product;
import com.simplewebapp.beans.UserAccount;

public class DbUtils {

	public static UserAccount findUser(Connection conn, String userName, String password) throws SQLException {
		String sql= "Select a.user_name, a.gender, a.password from user_account a where a.name= ? "
				+ "and a.password=?;";
		
		PreparedStatement ps= conn.prepareStatement(sql);
		ps.setString(1, userName);
		ps.setString(2, password);
		ResultSet rs= ps.executeQuery();
		
		if (rs.next()) {
			String gender= rs.getString("Gender");
			UserAccount user= new UserAccount();
			user.setGender(gender);
			user.setName(userName);
			user.setPassword(password);
			return user;
		}
		return null;
		
	}
	
	public static UserAccount findUser(Connection conn, String userName) throws SQLException {
		String sql= "Select a.user_name, a.gender, a.password from user_account a where a.userName= ?;";
		
		PreparedStatement ps= conn.prepareStatement(sql);
		ps.setString(1, userName);
		ResultSet rs= ps.executeQuery();
		
		if (rs.next()) {
			String gender= rs.getString("Gender");
			String password=rs.getString("Password");
			UserAccount user= new UserAccount();
			user.setGender(gender);
			user.setName(userName);
			user.setPassword(password);
			return user;
		}
		return null;
	
	}
	
	public static List<Product> queryProduct(Connection conn) throws SQLException{
		String sql= "select a.code, a.name, a.price from Product a";
		
		PreparedStatement ps= conn.prepareStatement(sql);
		List<Product> productlist=new ArrayList<Product>();
		ResultSet rs= ps.executeQuery();
		
		if(rs.next()) {
			String code= rs.getString("Code");
			String name= rs.getString("Name");
			float value= rs.getFloat("Price");
			Product product = new Product(code, name, value);
			productlist.add(product);
			return productlist;
		
			
		}
		return null;
	}
	
	 public static Product findProduct(Connection conn, String code) throws SQLException {
	        String sql = "Select a.Code, a.Name, a.Price from Product a where a.Code=?";
	 
	        PreparedStatement pstm = conn.prepareStatement(sql);
	        pstm.setString(1, code);
	 
	        ResultSet rs = pstm.executeQuery();
	 
	        while (rs.next()) {
	            String name = rs.getString("Name");
	            float price = rs.getFloat("Price");
	            Product product = new Product(code, name, price);
	            return product;
	        }
	        return null;
	 }
	 
	  public static void updateProduct(Connection conn, Product product) throws SQLException {
	        String sql = "Update Product set Name =?, Price=? where Code=? ";
	 
	        PreparedStatement pstm = conn.prepareStatement(sql);
	 
	        pstm.setString(1, product.getName());
	        pstm.setFloat(2, product.getPrice());
	        pstm.setString(3, product.getCode());
	        pstm.executeUpdate();
	    }
	 
	    public static void insertProduct(Connection conn, Product product) throws SQLException {
	        String sql = "Insert into Product(Code, Name,Price) values (?,?,?)";
	 
	        PreparedStatement pstm = conn.prepareStatement(sql);
	 
	        pstm.setString(1, product.getCode());
	        pstm.setString(2, product.getName());
	        pstm.setFloat(3, product.getPrice());
	 
	        pstm.executeUpdate();
	    }
	 
	    public static void deleteProduct(Connection conn, String code) throws SQLException {
	        String sql = "Delete From Product where Code= ?";
	 
	        PreparedStatement pstm = conn.prepareStatement(sql);
	 
	        pstm.setString(1, code);
	 
	        pstm.executeUpdate();
	    }
}	
	
