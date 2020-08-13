package com.simplewebapp.filter;

import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.simplewebapp.conn.ConnectionUtils;
import com.simplewebapp.utils.MyUtils;


@WebFilter(filterName="JdbcFilter", urlPatterns= {"/*"})
public class JDBCFilter implements Filter {

   
    public JDBCFilter() {
       
    }

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req= (HttpServletRequest)request;
		if(this.needJDBC(req)) {
			System.out.println("Open connection for: " + req.getServletPath());
			Connection conn= null;
			
			try {
				conn= ConnectionUtils.getConnection();
				conn.setAutoCommit(false);
				
				MyUtils.storeConnection(request, conn);
				chain.doFilter(request, response);
				
				conn.commit();
			}
			catch (Exception e) {
				e.printStackTrace();
				ConnectionUtils.rollbackQuietly(conn);
				throw new ServletException();
			}
			finally {
				ConnectionUtils.closeQuietly(conn);
			}
		}
		else
			chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		
	}
	
	private boolean needJDBC(HttpServletRequest request) {
		System.out.println("JDBC Filter");
		
		String servletPath= request.getServletPath();
		String pathInfo= request.getPathInfo();
		String urlPattern= servletPath;
		
		if(pathInfo!=null) {
			urlPattern= servletPath+"/*";
			
		}
		
		Map<String, ? extends ServletRegistration> servletRegistrations= request.getServletContext()
				.getServletRegistrations();
		
		Collection<? extends ServletRegistration> values= servletRegistrations.values();
		for(ServletRegistration sr:values) {
			Collection<String> mapping= sr.getMappings();
			if(mapping.contains(urlPattern))
				return true;
		}
		return false;
	}

}
