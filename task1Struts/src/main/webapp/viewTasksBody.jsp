<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

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
	<form method="post" action="updateTasks.do">
		<TABLE id="tskView" BORDER="1">
			<tr>
				<th onclick="sortTable(0)">id</th>
				<th onclick="sortTable(1)">Title</th>
				<th onclick="sortTable(2)">Status</th>
				<th onclick="sortTable(3)">Comment</th>
				<th onclick="sortTable(4)">User</th>
			</tr>
				<c:forEach var="task" items="${tasks}">
					<tr>
						<td><input type="text" name="id_${task.id}"	value="${task.id}" required></td>
						<td><input type="text" name="title_${task.id}" value="${task.title}" required></td>
						<c:choose>
							<c:when test="${user.type eq 'Manager'}">
								<c:choose>
									<c:when test="${task.status eq 'Pending Approval'}">
										<td><select name="status_${task.id}">
												<option>Pending Approval</option>
												<option>To Do</option>
												<option>Canceled</option>
										</select></td>
									</c:when>
									<c:when test="${task.status ne 'Pending Approval'}">
										<td><input type="text" name="status_${task.id}"	value="${task.status}" readonly></td>
									</c:when>
								</c:choose>
							</c:when>
							
							<c:when test="${user.type eq 'Leader'}">
								<c:choose>
									<c:when test="${task.status eq 'Pending Approval'}">
										<td><select name="status_${task.id}">
												<option>Pending Approval</option>
												<option>To Do</option>
												<option>In Progress</option>
												<option>Canceled</option>
										</select></td>
									</c:when>
									<c:when test="${task.status ne 'Pending Approval'}">
										<td><select name="status_${task.id}">
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
									<td><input type="text" name="status_${task.id}"	value="${task.status}" readonly></td>
							</c:when>
						</c:choose>

						<td><input type="text" name="comment_${task.id}" value="${task.comment}" required></td>
						<c:choose>
							<c:when test="${user.type eq 'Developer'}">
								<td><input type="text" name="emp_user_${task.id}" value="${task.emp_user}" readonly></td>
							</c:when>
							<c:otherwise>
								<td><select name="emp_user_${task.id}">
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
		</TABLE>
		<br>${message}
		<br> <input type="SUBMIT" value="Update">
	</form>
	<form action="indexDef.do">
			<c:set var="headerMsg" scope="session" value="Welcome ${user.name} !" />
		<br> <input type="SUBMIT" value="Back/Cancel">
	</form>
</body>

<script type="text/javascript">
function sortTable(n) {
	  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
	  table = document.getElementById("tskView");
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