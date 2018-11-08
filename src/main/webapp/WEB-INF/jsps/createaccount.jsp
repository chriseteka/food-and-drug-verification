<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>CREATE ACCOUNT FORM</title>
</head>
<body>
<div align="center">
	<h3>
		CREATE ACCOUNT FORM
	</h3>
	<hr>
	<div align="center">
		<form action="/users/newaccount" method="post">
		    Enter your Name: <input type="text" name="fullname" /><br>
		    Enter your email: <input type="text" name="email" /><br>
		    Enter your phone number: <input type="text" name="phone" /><br>
		    Enter your Gender: <input type="text" name="gender" /><br>
		    Enter your address: <input type="text" name="address" /><br>
		    Choose a password: <input type="password" name="password" /><br>
		    <input type="submit" name="submit" value="Submit" />
		</form>
	</div>
</div>
</body>
</html>