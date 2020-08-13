package com.simplewebapp.filter;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.simplewebapp.beans.UserAccount;
import com.simplewebapp.utils.DbUtils;
import com.simplewebapp.utils.MyUtils;


@WebFilter(filterName = "cookieFilter",
		urlPatterns = { "/*" } 
		)
public class CookieFilter implements Filter {

   
    public CookieFilter() {
        
    }

	
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req= (HttpServletRequest)request;
		HttpSession session= req.getSession();
		
		UserAccount userInSession= MyUtils.getLoginedUser(session);
		
		if(userInSession!=null) {
			session.setAttribute("COOKIE_CHECKED", "CHECKED");
			chain.doFilter(request, response);
			return;
		}
		
		Connection conn=MyUtils.getStoredConnection(request);
		
		String checked= (String)session.getAttribute("COOKIE_CHECKED");
		if (checked==null && conn!=null) {
			String userName= MyUtils.getUserNameInCookie(req);
			try {
				UserAccount user= DbUtils.findUser(conn, userName);
				MyUtils.storeLoginedUser(session, user);
			}
			catch(Exception e) {}
			session.setAttribute("COOKIE_CHECKED", "CHECKED");
		}
		
		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
