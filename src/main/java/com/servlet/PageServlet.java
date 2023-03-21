package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.constants.SystemConstant;
import com.dao.HouseDAO;
import com.dao.TenantDAO;
import com.dao.UserDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.House;

@WebServlet(name = "PageServlet", urlPatterns = {"/page"})
@MultipartConfig
public class PageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	SystemConstant sc = new SystemConstant();
	UserDAO userDAO = new UserDAO();
	TenantDAO tenantDAO = new TenantDAO();
	HouseDAO houseDAO = new HouseDAO();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String pathInfo = request.getPathInfo();
		if(pathInfo == null) {			
			request.getRequestDispatcher("page.jsp").forward(request, response);	    		    	    			
		} else {
			request.getRequestDispatcher("../404.jsp").forward(request, response);	    		    	    						
		}
	}
	
	 @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		 String action = request.getParameter("action");
		 if(action.equals("get_all_houses")) {
			 List<House> list = houseDAO.getAll();
			 if(list != null) {
				 ObjectMapper objectMapper = new ObjectMapper();
				 String json = objectMapper.writeValueAsString(list);
				 response.getWriter().write(json);  				 
			 } else {
				 response.getWriter().write("{\"error\": \"No existe esa casa\"}");				 
			 }
			 
		 }

	 }

}
