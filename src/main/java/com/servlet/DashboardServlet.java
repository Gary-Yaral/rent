package com.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.constants.SystemConstant;
import com.dao.HouseDAO;
import com.dao.RentalDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.House;
import com.model.Rental;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard"})
@MultipartConfig
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	SystemConstant sc = new SystemConstant();
	HouseDAO houseDAO = new HouseDAO();
	RentalDAO rentalDAO = new RentalDAO();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String pathInfo = request.getPathInfo();
	    String[] path = sc.searchPath(pathInfo);
	    
	    if (pathInfo == null) {
	    	response.sendRedirect("./");
	    	return;
	    }
	    
	    if(path != null) {
	    	request.setAttribute("page", path[1]);
	    	request.setAttribute("view", path[2]);
	    	request.getRequestDispatcher(path[0]).forward(request, response);	    	
	    } else {
	    	request.getRequestDispatcher("../404.jsp").forward(request, response);	    	
	    }	    	    
	}
	
	 @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		 String folder = request.getServletContext().getRealPath("/") + "uploads/";
		 String action = request.getParameter("action");
		 //System.out.print(action);
		 
		 if(action.equals("load-houses")) {
			 String userID = request.getParameter("user_id");
			 List<House> list = houseDAO.getAllHousesByUserId(userID);
			 if(list.size() > 0) {
				 ObjectMapper objectMapper = new ObjectMapper();
				 String json = objectMapper.writeValueAsString(list);
				 response.getWriter().write(json);        		
			 } else {
				 response.getWriter().write("[]");  
			 }	 
		 }
		 
		 if(action.equals("load-rental")) {
			 String userID = request.getParameter("user_id");
			 List<Rental> list = rentalDAO.getAllByUserId(userID);
			 if(list.size() > 0) {
				 ObjectMapper objectMapper = new ObjectMapper();
				 String json = objectMapper.writeValueAsString(list);
				 response.getWriter().write(json);        		
			 } else {
				 response.getWriter().write("[]");  
			 }	 
		 }
		 
		 if(action.equals("save-house")) {
			 House house = new House();
			 String nextIndex = houseDAO.getNextIndex();
			 Part filePart = request.getPart("image");
			 String fileName = filePart.getSubmittedFileName();
			 
			 String price = request.getParameter("price");
			 String address = request.getParameter("address");
			 String description = request.getParameter("description");
			 String status = request.getParameter("status");
			 long user_id = Long.parseLong(request.getParameter("user_id"));
			 String image = "image-" + nextIndex + ".jpg";
			 
			 house.setAddress(address);
			 house.setDescription(description);
			 house.setPrice(Double.valueOf(price));
			 house.setStatus(status);
			 house.setPhoto(image);
			 house.setUser(houseDAO.findUser(user_id));
			 String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
			 
			 if (!extension.equalsIgnoreCase("jpg") &&
			     !extension.equalsIgnoreCase("jpeg") &&
			     !extension.equalsIgnoreCase("png")) {
			     response.getWriter().write("{\"result\": false, \"message\": \"Solo se permiten JPG, JPEG y PNG\"}");
			     return;
			 }
			
			try {
				String imagePath = folder + image;
				filePart.write(imagePath);
				//sc.getFiles(folder);
				if(houseDAO.add(house)) {
					response.getWriter().write("{\"result\": true, \"message\": \"Casa guardada correctamente\"}");
					return;	
				} else {
					response.getWriter().write("{\"result\": false, \"message\": \"Error al guardar los datos\"}");					
				}
				return;				
			} catch(Exception e) {
				response.getWriter().write("{\"result\": false, \"message\": \"Error al guardar la imagen\"}");
			}
		 }
		 
		 if(action.equals("edit-house")) {
			 long house_id = Long.parseLong(request.getParameter("house_id"));
			 House house = houseDAO.findOne(house_id);
			 
			 Part filePart = request.getPart("image");
			 if(filePart != null && filePart.getSize() > 0) {
				 String fileName = filePart.getSubmittedFileName();
				 String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
				 if (!extension.equalsIgnoreCase("jpg") &&
						 !extension.equalsIgnoreCase("jpeg") &&
						 !extension.equalsIgnoreCase("png")) {
					 response.getWriter().write("{\"result\": false, \"message\": \"Solo se permiten JPG, JPEG y PNG\"}");
					 return;
				 }	
				 
				 try {
					 String imagePath = folder + house.getPhoto();
					 filePart.write(imagePath);
				 } catch(Exception e) {
					 response.getWriter().write("{\"result\": false, \"message\": \"No se ha podido actualizar la imagen\"}");
					 return;
				 }
			 }
			 		 
			 String price = request.getParameter("price");
			 String address = request.getParameter("address");
			 String description = request.getParameter("description");
			 String status = request.getParameter("status");
			 
			 house.setAddress(address);
			 house.setDescription(description);
			 house.setPrice(Double.valueOf(price));
			 house.setStatus(status);			 
			
			 if(houseDAO.update(house)) {
				response.getWriter().write("{\"result\": true, \"message\": \"Casa actualizada correctamente\"}");
				return;	
			} else {
				response.getWriter().write("{\"result\": false, \"message\": \"No se han podido actualizar los datos de la casa\"}");					
			}
		 }
		 
		 if(action.equals("delete-house")) {
			 long house_id = Long.parseLong(request.getParameter("house_id"));
			 House house = houseDAO.findOne(house_id);
			 String img = house.getPhoto();
			 File file = new File(folder + img);
			 if(file.delete()) {
				 if(houseDAO.remove(house_id)) {
					 response.getWriter().write("{\"delete\": true }");				 
				 } else {
					 response.getWriter().write("{\"delete\": false }");				 
				 }				 
			 } else {
				 response.getWriter().write("{\"delete\": false }");
			 }
		 }
		 
		 if(action.equals("get-house")) {				 
			 long house_id = Long.parseLong(request.getParameter("house_id"));
			 House house = houseDAO.findOne(house_id);
			 if(house != null) {
				 ObjectMapper objectMapper = new ObjectMapper();
				 String json = objectMapper.writeValueAsString(house);
				 response.getWriter().write(json);  				 
			 } else {
				 response.getWriter().write("{\"error\": \"No existe esa casa\"}");				 
			 }
		 }
	 }

}
