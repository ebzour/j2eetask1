<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

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
	<form method="post" action="updateEmployees.do">
		<TABLE id="empView" BORDER="1">
			<tr>
				<th onclick="sortTable(0)">id</th>
				<th onclick="sortTable(1)">Name</th>
				<th>Type</th>
				<th onclick="sortTable(2)">username</th>
				<c:if test="${user.type eq 'Manager'}">
					<th>password</th>
				</c:if>
				<th>Lead</th>
			</tr>
			<c:forEach var="employee" items="${employees}">
				<tr>
					<td><input type="text" name="id_${employee.id}"
						value="${employee.id}" readonly></td>
					<td><input type="text" name="name_${employee.id}"
						value="${employee.name}" required></td>
					<c:choose>
						<c:when test="${user.type eq 'Manager'}">
							<td><select name="type_${employee.id}">
									<option>${employee.type}</option>
									<c:forEach var="emp_type" items="${types}">
										<c:if test="${emp_type ne employee.type}">
											<c:if test="${emp_type ne 'Manager'}">
												<option>${emp_type}</option>
											</c:if>
										</c:if>
									</c:forEach>
							</select></td>
						</c:when>
						<c:otherwise>
							<td><input type="text" name="type_${employee.id}"
								value="${employee.type}" readonly></td>
						</c:otherwise>
					</c:choose>


					<td><input type="text" name="username_${employee.id}"
						value="${employee.username}" readonly></td>

					<c:if test="${user.type eq 'Manager'}">
						<td><input type="password" name="password_${employee.id}"
							value="${employee.password}" required></td>
					</c:if>

					<c:choose>
						<c:when test="${user.type eq 'Manager'}">
							<td><select name="lead_${employee.id}">
									<option>${employee.lead}</option>
									<c:forEach var="emp" items="${m_leads}">
										<c:if test="${emp.username ne employee.lead}">
											<option>${emp.username}</option>
										</c:if>
									</c:forEach>
							</select></td>
						</c:when>
						<c:otherwise>
							<td><input type="text" name="lead_${employee.id}"
								value="${employee.lead}" readonly></td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
		</TABLE>
		<br>${message}
		<c:if test="${user.type eq 'Manager'}">
			<br>
			<input type="SUBMIT" value="Update">
		</c:if>
	</form>
	<form action="indexDef.do">
		<c:set var="headerMsg" scope="session" value="Welcome ${user.name} !" />
		<br> <input type="SUBMIT" value="Back/Cancel">
	</form>
</body>

<script type="text/javascript">
function sortTable(n) {
	  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
	  table = document.getElementById("empView");
	  switching = true;
	  // Set the sorting direction to ascending:
	  dir = "asc";
	  /* Make a loop that will continue until
	  no switching has been done: */
	  while (switching) {
	    // Start by saying: no switching is done:
	    switching = false;
	    rows = table.rows;
	    /* Loop through all table rows (except the
	    first, which contains table headers): */
	    for (i = 1; i < (rows.length - 1); i++) {
	      // Start by saying there should be no switching:
	      shouldSwitch = false;
	      /* Get the two elements you want to compare,
	      one from current row and one from the next: */
	      x = rows[i].getElementsByTagName("TD")[n];
	      y = rows[i + 1].getElementsByTagName("TD")[n];
	      /* Check if the two rows should switch place,
	      based on the direction, asc or desc: */
	      if (dir == "asc") {
	        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
	          // If so, mark as a switch and break the loop:
	          shouldSwitch = true;
	          break;
	        }
	      } else if (dir == "desc") {
	        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
	          // If so, mark as a switch and break the loop:
	          shouldSwitch = true;
	          break;
	        }
	      }
	    }
	    if (shouldSwitch) {
	      /* If a switch has been marked, make the switch
	      and mark that a switch has been done: */
	      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
	      switching = true;
	      // Each time a switch is done, increase this count by 1:
	      switchcount ++;
	    } else {
	      /* If no switching has been done AND the direction is "asc",
	      set the direction to "desc" and run the while loop again. */
	      if (switchcount == 0 && dir == "asc") {
	        dir = "desc";
	        switching = true;
	      }
	    }
	  }
	}
</script>

</html>