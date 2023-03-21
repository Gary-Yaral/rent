<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@page import="com.model.Tenant"%>
<%@page import="com.constants.SystemConstant"%>
<%
	SystemConstant sc = new SystemConstant();
	Tenant tenant = null;
	String fullname = "";
	if(session.getAttribute(sc.getSessionTenantName()) == null) {
		response.sendRedirect("./");
	} else {
		tenant = (Tenant) session.getAttribute(sc.getSessionTenantName());
		fullname = tenant.getName() + " " + tenant.getLastname();
	}
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" />
<link
	href="https://cdn.datatables.net/v/dt/dt-1.13.4/datatables.min.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="https://cdn.datatables.net/responsive/2.4.1/css/responsive.dataTables.min.css">
<link rel="stylesheet" href="./css/page.css" />
<title>Panel | Casas</title>
</head>
<body>
	<div class="header">
		<div class="header-left">
			<button class="hamburger">
				<span></span>
				<span></span>
				<span></span>
			</button>
			<div class="bussines"><%=sc.getAppName()%></div>
		</div>
		<div class="header-right">
			<div>
				<%=fullname%>
			</div>
			<button class="btn-path" id="btn-logout">
				<i class="fa-solid fa-right-from-bracket"></i>
			</button>
		</div>
	</div>
	<div class="content">
		<div class="banner">
			<img src="./assets/banner.jpg" />
		</div>
		<div class="content-title"><span>Casas para arrendar</span></div>
		<div class="container">
			<div class="row" id="cards"></div>
		</div>
		<footer><%=sc.getAppName()%> ©</footer>
	</div>
	<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
	<script
		src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
	<script
		src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap5.min.js"></script>
	<script
		src="https://cdn.datatables.net/responsive/2.4.1/js/dataTables.responsive.min.js"></script>
	<script src="//cdn.jsdelivr.net/npm/sweetalert2@10"></script>
	<script src="./js/page.js"></script>
	<script src="./js/logout.js"></script>
</body>
</html>