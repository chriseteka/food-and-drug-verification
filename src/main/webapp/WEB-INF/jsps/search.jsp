<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>AUTHENTICITY CHECK</title>
</head>
<body>
	<div align="center">
		<h3>
			<i>SEARCH A PRODUCT BY THEIR PRODUCT ID TO CONFIRM THEIR AUTHENTICITY</i><br>
		</h3>
		<hr>
		<i style="color: red">YOU MAY TYPE IN THE PRODUCT ID WITH OR WITHOUT THE HYPHEN i.e "-"</i>
	</div>

	<div align="center">
		<form action="/product" method="post">
			<p>
			ENTER PRODUCT ID HERE: <input type="text" name="productId"><br>
			<p>
			<input type="submit" value="submit">
		</form>
	</div>
	<hr>
	<div align="center">
	<i>
		RESET YOUR PASSWORD HERE
	</i>
	</div>
	<div align="center">
		<form action="/passwordreset" method="post">
			<p>
			ENTER YOUR EMAIL HERE: <input type="text" name="emailaddr"><br>
			<p>
			<input type="submit" value="submit">
		</form>
	</div>
	<hr>
	#####################################################################################################
	<hr>
	<div align="center">
	<i>
		TYPE IN YOUR RESET CODE TO PROCEED
	</i>
	</div>
	<div align="center">
		<form action="/passwordreset/proceed" method="post">
			<p>
				ENTER THE RESET CODE SENT TO YOUR MAIL HERE: <br>
				<input type="text" name="resetCode"><br>
			<p>
			<input type="submit" value="submit">
		</form>
	</div>
	<hr>
	#####################################################################################################
	<hr>
	<div align="center">
	<i>
		TYPE IN YOUR OLD AND NEW PASSWORD TO EFFECT CHANGE
	</i>
	</div>
	<div align="center">
		<form action="/user/passwordreset" method="post">
			<p>
				ENTER YOUR EMAIL ADDRESS HERE:
				<input type="text" name="email"><br>
				ENTER YOUR OLD PASSWORD HERE:
				<input type="text" name="oldpassword"><br>
				ENTER YOUR NEW PASSWORD HERE:
				<input type="text" name="newpassword"><br>
			<p>
			<input type="submit" value="submit">
		</form>
	</div>
</body>
</html>