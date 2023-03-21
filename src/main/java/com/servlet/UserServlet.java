package com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.constants.SystemConstant;
import com.dao.TenantDAO;
import com.dao.UserDAO;
import com.model.Tenant;
import com.model.User;


@WebServlet(name = "UserServlet", urlPatterns = {"/user"})
@MultipartConfig
public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	SystemConstant sc = new SystemConstant();
        String action = request.getParameter("action");
        response.setContentType("application/json; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        if(action.equals("login")) {
        	String username = request.getParameter("username");
        	String password = request.getParameter("password");
        	String type = request.getParameter("type");
        	
        	if(username.equals(null) || password.equals(null) || type.equals(null)) {
        		response.getWriter().write("{\"access\": false , \"error\":\"No se han enviado todos los datos\"}");        		
        		return;
        	}
        	
        	if(type.equals("admin")) {
        		UserDAO user = new UserDAO();        		        		
        		User userExists = user.findUserByEmailAndPassword(username, password); 
        		if(userExists != null) {
        			HttpSession session = request.getSession();
        			session.setAttribute(sc.getSessionName(), userExists);
        			response.getWriter().write("{\"access\":true, \"message\":\"\"}");
        			return;
        		}        		
        	}
        	
        	if(type.equals("tenant")) {
	        	TenantDAO tenant = new TenantDAO();        		
	        	Tenant tenantExists = tenant.findUserByEmailAndPassword(username, password);       		   	  	
	        	if(tenantExists != null) {
	        		HttpSession session = request.getSession();
	        		session.setAttribute(sc.getSessionTenantName(), tenantExists);
	        		response.getWriter().write("{\"access\":true, \"message\":\"\"}");
	        		return;
	        	}
        	}
        	
        	response.getWriter().write("{\"access\": false, \"message\":\"Usuario o contraseña incorrectos\"}");        		        			
        } 
        
        if(action.equals("logout")) {
        	HttpSession session = request.getSession();
        	session.removeAttribute(sc.getSessionName());
        	session.removeAttribute(sc.getSessionTenantName());
        	response.getWriter().write("{\"logout\":\"true\", \"error\":\"\"}"); 
        }
    }
}