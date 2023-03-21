<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@page import="com.model.User"%>
<%@page import="com.constants.SystemConstant"%>
<%
	SystemConstant sc = new SystemConstant();
	User user = null;
	if(session.getAttribute(sc.getSessionTenantName()) == null) {
	  response.sendRedirect("../");
	 } else {
		  user = (User) session.getAttribute("user-session");
		  String view = (String) request.getAttribute("view");
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
	<header>
		<div class="header-left">
			<button class="hamburger">
				<span></span> <span></span> <span></span>
			</button>
			<h1 class="bussines">Nombre de la App</h1>
		</div>
		<div class="header-right"><%=user.getName()%> <%=user.getLastName()%></div>

	</header>
	<div class="main">
		<div class="options open" id="sidebar">
			<button class="btn-path goTo" id="/"><i class="fa-solid fa-house"></i><span>Inicio</span></button>
			<button class="btn-path goTo" id="/houses"><i class="fa-solid fa-list"></i><span>Casas</span></button>
			<button class="btn-path goTo" id="/rental"><i class="fa-solid fa-money-bill-1"></i><span>Alquiler</span></button>
			<button class="btn-path" id="btn-logout"><i class="fa-solid fa-right-from-bracket"></i><span>Salir</span></button>
		</div>
		<div class="content">
			<h3>${page}</h3>
		<% if(view.equals("home")) { %>
			<div class="card-container">
				<div class="card-1">
					<h5 class="card-title">Casas</h5>
					<div class="card-body">
						<div class="card-content card-icon">
							<i class="fa-solid fa-house-user"></i>
						</div>
						<div class="card-content card-number">56</div>
					</div>
				</div>
				<div class="card-2">
					<h5 class="card-title">Alquileres</h5>
					<div class="card-body">
						<div class="card-content card-icon">
							<i class="fa-solid fa-users"></i>
						</div>
						<div class="card-content card-number">56</div>
					</div>
				</div>				
			</div>
		
		<%}%>
		<% if(view.equals("houses")) { %>
			<button type="button" class="btn btn-primary new-house" id="btn-new-house">Agregar</button>
			<div class="container-table">
				<table id="houses" class="table table-striped" style="width: 100%">
					<thead>
						<tr>
							<th>N°</th>
							<th>Imagen</th>
							<th>Dirección</th>
							<th>Precio</th>
							<th>Descripción</th>
							<th>Estado</th>
							<th>Opciones</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
		<%}%>
		<% if(view.equals("rental")) { %>
			<div class="container-table">
				<table id="rental" class="table table-striped" style="width: 100%">
					<thead>
						<tr>
							<th>N°</th>
							<th>Casa</th>
							<th>Dirección</th>
							<th>Tiempo</th>
							<th>Pago</th>
							<th>Fecha</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		<% } %>	
	</div>
	
	<!-- Formulario para agregar casas  -->
	
	<div class="form-modal modal-hidden" id="form-modal">
		<form>
			<h4 class="form-title" id="form-house-title">Agregar</h4>
			<img src="" id="preview">
			<input type="file" name="image" id ="img-loaded">
			<input type="number" name="price" placeholder="Precio">
			<input type="text" name="address" placeholder="Dirección">	
			<select name="status" id="select-status"></select>
			<textarea rows="5" name="description" placeholder="Descripción"></textarea>
			<input type="hidden" name="user_id" value="<%=user.getId()%>"/>
			<input type="hidden" name="house_id" value=""/>
			<button type="submit" class="btn btn-primary add-house">Agregar</button>
			<div class="btn btn-danger btn-close"></div>
		</form>
	</div>	
	<!-- Fin -->
	<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
	<script
		src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
	<script
		src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap5.min.js"></script>
	<script
		src="https://cdn.datatables.net/responsive/2.4.1/js/dataTables.responsive.min.js"></script>
	<script src="//cdn.jsdelivr.net/npm/sweetalert2@10"></script>
	<script src="../js/app.js" type="module"></script>
	<script src="../js/logout.js"></script>
</body>
</html>
<%
  }
%>