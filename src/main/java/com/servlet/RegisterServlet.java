package com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.constants.SystemConstant;
import com.dao.TenantDAO;
import com.dao.UserDAO;
import com.model.Tenant;
import com.model.User;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
@MultipartConfig
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	SystemConstant sc = new SystemConstant();
	UserDAO userDAO = new UserDAO();
	TenantDAO tenantDAO = new TenantDAO();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String pathInfo = request.getPathInfo();
		if(pathInfo == null) {			
			request.getRequestDispatcher("register.jsp").forward(request, response);	    		    	    			
		} else {
			if(pathInfo.equals("/tenant")) {
				request.getRequestDispatcher("../register_tenant.jsp").forward(request, response);	    		    	    						
				return;
			}
			request.getRequestDispatcher("../404.jsp").forward(request, response);	    		    	    						
		}
	}
	
	 @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		 String action = request.getParameter("action");
		 if(action.equals("register")) {
			 User user = new User();
			 String name = request.getParameter("name");
			 String lastname = request.getParameter("lastname");
			 String email = request.getParameter("email");
			 String password = request.getParameter("password");
			 String telephone = "593"+request.getParameter("telephone").substring(1);;
			 
			 user.setEmail(email);
			 user.setLastName(lastname);
			 user.setPassword(password);
			 user.setName(name);
			 user.setTelephone(telephone);
			 
			 if(userDAO.exists(email) != null) {
				 response.getWriter().write("{\"result\": false, \"message\": \"Ya existe un usuario con ese Email\"}");					
				 return;
			 }
			 
			 try {
				 userDAO.add(user);				 				 
				 response.getWriter().write("{\"result\": true, \"message\": \"Usuario registrado correctamente\"}");					
			 } catch(Exception e) {
				 response.getWriter().write("{\"result\": false, \"message\": \"No se han podido registrar los datos\"}");					
			 }
			 
			 
		 }
		 
		 if(action.equals("register_tenant")) {
			 Tenant tenant = new Tenant();
			 String dni = request.getParameter("dni");
			 String name = request.getParameter("name");
			 String lastname = request.getParameter("lastname");
			 String telephone = request.getParameter("telephone");
			 String email = request.getParameter("email");
			 String password = request.getParameter("password");
			 
			 if(tenantDAO.exists(dni , email) != null) {
				 response.getWriter().write("{\"result\": false, \"message\": \"Ya existe un usuario con ese DNI o Email\"}");					
				 return;
			 }
			 
			 tenant.setDni(dni);
			 tenant.setName(name);
			 tenant.setLastname(lastname);
			 tenant.setTelephone(telephone);
			 tenant.setEmail(email);
			 tenant.setPassword(password);
			 try {
				 tenantDAO.add(tenant);				 				 
				 response.getWriter().write("{\"result\": true, \"message\": \"Usuario registrado correctamente\"}");					
			 } catch(Exception e) {
				 response.getWriter().write("{\"result\": false, \"message\": \"No se han podido registrar los datos\"}");					
			 }
			 
			 
		 }
	 }

}
