package com.servlet;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.constants.SystemConstant;
import com.dao.ImagesDAO;
import com.dao.HouseDAO;
import com.dao.RentalDAO;
import com.dao.UserDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.House;
import com.model.Images;
import com.model.Rental;
import com.model.User;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard"})
@MultipartConfig
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	SystemConstant sc = new SystemConstant();
	HouseDAO houseDAO = new HouseDAO();
	RentalDAO rentalDAO = new RentalDAO();
	ImagesDAO imagesDAO = new ImagesDAO();
	UserDAO userDAO = new UserDAO();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String pathInfo = request.getPathInfo();
	    String[] path = sc.searchPath(pathInfo);
	    String house = request.getParameter("house");
	    
	    if (pathInfo == null) {
	    	response.sendRedirect("./");
	    	return;
	    }
	    
	    if(path != null) {
	    	request.setAttribute("page", path[1]);
	    	request.setAttribute("view", path[2]);
	    	request.setAttribute("house", house);
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
			 long user_id = Long.parseLong(request.getParameter("user_id"));
			 List<Part> fileParts = request.getParts().stream().filter(part -> "images[]".equals(part.getName())).collect(Collectors.toList());
			 ImagesDAO imagesDAO = new ImagesDAO(); 
		
			 String title = request.getParameter("title");
			 String province = request.getParameter("province");
			 String canton = request.getParameter("canton");
			 String address = request.getParameter("address");
			 String price = request.getParameter("price");
			 String bathrooms = request.getParameter("bathrooms");
			 String rooms = request.getParameter("rooms");
			 String area = request.getParameter("area");
			 String status = request.getParameter("status");
			 String description = request.getParameter("description");
			 
			 
			 house.setTitle(title);
			 house.setProvince(province);
			 house.setCanton(canton);
			 house.setAddress(address);
			 house.setPrice(Double.valueOf(price));
			 house.setArea(area);
			 house.setRooms(rooms);
			 house.setBathrooms(bathrooms);
			 house.setStatus(status);
			 house.setDescription(description);
			 house.setUser(houseDAO.findUser(user_id));
			 House result = houseDAO.add(house);
			 if(result != null) {
				int counter = 0;
				try {
					for (Part filePart : fileParts) {
						Images img = new Images();
						counter++;
						String fileName = "img-" + result.getId() + "-" + counter+".jpg";
						img.setName(fileName);
						img.setHouse(result);				
						String path = folder + fileName;
						filePart.write(path);
						imagesDAO.add(img);
					}
				response.getWriter().write("{\"result\": true, \"message\": \"Casa guardada correctamente\"}");
				} catch(Exception e) {
					response.getWriter().write("{\"result\": false, \"message\": \"Error al guardar los datos\"}");									 
				}
				
			 } else {
				response.getWriter().write("{\"result\": false, \"message\": \"Error al guardar los datos\"}");					
			 }			
		 }
		 
		 if(action.equals("edit-house")) {
			 long house_id = Long.parseLong(request.getParameter("house_id"));
			 House house = houseDAO.findOne(house_id);
			 List<Part> fileParts = request.getParts().stream().filter(part -> "images[]".equals(part.getName())).collect(Collectors.toList());
			 ImagesDAO imagesDAO = new ImagesDAO(); 
		
			 String title = request.getParameter("title");
			 String province = request.getParameter("province");
			 String canton = request.getParameter("canton");
			 String address = request.getParameter("address");
			 String price = request.getParameter("price");
			 String bathrooms = request.getParameter("bathrooms");
			 String rooms = request.getParameter("rooms");
			 String area = request.getParameter("area");
			 String status = request.getParameter("status");
			 String description = request.getParameter("description");
			 
			 
			 house.setTitle(title);
			 house.setProvince(province);
			 house.setCanton(canton);
			 house.setAddress(address);
			 house.setPrice(Double.valueOf(price));
			 house.setArea(area);
			 house.setRooms(rooms);
			 house.setBathrooms(bathrooms);
			 house.setStatus(status);
			 house.setDescription(description);
			 //System.out.println(house.getUser().getPassword());
			 if(houseDAO.update(house)) {
				 //System.out.println("hay "+ fileParts.size());
				 //System.out.println(fileParts.get(0).getSize());
				 if(fileParts.get(0).getSize() > 0) {
					 sc.deleteImages(folder, house.getId());
					 imagesDAO.removeAll(house.getId());
					 int counter = 0;
					 try {
						 for (Part filePart : fileParts) {
							 Images img = new Images();
							 counter++;
							 String fileName = "img-" + house.getId() + "-" + counter+".jpg";
							 img.setName(fileName);
							 img.setHouse(house);				
							 String path = folder + fileName;
							 filePart.write(path);
							 imagesDAO.add(img);
						 }
						 
						 response.getWriter().write("{\"result\": true, \"message\": \"Casa guardada correctamente\"}");
						 
					 } catch(Exception e) {
						 response.getWriter().write("{\"result\": false, \"message\": \"Error al actualizar las imagenes\"}");									 
					 }					 
				 } else {
					 response.getWriter().write("{\"result\": true, \"message\": \"Casa guardada correctamente\"}");					 
				 }
				 
			 }
		 }
		 
		 if(action.equals("delete-house")) {
			 long house_id = Long.parseLong(request.getParameter("house_id"));
			 if(houseDAO.remove(house_id)) {
				 sc.deleteImages(folder,house_id);
				 response.getWriter().write("{\"result\": true }");				 
			 } else {
				 response.getWriter().write("{\"result\": false }");				 
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
		 
		 if(action.equals("get-home-data")) {				 
			 long user_id = Long.parseLong(request.getParameter("user_id"));
			 String id = request.getParameter("user_id");
			 long availables = houseDAO.countAvailable(user_id);
			 int houses = houseDAO.getAllHousesByUserId(id).size();
			 String result = "{\"houses\":"+houses+",\"availables\":"+availables+"}";
			 response.getWriter().write(result);				 
			 				 
		 }
		 
		 if(action.equals("get-images")) {				 
			 long house_id = Long.parseLong(request.getParameter("house_id"));
			 List<Images> result = imagesDAO.getAll(house_id);
			 ObjectMapper objectMapper = new ObjectMapper();
			 String json = objectMapper.writeValueAsString(result);
			 response.getWriter().write(json);  			 
			 
		 }
		 
		 if(action.equals("edit-user")) {
			 long id = Long.valueOf(request.getParameter("user_id"));
			 User user = userDAO.findOne(id);
			 
			 String name = request.getParameter("name");
			 String lastname = request.getParameter("lastname");
			 String email = request.getParameter("email");
			 String password = request.getParameter("password");
			 String telephone = "593"+request.getParameter("telephone").substring(1);;
			 
			 boolean isSame = user.getEmail().equals(email);
			 
			 user.setName(name);
			 user.setLastName(lastname);
			 user.setTelephone(telephone);
			 user.setEmail(email);
			 user.setPassword(password);
			 
			 
			 if(userDAO.exists(email) != null && !isSame) {
				 response.getWriter().write("{\"result\": false, \"message\": \"Ya existe un usuario con ese Email\"}");					
				 return;
			 }
			 
			 try {
				 if(userDAO.update(user)) {       		        		
        			HttpSession session = request.getSession();
        			session.setAttribute(sc.getSessionName(), user);       	       		
					response.getWriter().write("{\"result\": true, \"message\": \"Usuario actualizado correctamente\"}");										 
				 }else {
					 response.getWriter().write("{\"result\": false, \"message\": \"Error al actualizar\"}");										 
				 }
			 } catch(Exception e) {
				 response.getWriter().write("{\"result\": false, \"message\": \"No se han podido actualizar los datos\"}");					
			 }
			 
			 
		 }
	 }

}
