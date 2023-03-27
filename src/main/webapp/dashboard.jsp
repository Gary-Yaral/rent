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
		String telephone = "0"+user.getTelephone().substring(3);
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
			<button class="btn-path goTo" id="/edit-user"><i class="fa-solid fa-user"></i><span>Usuario</span></button>
			<button class="btn-path" id="btn-logout"><i class="fa-solid fa-right-from-bracket"></i><span>Salir</span></button>
		</div>
		<div class="content">
			<h3>${page}</h3>
		<% if(view.equals("user")) { %>
			<form class="form-edit">
			<input type="hidden" name="user_id" value="<%=user.getId()%>">
				<div class="form-group">
					<label for="name">Nombres</label>
					<input type="text" value="<%=user.getName() %>" class="form-control" id="username" name="name" placeholder="Ingrese sus nombres" required>
				</div>
				<div class="form-group">
					<label for="lastname">Apellidos</label>
					<input type="text" value="<%=user.getLastName() %>" class="form-control" id="lastname" name="lastname" placeholder="Ingrese sus apellidos" required>
				</div>
				<div class="form-group">
					<label for="telephone">Celular</label>
					<input type="tel" value="<%=telephone %>" class="form-control" pattern="0[0-9]{9}" id="telephone" name="telephone" placeholder="Whatsapp - 094555545" required>
				</div>
				<div class="form-group">
					<label for="email">Email</label>
					<input type="email" value="<%=user.getEmail() %>" class="form-control" id="email" name="email" placeholder="Ingrese su email" required>
				</div>
				<div class="form-group">
					<label for="password">Contraseña</label>
					<input type="password" class="form-control" id="password" name="password" placeholder="Ingrese su contraseña" required>
				</div>
				<div class="form-group mt-2">
					<button type="submit" class="btn btn-primary btn-block">Guardar Cambios</button>
				</div>
			</form>
		<%} %>
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
			</div>
		</div>
	</div>	
		<%}%>
		
	<div class="form-modal hidden" id="all-images">
		<div class="close" id="close">
			<div>+</div>
		</div>
		<div  class="all-images" id="carousel"></div>		
	</div>
	<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
	<script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
	<script	src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap5.min.js"></script>
	<script	src="https://cdn.datatables.net/responsive/2.4.1/js/dataTables.responsive.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha2/dist/js/bootstrap.min.js" integrity="sha384-heAjqF+bCxXpCWLa6Zhcp4fu20XoNIA98ecBC1YkdXhszjoejr5y9Q77hIrv8R9i" crossorigin="anonymous"></script>
	<script src="//cdn.jsdelivr.net/npm/sweetalert2@10"></script>
	<script src="../js/app.js" type="module"></script>
</body>
</html>
<%}%>