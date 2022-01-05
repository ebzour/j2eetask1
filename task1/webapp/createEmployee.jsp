<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Employee</title>
</head>
<body>
	<p>Enter Employee Details :<br></p>
	<form method="post" action="create_employee">	
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
				<select name=lead">
					<c:forEach var="employee" items="${employees}">
						<option>${employee.name}</option>
					</c:forEach>
				</select>
				</td>
			</tr>
		</TABLE>
		<br> ${message}
		<br> <input type="SUBMIT" value="Create">
	</form>
	<form action="index.jsp">
		<br> <input type="SUBMIT" value="Back/Cancel">
	</form>
</body>
</html>