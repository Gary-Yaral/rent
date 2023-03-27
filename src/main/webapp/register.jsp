<%@page import="com.constants.SystemConstant"%>
<%
	SystemConstant sc = new SystemConstant();
	if(session.getAttribute(sc.getSessionTenantName()) != null) {
	   response.sendRedirect("./page");
	  }	 
	
	if(session.getAttribute(sc.getSessionName()) != null) {
		response.sendRedirect("./dashboard/");
	}	
%>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Registrarse</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css">
	<style>
		body {
			background-color: #f8f9fa;
		}
		.form-login {
			background-color: #ffffff;
			padding: 20px;
			margin-top: 50px;
			border-radius: 5px;
			box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
		}
		
		.hidden {
			display: none;
		}
	</style>
</head>
<body>
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-6 col-lg-4">
				<form class="form-login">
					<h2 class="text-center mb-4">Registrarse</h2>
					<div class="form-group">
						<label for="name">Nombres</label>
						<input type="text" class="form-control" id="username" name="name" placeholder="Ingrese sus nombres" required>
					</div>
					<div class="form-group">
						<label for="lastname">Apellidos</label>
						<input type="text" class="form-control" id="lastname" name="lastname" placeholder="Ingrese sus apellidos" required>
					</div>
					<div class="form-group">
						<label for="telephone">Celular</label>
						<input type="tel" class="form-control" pattern="0[0-9]{9}" id="telephone" name="telephone" placeholder="Whatsapp - 094555545" required>
					</div>
					<div class="form-group">
						<label for="email">Email</label>
						<input type="email" class="form-control" id="email" name="email" placeholder="Ingrese su email" required>
					</div>
					<div class="form-group">
						<label for="password">Contraseña</label>
						<input type="password" class="form-control" id="password" name="password" placeholder="Ingrese su contraseña" required>
					</div>
					<div class="form-group mt-2">
						<button type="submit" class="btn btn-primary btn-block">Guardar</button>
					</div>
					<div class="form-group mt-2">
						<a href="./" class="btn btn-secondary btn-block">Inicio</a>
					</div>
					<sub class="text-danger hidden" id="error">Error de usuario o contraseña</sub>
				</form>
			</div>
		</div>
	</div>
	<script src="//cdn.jsdelivr.net/npm/sweetalert2@10"></script>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/js/bootstrap.min.js"></script>
	<script src="./js/register.js" type="module"></script>
</body>
</html>