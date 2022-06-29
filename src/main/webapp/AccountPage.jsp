<%@ page import="java.util.ResourceBundle" %>
<%@ page import="com.example.finalproject3.Entity.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="WEB-INF/LocalTagLib.tld" prefix="m" %>
<%--

  Created by IntelliJ IDEA.
  User: nazik
  Date: 30.03.2022
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title><m:locale-tag key="AccPage"/></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">

    <header class="d-flex justify-content-center py-3">
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="/" class="nav-link active" aria-current="page"><m:locale-tag key="Home"/></a></li>
            <li class="nav-item"><a href="/?command=stations&page=1" class="nav-link"><m:locale-tag key="Stations"/></a></li>
            <li class="nav-item"><a href="/?command=tickets&page=1" class="nav-link"><m:locale-tag key="Tickets"/></a></li>
            <li class="nav-item"><form action="/"  method="post">
                <input type="hidden" name="command" value="signOut">
                <button type="submit" class="nav-link"><m:locale-tag key="SignOut"/></button></form></li>
        </ul>
        <span class="fs-4" style="position: absolute;right: 15px">${user.getName()}</span>
        <p><small style="margin-top:40px;position: absolute;right: 15px" >${user.getMoney()} UAH</small></p>
    </header>
</div>
<hr>
<div>
    <div>
        <a class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-dark text-decoration-none">
            <svg class="bi me-2" width="40" height="32"><use xlink:href="#bootstrap"></use></svg>
        </a>
    </div>
</div>
<c:if test="${error!=null&&error.equals('NoPermission')}">
    <p><small style="margin-top:40px;margin-left: 47%; color: red" ><m:locale-tag key="NoPermission"/></small></p>
</c:if>
<ul class="nav nav-pills flex-column mb-auto" style="margin-left: 25px; ">
    <li class="nav-item">
        <a href="/?command=trains&page=1" class="nav-link active" aria-current="page" style="width: 250px;">
            <svg class="bi me-2" width="16" height="16"><use xlink:href="#home"></use></svg>
            <m:locale-tag key="BuyTicket"/>
        </a>
    </li>
    <li> <a href="/?command=changeMoneyPage" class="nav-link active" aria-current="page" style="width: 250px;margin-top: 15px">
        <svg class="bi me-2" width="16" height="16"><use xlink:href="#home"></use></svg>
        <m:locale-tag key="ChangeMoney"/>
    </a></li>
<%--    <li class="nav-item" style="margin-top: 15px">--%>
<%--     <a href="Messages" class="nav-link active" aria-current="page" style="width: 250px;"> <svg class="bi me-2" width="16" height="16">--%>
<%--         <use xlink:href="#home"></use></svg>--%>
<%--         ${Messages}</a></li>--%>
    <c:if test='${user.isAdmin()}'>
        <li class="nav-item" style="margin-top: 15px">
            <a href="/?command=createRoutePage" class="nav-link active" aria-current="page"
               style="width: 250px;"><svg class="bi me-2" width="16"
           height="16"><use xlink:href="#home"></use></svg>
                <m:locale-tag key="CreateRoute"/>
        </a></li>

<%--        <li class="nav-item" style="margin-top: 15px">--%>
<%--        <a href="/CancelTrain" class="nav-link active" aria-current="page"--%>
<%--           style="width: 250px;"><svg class="bi me-2" width="16"--%>
<%--                     height="16"><use xlink:href="#home"></use></svg>--%>
<%--                ${CancelTrain}--%>
<%--            </a></li>--%>
<%--        </li>--%>
         <li class="nav-item" style="margin-top: 15px">
         <a href="/?command=addStationPage" class="nav-link active" aria-current="page" style="width: 250px;"><svg
                 class="bi me-2" width="16" height="16"><use xlink:href="#home"></use></svg>
             <m:locale-tag key="AddStation"/>
            </a>
            </li>
        <li class="nav-item" style="margin-top: 15px">
         <a href="/?command=planTrainPage" class="nav-link active" aria-current="page"
            style="width: 250px"> <svg class="bi me-2" width="16" height="16"><use xlink:href="#home"></use></svg>
             <m:locale-tag key="PlanTrain"/>
                </a></li>
    </c:if>

    </ul>
    <footer class="py-3 my-4" style=" position: absolute;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 80px;">
        <ul class="nav justify-content-center border-bottom pb-3 mb-3">
            <form action="/" method="post">
                <input type="hidden" name="command" value="changeToUA">
                <input type="hidden" name="prev" value="/">
                <li class="nav-item"><button type="submit">Українська мова</button></li></form>
            <form action="/" method="post">
                <input type="hidden" name="command" value="changeToEn">
                <input type="hidden" name="prev" value="/">
                <li class="nav-item"><button type="submit">English language</button></li></form>  </ul>
    </footer>
</body>
</html>