<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<!DOCTYPE html>
<html>
<head>
<title>Login Page</title>
</head>

<body>

<form method="post" action="login.do" >
 <p></p>
	Username : <input type="text" name="username" required>
	<br><br>
	Password : <input type="password" name="password" required>
 <br>
 <br>${message}<br>
 <input type="SUBMIT" value="Login">
 </form>
</body>
</html>