<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>USER LOGIN</title>
</head>
<body>
<div align="center">
	<h3>
		USER LOGIN PAGE
	</h3>
	<hr>
	<div align="center">
		<form action="/loginuser" method="post">
		    Enter your Email: <input type="text" name="username" /><br>
		    Enter your password: <input type="password" name="password" /><br>
		    <input type="submit" name="submit" value="Submit" />
		</form>
	</div>
</div>
</body>
</html>