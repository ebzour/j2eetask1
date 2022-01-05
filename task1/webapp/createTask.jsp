<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Task</title>
</head>
<body>
	<p>
		Enter Task Details :<br>
	</p>
	<form method="post" action="create_task">
		<TABLE>
			<tr>
				<td>Title</td>
				<td>Status</td>
				<td>Comment</td>
				<td>User</td>
			</tr>
			<tr>
				<td><input type="text" name="title" required></td>
				<td><select name="status">
						<c:choose>
							<c:when test="${user.type eq 'Leader'}">
								<option>Done</option>
								<option>In Progress</option>
								<option>Pending Approval</option>
								<option>Canceled</option>
							</c:when>

							<c:when test="${user.type eq 'Developer'}">
								<option>Pending Approval</option>
							</c:when>
							
						</c:choose> 
				</select></td>
				<td><input type="text" name="comment" required></td>
				<td><select name="username">
						<c:choose>
							<c:when test="${user.type eq 'Leader'}">
								<option>${user.username}</option>
								<c:forEach var="employee" items="${employees}">
									<option>${employee.username}</option>
								</c:forEach>
							</c:when>
							<c:when test="${user.type eq 'Developer'}">
								<option>${user.username}</option>
							</c:when>
						</c:choose> 
				</select></td>
			</tr>
		</TABLE>
		<br> ${message} <br> <input type="SUBMIT" value="Create">
	</form>
	<form action="index.jsp">
		<br> <input type="SUBMIT" value="Back/Cancel">
	</form>
</body>
</html>