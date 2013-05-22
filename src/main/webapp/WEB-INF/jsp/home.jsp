<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
 
<html>
 
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	<title>Spring Greetings</title>
</head>
 
<body>
 
<h1>Spring Greetings</h1>
 
Greeting by <b>Anonymous</b><br/>
on <c:out value="<%=new java.util.Date()%>" /><br/>
<textarea readonly rows="20" cols="75">${schemaText}</textarea><br/>			
 
<p><a href="/home/addgreeting.do">Add greeting</a><br/>
<a href="/home/index.do">Home</a>
 
</body>
</html>