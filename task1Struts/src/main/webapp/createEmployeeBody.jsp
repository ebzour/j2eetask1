<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Employee</title>
</head>
<body>	
	<form method="post" action="createEmployee.do">	
		<TABLE>
			<tr>
				<td>Name</td>
				<td>Type</td>
				<td>Username</td>
				<td>Password</td>
				<td>Lead</td>
			</tr>
			<tr>
				<td><input type="text" name="name" required></td>
				<td>
				<select name="type">
					<option>Developer</option>
					<option>Leader</option>
				</select>
				</td>
				<td><input type="text" name="username" required></td>
				<td><input type="text" name="password" required></td>
				<td>
				<select name="lead">
					<c:forEach var="employee" items="${employees}">
						<option>${employee.username}</option>
					</c:forEach>
				</select>
				</td>
			</tr>
		</TABLE>
		<br> ${message}
		<br> <input type="SUBMIT" value="Create">
	</form>
	<form action="indexDef.do">
		<c:set var="headerMsg" scope="session" value="Welcome ${user.name} !" />
		<br> <input type="SUBMIT" value="Back/Cancel">
	</form>
</body>
</html>