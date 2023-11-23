<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 23/11/2023
  Time: 2:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Calculator</title>
</head>
<body>

<form action="calculator">
    <div>
        <input type="number" name="num1">
        <input type="number" name="num2">
    </div>
    <div>
        <input type="submit" name="operation" value="Addition(+)">
        <input type="submit" name="operation" value="Subtraction(-)">
        <input type="submit" name="operation" value="Multiplication(x)">
        <input type="submit" name="operation" value="Division(/)">
    </div>

</form>
    <h2>Result: ${result}</h2>
    <h2>${error}</h2>
</body>
</html>
