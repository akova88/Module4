<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 22/11/2023
  Time: 11:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Currency Converter</h2>
<form action="/converter" method="post">
    <label>Rate:</label>
    <input type="text" name="rate" placeholder="RATE" value="23000"> <br/>
    <label>USD:</label>
    <input type="text" name="usd" placeholder="USD" value="0"><br/>
    <input type="submit" id="submit" value="Converter">
</form>
</body>
</html>
