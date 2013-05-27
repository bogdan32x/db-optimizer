<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DB Parser</title>

<link rel="stylesheet" href="../css/global.css" type="text/css">
</head>

<body>

	<div class="container">
		<div class="header">
			<span>Schema Text</span>
		</div>
		<div class="menu-left"></div>
		<div class="content">
			<form:form action="home.do">
				<span id="greetingMessage">Add your greeting:</span>

				<textarea name="schemaText" rows="20" cols="75"></textarea>
				<input type="submit" value="Submit">
			</form:form>
		</div>
	</div>
</body>
</html>