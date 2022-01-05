<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Employees</title>
</head>
<body>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0);
//prevents caching at the proxy server
%>
	<form method ="post" action="update_employees">
		<TABLE BORDER="1">
			<tr>
				<td>ID</td>
				<td>Name</td>
				<td>Type</td>
				<td>username</td>				
				<c:if test="${user.type eq 'Manager'}"><td>password</td></c:if>
				<td>Lead</td>
			</tr>
			<c:forEach var="employee" items="${employees}">
				<tr>
					<td><input type="text" name="ID_${employee.ID}"	value="${employee.ID}" readonly></td>
					<td><input type="text" name="name_${employee.ID}" value="${employee.name}" required></td>
					<c:choose>
						<c:when test="${user.type eq 'Manager'}">
							<td>
							<select name="type_${employee.ID}">
									<option>${employee.type}</option>
									<c:forEach var="emp_type" items="${types}">
										<c:if test="${emp_type ne employee.type}">
											<option>${emp_type}</option>
										</c:if>
									</c:forEach>
							</select>
							</td>
						</c:when>
						<c:otherwise>
							<td><input type="text" name="type_${employee.ID}" value="${employee.type}" readonly></td>
						</c:otherwise>
					</c:choose>
					

					<td><input type="text" name="username_${employee.ID}" value="${employee.username}" readonly></td>
		
					<c:if test="${user.type eq 'Manager'}">
						<td><input type="text" name="password_${employee.ID}" value="${employee.password}" required></td>
					</c:if>
					
					<c:choose>
						<c:when test="${user.type eq 'Manager'}">
							<td>
								<select name="lead_${employee.ID}">
									<option>${employee.lead}</option>
									<c:forEach var="emp" items="${m_leads}">
										<c:if test="${emp.username ne employee.lead}">
											<option>${emp.username}</option>
										</c:if>
									</c:forEach>
								</select>
							</td>
						</c:when>
						<c:otherwise>					
							<td><input type="text" name="lead_${employee.ID}" value="${employee.lead}" readonly></td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
		</TABLE>
			<br>${message}
			<c:if test="${user.type eq 'Manager'}">
				<br> <input type="SUBMIT" value="Update">
			</c:if>
	</form>
	<form action="index.jsp">
		<br> <input type="SUBMIT" value="Back/Cancel">
	</form>
</body>
</html>