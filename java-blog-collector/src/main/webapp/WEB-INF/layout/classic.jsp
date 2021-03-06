<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles-extras"
	prefix="tilesx"%>

<!-- spring security tablib -->
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:getAsString name="title" /></title>

<!-- bootstrap -->
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">

<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">

<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>

<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>

<!-- jquery validation -->
<script type="text/javascript"
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
</head>
<body>

	<tilesx:useAttribute name="current" />

	<div class="container">
		<!-- navbar -->
		<div class="navbar navbar-default" role="navigation">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="<spring:url value='/' />">JBC</a>
				</div>
				<div class="navbar-collapse collapse">
					<ul class="nav navbar-nav">
						<li class="${current == 'index' ? 'active' : '' }"><a
							href="<spring:url value='/' />">Home</a></li>

						<security:authorize access="hasRole('ROLE_ADMIN')">
							<li class="${current == 'users' ? 'active' : '' }"><a
								href="<spring:url value='/users.html' />">Users</a></li>
						</security:authorize>

						<li class="${current == 'register' ? 'active' : '' }"><a
							href="<spring:url value='/register.html' />">Register</a></li>

						<security:authorize access="! isAuthenticated()">
							<li class="${current == 'login' ? 'active' : '' }"><a
								href="<spring:url value='/login.html' />">Login</a></li>
						</security:authorize>

						<security:authorize access="isAuthenticated()">
							<li class="${current == 'account' ? 'active' : '' }"><a
								href="<spring:url value='/account.html' />">My Account</a></li>
							<li><a href="<spring:url value='/logout' />">Logout</a></li>
						</security:authorize>


					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
			<!--/.container-fluid -->
		</div>

		<tiles:insertAttribute name="body" />

		<br><br>

		<center><tiles:insertAttribute name="footer" /></center>

<%-- 		<div class="navbar navbar-inverse" role="navigator">
			<div class="container">
				<div class="navbar-text">
					<p align="center">
						<tiles:insertAttribute name="footer" />
					</p>
				</div>
			</div>
		</div> --%>

	</div>
</body>
</html>