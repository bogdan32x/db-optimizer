<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
 
<html>
 
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<title>Spring Greetings</title>
</head>
 
<body>
 
<h1>Schema Text</h1>
<form:form action="home.do"> 	
	<table>
		<tr>
			<td>Add your greeting:</td>
			<td>
				<textarea name="schemaText" rows="20" cols="75"></textarea>
			</td>
		</tr>	
		<tr>
			<td colspan="2" align="center">
				<input type="submit" value="Submit" />	
			</td>
		</tr>
	</table>
</form:form>
 
</body>
</html>