<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 23/11/2023
  Time: 1:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Sandwich Condiment Selection</title>
</head>
<body>

  <form action="sandwich" method="get">
    <label>Select condiments:</label><br>
    <input type="checkbox" name="condiment" value="mustard"> Mustard<br>
    <input type="checkbox" name="condiment" value="mayo"> Mayo<br>
    <input type="checkbox" name="condiment" value="lettuce"> Lettuce<br>
    <input type="submit" value="Submit">
  </form>
  <h2>Selected condiments:</h2>
  <ul>
    <c:if test="${condiments == null}">
      <h2>Chọn 1 loại gia vị !</h2>
    </c:if>
    <c:forEach var="condiment" items="${condiments}">
      <li>${condiment}</li>
    </c:forEach>
  </ul>

</body>
</html>
