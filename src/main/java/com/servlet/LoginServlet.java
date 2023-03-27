package com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.constants.SystemConstant;
import com.dao.ImagesDAO;
import com.dao.RentalDAO;

@WebServlet(name = "loginServlet", urlPatterns = {"/login"})
@MultipartConfig
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	SystemConstant sc = new SystemConstant();
	ImagesDAO houseDAO = new ImagesDAO();
	RentalDAO rentalDAO = new RentalDAO();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String pathInfo = request.getPathInfo();
	    
	    if (pathInfo == null) {
	    	request.getRequestDispatcher("./index.jsp").forward(request, response);	    		    	
	    	return;
	    } else {
	    	request.getRequestDispatcher("../404.jsp").forward(request, response);	    		    	
	    }    	    
	}

}
