<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>

<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html>
<head>
<title>Home Page</title>
</head>
<body>
	<c:choose>
		<c:when test="${user.type eq 'Manager'}">
			<form action="viewEmployees.do">
				<br> <input type="SUBMIT" value="View/Update Employees">
			</form>

			<form method="get" action="createEmployee.do">
				<br> <input type="SUBMIT" value="Create Employee">
			</form>

			<form method="get" action="viewTasks.do">
				<br> <input type="SUBMIT" value="View/Update All Tasks">
			</form>

		</c:when>

		<c:when test="${user.type eq 'Leader'}">
			<form action="viewEmployees.do">
				<br> <input type="SUBMIT" value="View Employees">
			</form>

			<form method="get" action="viewTasks.do">
				<br> <input type="SUBMIT" value="View/Update All Tasks">
			</form>

			<form action="createTask.do">
				<br> <input type="SUBMIT" value="Create Task">
			</form>

		</c:when>

		<c:when test="${user.type eq 'Developer'}">
			<form action="viewEmployees.do">
				<br> <input type="SUBMIT" value="View Info">
			</form>
			<form action="viewTasks.do">
				<br> <input type="SUBMIT" value="View Tasks">
			</form>
			<form action="createTask.do">
				<br> <input type="SUBMIT" value="Create Task">
			</form>
		</c:when>

		<c:otherwise>
		</c:otherwise>
	</c:choose>
	<div style="text-align: left">
		<jsp:include page="logout.jsp" />
	</div>
</body>
</html>