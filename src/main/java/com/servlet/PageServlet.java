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
import com.dao.ImagesDAO;
import com.dao.TenantDAO;
import com.dao.UserDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.HouseRender;

@WebServlet(name = "PageServlet", urlPatterns = {"/page"})
@MultipartConfig
public class PageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	SystemConstant sc = new SystemConstant();
	UserDAO userDAO = new UserDAO();
	TenantDAO tenantDAO = new TenantDAO();
	HouseDAO houseDAO = new HouseDAO();
	ImagesDAO imagesDAO = new ImagesDAO();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String folder = request.getServletContext().getRealPath("/") + "uploads/";
	    String pathInfo = request.getPathInfo();
	    String house_id =request.getParameter("house");
		if(pathInfo == null) {	
			//sc.cleanFolder(folder);
			//sc.getFiles(folder);
			if(house_id != null) {				
				try {
					long house = Long.parseLong(house_id);
					String id = String.valueOf(house);
					request.setAttribute("house", id);
					request.getRequestDispatcher("./show_house.jsp").forward(request, response);
				} catch (NumberFormatException e) {
					request.getRequestDispatcher("./404.jsp").forward(request, response);	   
				}
			} else {
				request.getRequestDispatcher("./page.jsp").forward(request, response);	    		    	    							
			}    		    	    							
		} else {
			request.getRequestDispatcher("../404.jsp").forward(request, response);	    		    	    						
		}
	}
	
	 @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		 String action = request.getParameter("action");
		 if(action.equals("get_all_houses")) {
			 List<HouseRender> list = houseDAO.getAll();
			 if(list != null) {
				 ObjectMapper objectMapper = new ObjectMapper();
				 String json = objectMapper.writeValueAsString(list);
				 response.getWriter().write(json);  				 
			 } else {
				 response.getWriter().write("[]");				 
			 }
			 
		 }

		 if(action.equals("get_house")) {
			 long house_id = Long.valueOf(request.getParameter("house_id"));
			 HouseRender hr =  houseDAO.getOneHouseData(house_id);
			 ObjectMapper objectMapper = new ObjectMapper();
			 String json = objectMapper.writeValueAsString(hr);
			 response.getWriter().write(json);  				 			 
		 }
	 }

}
