<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@page import="com.model.User"%>
<%@page import="com.constants.SystemConstant"%>
<%
 	User user = null;
	SystemConstant sc = new SystemConstant();
	String view = ""; 
 	if(session.getAttribute(sc.getSessionName()) == null) {
		response.sendRedirect("../");
	} else {
		user = (User) session.getAttribute(sc.getSessionName());
		view = (String) request.getAttribute("view");
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" />
<link
	href="https://cdn.datatables.net/v/dt/dt-1.13.4/datatables.min.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="https://cdn.datatables.net/responsive/2.4.1/css/responsive.dataTables.min.css">
<link rel="stylesheet" href="../css/style.css" />
<link rel="stylesheet" href="../css/modal.css" />
<title>Panel | Casas</title>
</head>
<body>
	<input type="hidden" id="user" value="<%=user.getId()%>">
	<header>
		<div class="header-left">
			<button class="hamburger">
				<span></span> <span></span> <span></span>
			</button>
			<div class="bussines"><%=sc.getAppName()%></div>
		</div>
		<div class="header-right"><%=user.getName()%> <%=user.getLastName()%></div>

	</header>
	<div class="main">
		<div class="options open" id="sidebar">
			<button class="btn-path goTo" id="/"><i class="fa-solid fa-house"></i><span>Inicio</span></button>
			<button class="btn-path goTo" id="/houses"><i class="fa-solid fa-list"></i><span>Casas</span></button>
			<button class="btn-path" id="btn-logout"><i class="fa-solid fa-right-from-bracket"></i><span>Salir</span></button>
		</div>
		<div class="content">
			<h3>${page}</h3>
		<% if(view.equals("home")) { %>
			<div class="card-container" id="home">
				<div class="card-1">
					<h5 class="card-title">Casas</h5>
					<div class="card-body">
						<div class="card-content card-icon">
							<i class="fa-solid fa-house-user"></i>
						</div>
						<div class="card-content card-number" id="houses-total"></div>
					</div>
				</div>
				<div class="card-2">
					<h5 class="card-title">Disponibles</h5>
					<div class="card-body">
						<div class="card-content card-icon">
							<i class="fa-sharp fa-solid fa-circle-check"></i>
						</div>
						<div class="card-content card-number" id="availables"></div>
					</div>
				</div>				
			</div>
		
		<%}%>
		<% if(view.equals("houses")) { %>
			<a href="new-house" class="btn btn-primary new-house">Agregar</a>
			<div class="container-table">
				<table id="houses" class="table table-striped" style="width: 100%">
					<thead>
						<tr>
							<th>N°</th>
							<th>Fotos</th>
							<th>Provincia</th>
							<th>Cantón</th>
							<th>Dirección</th>
							<th>Precio</th>
							<th>N°Baños</th>
							<th>N°Habitaciones</th>
							<th>Área/Km<sup>2</sup></th>
							<th>Descripción</th>
							<th>Estado</th>
							<th>Opciones</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
				<iframe src="https://www.google.com/maps/embed?pb=!1m10!1m8!1m3!1d997.4506137737776!2d-78.4845747!3d-0.1615789!3m2!1i1024!2i768!4f13.1!5e0!3m2!1ses!2sec!4v1679770532505!5m2!1ses!2sec" width="400" height="300" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
			</div>
		</div>
	</div>	
		<%}%>
		
	<div class="form-modal" id="all-images">
		<div  class="all-images" id="carousel"></div>		
	</div>
	<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
	<script
		src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
	<script
		src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap5.min.js"></script>
	<script
		src="https://cdn.datatables.net/responsive/2.4.1/js/dataTables.responsive.min.js"></script>
	<script src="//cdn.jsdelivr.net/npm/sweetalert2@10"></script>
	<script src="../js/app.js" type="module"></script>
</body>
</html>
<%}%>