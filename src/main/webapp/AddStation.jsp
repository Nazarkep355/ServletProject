<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Quant
  Date: 24.05.2022
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="WEB-INF/LocalTagLib.tld" prefix="m" %>
<html>
<head>
    <title><m:locale-tag key="AddStation"/></title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">

  <header class="d-flex justify-content-center py-3">
    <ul class="nav nav-pills">
      <li class="nav-item"><a href="/" class="nav-link active" aria-current="page"><m:locale-tag key="Home"/></a></li>
      <li class="nav-item"><a href="/?command=stations&page=1" class="nav-link"><m:locale-tag key="Stations"/></a></li>
      <li class="nav-item"><a href="/?command=tickets&page=1"  class="nav-link"><m:locale-tag key="Tickets"/></a></li>
      <li class="nav-item"><form action="/"  method="post">
        <input type="hidden" name="command" value="signOut">
        <button type="submit" class="nav-link"><m:locale-tag key="SignOut"/></button></form></li>
    </ul>
    <span class="fs-4" style="position: absolute;right: 15px">${user.getName()}</span>
    <p><small style="margin-top:40px;position: absolute;right: 15px">${user.getMoney()} UAH</small></p>
  </header>
</div>
<hr>
<div class="col-md-10 mx-auto col-lg-5"
     style="margin-top: 150px">
  <form action="/" method="post" class="p-4 p-md-5 border rounded-3 bg-light">
    <input name="command" value="addStation" type="hidden">
    <small class = "text-muted" style="margin-top: 15px; color: darkcyan;text-align: center"><m:locale-tag key="AddStation"/></small>
    <div class="form-floating mb-3">
      <input type="text" name="station" class="form-control" id="floatingInput" placeholder="example"
             pattern="[А-Яа-яA-Za-z.0-9_\-]{2,50}">
      <label for="floatingInput"><m:locale-tag key="EnterStationName"/></label>
    </div>
    <button class="w-100 btn btn-lg btn-primary" style="width: 150px;" type="submit"><m:locale-tag key="AddStation"/></button>
   <c:if test='${error!=null&&error.equals("StationInDB")}'>
     <small class = "text-muted" style="margin-top: 15px; color: red"><m:locale-tag key="StationInDB"/></small>
   </c:if></form>
</div>
<footer class="py-3 my-4" style=" position: absolute;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 80px;">
  <ul class="nav justify-content-center border-bottom pb-3 mb-3">
    <form action="/" method="post">
      <input type="hidden" name="command" value="changeToUA">
      <input type="hidden" name="prev" value="/?command=addStationPage">
      <li class="nav-item"><button type="submit">Українська мова</button></li></form>
    <form action="/" method="post">
      <input type="hidden" name="command" value="changeToEn">
      <input type="hidden" name="prev" value="/?command=addStationPage">
      <li class="nav-item"><button type="submit">English language</button></li></form>
  </ul>
</footer>
</body>
</html>
