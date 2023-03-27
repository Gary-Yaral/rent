<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@page import="com.model.User"%>
<%@page import="com.constants.SystemConstant"%>
<%
 	User user = null;
	SystemConstant sc = new SystemConstant();
 	if(session.getAttribute(sc.getSessionName()) == null) {
		response.sendRedirect("../");
	} else {
		user = (User) session.getAttribute(sc.getSessionName());
		String house = (String) request.getAttribute("house");
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link href="https://fonts.googleapis.com/css?family=Roboto:400,700"
	rel="stylesheet">
<!-- Booststrap -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" />

<link rel="stylesheet" href="../css/styles_new.css">
<title>Agregar | Registro</title>
</head>
<body>
	<header>
		<a href="./houses"><i class="fa-solid fa-circle-left"></i></a> <h3>Casas</h3>
	</header>

	<main>
		<div class="d-grid">
			<form>
				<h4 class="form-title" id="form-house-title">Editar</h4>
				<input type="hidden" name="house_id" value="<%=house%>" >
				<div>
					<label for="title">Titulo de post</label> 
					<input type="text" name="title" required/>
				</div>
				<div>
					<label for="province">Provincia</label> 
					<select name="province" id="province" required></select>
				</div>
				<div>
					<label for="canton">Cantón</label> <select name="canton"
						id="canton" required></select>
				</div>
				<div>
					<label for="address">Dirección</label> <input type="text"
						name="address" placeholder="Dirección" required>
				</div>
				<div>
					<label for="price">Costo</label> <input type="number" name="price"
						placeholder="Costo" id="price" required>
				</div>
				<div>
					<label for="bathrooms">Baños</label> <select name="bathrooms"
						id="bathrooms" required></select>
				</div>
				<div>
					<label for="rooms">Habitaciones</label> <select name="rooms"
						id="rooms" required></select>
				</div>
				<div>
					<label for="area">Área</label> <input type="number" name="area"
						id="area" placeholder="mt cuadradados" required>
				</div>
				<div>
					<label for="selec-status">Estado</label>
					<select name="status" id="select-status" required>
						<option value="DISPONIBLE">DISPONIBLE</option>
						<option value="NOT DISPONIBLE">NO DISPONIBLE</option>
					</select>
				</div>
				<div>
					<label for="description">Descripción</label>
					<textarea rows="5" name="description" placeholder="Escribir..." required></textarea>
				</div>
				<div>
					<label for="images">Cargar imagenes</label>
					<input type="file" multiple id="images" name="images[]">
				</div>
				<div>
					<button type="submit" class="btn btn-primary edit-house">Guardar cambios</button>
				</div>
			</form>
			<div id="preview" class="preview"></div>
			<div id="errors-imgs" class="errors-imgs"></div>			
		</div>
	</main>

	<!-- Bootstrap y jQuery -->

	<script src="https://code.jquery.com/jquery-3.4.0.min.js"
		integrity="sha256-BJeo0qm959uMBGb65z40ejJYGSgR7REI4+CW1fNKwOg="
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
		integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
		integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
		crossorigin="anonymous"></script>

	<!--  /Bootstrap y jQuery -->
	<script src="../js/file_multiple2.js" type="module"></script>
</body>
</html>

<%}%>