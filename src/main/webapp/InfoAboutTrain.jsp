<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.example.finalproject3.Entity.User" %>
<%@ page import="com.example.finalproject3.Entity.Train" %>
<%@ page import="com.example.finalproject3.Utility.Utility" %>
<%@ taglib uri="WEB-INF/LocalTagLib.tld" prefix="m" %>
<%--
  Created by IntelliJ IDEA.
  User: Quant
  Date: 21.05.2022
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title ><m:locale-tag key="InfoAboutTrain"/></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">

    <header class="d-flex justify-content-center py-3">
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="/" class="nav-link active" aria-current="page"><m:locale-tag key="Home"/></a></li>
            <li class="nav-item"><a href="/?command=stations&page=1" class="nav-link"><m:locale-tag key="Stations"/></a></li>
            <c:if test="${user!=null}"> <li class="nav-item"><a href="/?command=tickets&page=1"
                                                class="nav-link"><m:locale-tag key="Tickets"/></a></li>
                <li class="nav-item"><form action="/"  method="post">
                    <input type="hidden" name="command" value="signOut">
                    <button type="submit" class="nav-link"><m:locale-tag key="SignOut"/></button></form></li></c:if>
        </ul>
<c:if test="${user!=null}">
    <span class="fs-4" style="position: absolute;right: 15px">${user.getName()}</span>
    <p><small style="margin-top:40px;position: absolute;right: 15px">${user.getMoney()} UAH</small></p>
</c:if>
    </header>
</div>
<hr>
<div style="min-height: 70%">
    <c:if test='${requestScope.get("error")!=null&&requestScope.get("error").equals("notEnoughFunds")}'>
        <small class = "text-muted" style="margin-left: 40%"><m:locale-tag key="notEnoughFunds"/></small>
    </c:if>

    <c:if test='${requestScope.get("error")!=null&&requestScope.get("error").equals("NoFreeSeats")}'>
        <small class = "text-muted" style="margin-left: 40%"><m:locale-tag key="NoFreeSeats"/></small>
    </c:if>
        <div style="margin-left: 50px">
        <h3><m:locale-tag key="Cost"/>:  ${train.getCost()} </h3>
        <h3><m:locale-tag key="Booked"/> : ${train.getBooked()}/${train.getSeats()}</h3>
    </div>
    <table class="table table-striped table-hover ms-5 " style="max-width: 500px">
        <thead>
        <tr>
            <th scope="col" style="text-align: center"><m:locale-tag key="Station"/></th>
            <th scope="col" style="text-align: center"><m:locale-tag key="ArrivalTime"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="station" items="${train.getStations()}">
            <tr>
                <td scope="row" style="text-align: center">${station.name}</td>
                <td scope="row" style="text-align: center">${Utility.dateToString(train.agenda.get(station))}</td>
            </tr>
        </c:forEach>

        </div>
        </tbody>
    </table>
    <c:if test='${user!=null&&user.isAdmin()}'>
        <form  style="width: 300px;padding-left: 150px;" action="/" method="post">
            <input name="command" type="hidden" value="cancelTrain">
            <input type="hidden" name="trainId" value="${train.getId()}">
       <button type="submit"  class="w-100 btn btn-lg btn-primary"><m:locale-tag key="CancelTrain"/></button>
        </form>
    </c:if>
    <ul style="position: absolute;margin-left: 1000px" class="nav nav-pills">
        <c:if test="${station1==null}">
            <form action="/" method="post">
                <input name="command" type="hidden" value="buyOne">
                <input type="hidden" name="trainId" value="${train.getId()}">
                <button  type="submit" style="width: 250px" class="w-100 btn btn-lg btn-primary"><m:locale-tag key="Buy"/></button>
            </form>
        </c:if>
        <c:if test="${station1!=null}">
            <form action="/" method="post">
                <input name="command" type="hidden" value="buyOne">
                <input type="hidden" name="from" value="${station1}">
                <input type="hidden" name="to" value="${station2}">
                <input type="hidden" name="trainId" value="${train.getId()}">
                <button  type="submit" style="width: 250px" class="w-100 btn btn-lg btn-primary"><m:locale-tag key="Buy"/></button>
            </form>
        </c:if>
    </ul>

</div>
<footer class="py-3 my-4" style=" position: absolute;
        left: 0;
        bottom: 0;
        width: 100%;
        height: 80px;">
    <ul class="nav justify-content-center border-bottom pb-3 mb-3">
<c:if test="${station1!=null}">
    <form action="/" method="post">
        <input type="hidden" name="command" value="changeToUA">
        <input type="hidden" name="prev" value="/?command=trainInfo&trainId=${train.getId()}&from=${station1}&to=${station2}">
        <li class="nav-item"><button type="submit">Українська мова</button></li></form>
    <form action="/" method="post">
        <input type="hidden" name="command" value="changeToEn">
        <input type="hidden" name="prev" value="/?command=trainInfo&trainId=${train.getId()}&from=${station1}&to=${station2}">
        <li class="nav-item"><button type="submit">English language</button></li></form>
</c:if>
    <c:if test="${station1==null}">
        <form action="/" method="post">
            <input type="hidden" name="command" value="changeToUA">
            <input type="hidden" name="prev" value="/?command=trainInfo&trainId=${train.getId()}">
        <li class="nav-item"><button type="submit">Українська мова</button></li></form>
        <form action="/" method="post">
            <input type="hidden" name="command" value="changeToEn">
            <input type="hidden" name="prev" value="/?command=trainInfo&trainId=${train.getId()}">
            <li class="nav-item"><button type="submit">English language</button></li></form>  </c:if></ul>
</footer>
</body>
</body>
</html>
