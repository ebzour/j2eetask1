<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Login Page</title>
</head>

<body>
<form method="POST" action="login" >
 <p></p>
	Username : <input type="text" name="username" required>
	<br><br>
	Password : <input type="password" name="password" required>
 <br>
 <br>${message}<br>
 <input type="SUBMIT" value="Login">
</form>
</body>
</html>