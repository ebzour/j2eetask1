<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Task</title>
</head>
<body>
	<form method="post" action="createTask.do">
		<TABLE>
			<tr>
				<td>Title</td>
				<c:if test="${user.type eq 'Leader'}">
					<td>Status</td>
				</c:if>
				<td>Comment</td>
				<c:if test="${user.type eq 'Leader'}">
					<td>User</td>
				</c:if>
			</tr>
			<tr>
				<td><input type="text" name="title" required></td>
				<c:choose>
					<c:when test="${user.type eq 'Leader'}">
						<td><select name="status">
						<option>Done</option>
								<option>To Do</option>
								<option>In Progress</option>
								<option>Pending Approval</option>
								<option>Canceled</option>
						</select></td>
					</c:when>			
				</c:choose>
			
				<td><input type="text" name="comment" required></td>
				<c:choose>
					<c:when test="${user.type eq 'Leader'}">
						<td><select name="username">
						<option>${user.username}</option>
								<c:forEach var="employee" items="${employees}">
									<option>${employee.username}</option>
								</c:forEach>
						</select></td>
					</c:when>
				</c:choose>
			</tr>
		</TABLE>
		<br> ${message} <br> <input type="SUBMIT" value="Create">
	</form>
	<form action="indexDef.do">
		<c:set var="headerMsg" scope="session" value="Welcome ${user.name} !" />
		<br> <input type="SUBMIT" value="Back/Cancel">
	</form>
</body>
</html>