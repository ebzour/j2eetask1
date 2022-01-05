<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Tasks</title>
</head>
<body>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0);
//prevents caching at the proxy server
%>
	<form method="post" action="update_tasks">
		<TABLE BORDER="1">
			<tr>
				<td>ID</td>
				<td>Title</td>
				<td>Status</td>
				<td>Comment</td>
				<td>User</td>
			</tr>
			<tr>
				<c:forEach var="task" items="${tasks}">
					<tr>
						<td><input type="text" name="ID_${task.ID}"	value="${task.ID}" required></td>
						<td><input type="text" name="title_${task.ID}" value="${task.title}" required></td>
						<c:choose>
							<c:when test="${user.type eq 'Manager'}">
								<c:choose>
									<c:when test="${task.status eq 'Pending Approval'}">
										<td><select name="status_${task.ID}">
												<option>Pending Approval</option>
												<option>In Progress</option>
												<option>Canceled</option>
										</select></td>
									</c:when>
									<c:when test="${task.status ne 'Pending Approval'}">
										<td><input type="text" name="status_${task.ID}"	value="${task.status}" readonly></td>
									</c:when>
								</c:choose>
							</c:when>
							
							<c:when test="${user.type eq 'Leader'}">
								<c:choose>
									<c:when test="${task.status eq 'Pending Approval'}">
										<td><select name="status_${task.ID}">
												<option>Pending Approval</option>
												<option>In Progress</option>
												<option>Canceled</option>
										</select></td>
									</c:when>
									<c:when test="${task.status ne 'Pending Approval'}">
										<td><select name="status_${task.ID}">
												<option>${task.status}</option>
												<c:forEach  var="t_status" items="${task_status}">
													<c:if test="${t_status ne task.status}">
													<option>${t_status}</option>
													</c:if>
												</c:forEach>
										</select></td>
									</c:when>
								</c:choose>
							</c:when>

							<c:when test="${user.type eq 'Developer'}">
									<td><input type="text" name="status_${task.ID}"	value="${task.status}" readonly></td>
							</c:when>
						</c:choose>

						<td><input type="text" name="comment_${task.ID}" value="${task.comment}" required></td>
						<c:choose>
							<c:when test="${user.type eq 'Developer'}">
								<td><input type="text" name="emp_user_${task.ID}" value="${task.emp_user}" readonly></td>
							</c:when>
							<c:otherwise>
								<td><select name="emp_user_${task.ID}">
									<option>${task.emp_user}</option>
									<c:forEach var="emp_user" items="${emp_users}">
										<c:if test="${emp_user.username ne task.emp_user}">
											<option>${emp_user.username}</option>
										</c:if>
									</c:forEach>
								</select></td>								
							</c:otherwise>						
						</c:choose>
					</tr>
				</c:forEach>
			</tr>
		</TABLE>
		<br>${message}
		<br> <input type="SUBMIT" value="Update">
	</form>
	<form action="index.jsp">
		<br> <input type="SUBMIT" value="Back/Cancel">
	</form>
</body>
</html>