<%@page import="com.constants.SystemConstant"%>
<%	
	SystemConstant sc = new SystemConstant();
	if(session.getAttribute(sc.getSessionName()) != null) {
   		response.sendRedirect("./dashboard/");
  	}	
	
	if(session.getAttribute(sc.getSessionTenantName()) != null) {
	   response.sendRedirect("./page");
	}
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Login</title>
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
					<h2 class="text-center mb-4">Iniciar Sesión</h2>
					<div class="form-group">
						<select name="type">
						<option value="admin">Administrador</option>
						<option value="tenant">Usuario</option>
						</select>
					</div>
					<div class="form-group">
						<label for="email">Usuario</label>
						<input type="email" class="form-control" id="username" name="username" placeholder="Ingrese su usuario" required>
					</div>
					<div class="form-group">
						<label for="password">Contraseña</label>
						<input type="password" class="form-control" id="password" name="password" placeholder="Ingrese su contraseña" required>
					</div>
					<div class="form-group mt-2">
						<button type="submit" class="btn btn-primary btn-block">Ingresar</button>
					</div>
					<div class="form-group mt-2">
						<a href="./register" class="btn btn-secondary btn-block">Crear Admin</a>
					</div>
					<div class="form-group mt-2">
						<a href="./register/tenant" class="btn btn-warning btn-block">Crear User</a>
					</div>
					<sub class="text-danger hidden" id="error">Usuario o contraseña incorrectos</sub>
				</form>
			</div>
		</div>
	</div>
	<script src="//cdn.jsdelivr.net/npm/sweetalert2@10"></script>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/js/bootstrap.min.js"></script>
	<script src="./js/login.js"></script>
</body>
</html>