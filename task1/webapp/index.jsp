<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Home Page</title>
</head>

<body>
	Welcome ${user.name} !
	<c:if test="${empty user}">
		<c:redirect url="/login" />
	</c:if>
	<c:choose>
		<c:when test="${user.type eq 'Manager'}">
			<form action="view_employees">
				<br> <input type="SUBMIT" value="View/Update Employees">
			</form>

			<form method="get" action="create_employee">
				<br> <input type="SUBMIT" value="Create Employee">
			</form>

			<form method="get" action="view_tasks">
				<br> <input type="SUBMIT" value="View/Update All Tasks">
			</form>

		</c:when>

		<c:when test="${user.type eq 'Leader'}">
			<form action="view_employees">
				<br> <input type="SUBMIT" value="View Employees">
			</form>

			<form method="get" action="view_tasks">
				<br> <input type="SUBMIT" value="View/Update All Tasks">
			</form>

			<form action="create_task">
				<br> <input type="SUBMIT" value="Create Task">
			</form>

		</c:when>

		<c:when test="${user.type eq 'Developer'}">
			<form action="view_employees">
				<br> <input type="SUBMIT" value="View Info">
			</form>
			<form action="view_tasks">
				<br> <input type="SUBMIT" value="View Tasks">
			</form>
			<form action="create_task">
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