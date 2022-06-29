<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nazik
  Date: 01.04.2022
  Time: 17:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="WEB-INF/LocalTagLib.tld" prefix="m" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><m:locale-tag key="Register"/></title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
    </head>
    <body>
    <div class="container">
        <header class="d-flex justify-content-center py-3">
            <ul class="nav nav-pills">
                <li class="nav-item"><a href="/" class="nav-link active" aria-current="page"><m:locale-tag key="Home"/></a></li>
                <li class="nav-item"><a href="/?command=stations&page=1" class="nav-link"><m:locale-tag key="Stations"/></a></li>
            </ul>
        </header>
    </div>

    <div class="col-md-10 mx-auto col-lg-5">
        <form action="/" method="post" class="p-4 p-md-5 border rounded-3 bg-light">
            <input name="command" type="hidden" value="register">
            <div class="form-floating mb-3">
                <input type="text" required="required" name="email" class="form-control" id="floatingInput" placeholder="name@example.com"
                >
                <label for="floatingInput"><m:locale-tag key="EmailAddress"/></label>
            </div>
            <div class="form-floating mb-3">
                <input type="text" name="name" required="required" class="form-control" id="floatingNameInput" placeholder="John Smith">
                <label for="floatingInput"><m:locale-tag key="Username"/></label>
            </div>
            <div class="form-floating mb-3">
                <input type="password" name="password" required="required" class="form-control"
                       id="floatingPassword" placeholder="Password" title="">
                <label for="floatingPassword"><m:locale-tag key="Password"/></label>
            </div>

            <button class="w-100 btn btn-lg btn-primary" style="width: 150px;" type="submit"><m:locale-tag key="Register"/></button>
            <c:if test='${requestScope.get("error")!=null&&requestScope.get("error").equals("EmailInUse")}'>
                <small class="text-muted" style="margin-top:15px;color: red "><m:locale-tag key="EmailInUse"/></small>
            </c:if>
            <c:if test='${requestScope.get("error")!=null&&requestScope.get("error").equals("EnterWrongFormat")}'>
                <small class = "text-muted" style="margin-top: 15px"><m:locale-tag key="EnterWrongFormat"/></small>
            </c:if>
        </form>
    </div>
<footer class="py-3 my-4" style=" position: absolute;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 80px;">
    <ul class="nav justify-content-center border-bottom pb-3 mb-3">
        <form action="/" method="post">
            <input type="hidden" name="command" value="changeToUA">
            <input type="hidden" name="prev" value="/?command=registerPage">
            <li class="nav-item"><button type="submit">Українська мова</button></li></form>
        <form action="/" method="post">
            <input type="hidden" name="command" value="changeToEn">
            <input type="hidden" name="prev" value="/?command=registerPage">
            <li class="nav-item"><button type="submit">English language</button></li></form>
    </ul>
</footer>
</body>
</html>
