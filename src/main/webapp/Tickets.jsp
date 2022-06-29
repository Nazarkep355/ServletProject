<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.example.finalproject3.Entity.Ticket" %>
<%@ page import="com.example.finalproject3.Entity.User" %>
<%@ page import="com.example.finalproject3.Entity.Train" %>
<%@ page import="com.example.finalproject3.Utility.Utility" %>
<%--
  Created by IntelliJ IDEA.
  User: Quant
  Date: 21.05.2022
  Time: 20:57
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="WEB-INF/LocalTagLib.tld" prefix="m" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><m:locale-tag key="Tickets"/></title>
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
<c:if test="${currentPage!=null}">  <nav style="margin-left: 50%" aria-label="Page navigation example mx-auto">
    <ul class="pagination">
        <c:if test="${currentPage>1}">
            <li class="page-item"><a class="page-link"
                                     href="/?command=tickets&page=${currentPage-1}">${currentPage-1}</a></li>
        </c:if><li class="page-item"><a class="page-link" href="/?command=tickets&page=${currentPage}">${currentPage}</a></li>
        <li class="page-item"><a class="page-link" href="/?command=tickets&page=${currentPage+1}">${currentPage+1}</a></li>
    </ul>
</nav></c:if>
<table class="table table-striped table-hover " >
    <thead>
    <tr>
        <th scope="col" ><m:locale-tag key="Cost"/></th>
        <th scope="col" ><m:locale-tag key="DepartureStation"/></th>
        <th scope="col" ><m:locale-tag key="DepartureTime"/></th>
        <th scope="col" ><m:locale-tag key="ArrivalStation"/></th>
        <th scope="col" ><m:locale-tag key="ArrivalTime"/></th>
        <th scope="col" ><m:locale-tag key="Ordered"/></th>

    </tr>
    </thead>
    <tbody>
    <div>
        <c:forEach var="ticket" items="${tickets}">
       <tr onclick="location.replace('/?command=trainInfo&trainId=${ticket.getTrain().getId()}')">
            <td scope="row" >${ticket.getCost()}</td>
           <c:if test='${ticket.getEndStation().getName().equals("null")||ticket.getEndStation().getName().equals("")
           ||ticket.getEndStation()==null}'>
               <td scope="row" >${ticket.getTrain().getStations().get(0).name}</td>
               <td scope="row" >${Utility
                       .dateToString(ticket.getTrain().getAgenda().get(ticket.getTrain().getStations().get(0)))}</td>
               <td scope="row" >${ticket.getTrain().getStations().get(ticket.getTrain().getStations().size()-1).name}</td>
               <td scope="row" >${Utility
                       .dateToString(ticket.getTrain().getAgenda().get(ticket.getTrain().getStations()
                       .get(ticket.getTrain().getStations().size()-1)))}</td>
           </c:if>
            <c:if test='${!ticket.getEndStation().getName().equals("null")&&ticket.getEndStation()!=null&&!ticket.getEndStation().getName().equals("")}'>
            <td scope="row" >${ticket.getStartStation().name}</td>
            <td scope="row" >${Utility.dateToString(ticket.getTrain().getAgenda().get(ticket.getStartStation()))}</td>
            <td scope="row" >${ticket.getEndStation().name}</td>
            <td scope="row" >${Utility
                    .dateToString(ticket.getTrain().getAgenda().get(ticket.getEndStation()))}</td></c:if>
            <td scope="row" >${Utility
           .dateToString(ticket.date)}</td>
        </tr>

        </c:forEach>
    </div>
    </tbody>
</table>



<footer class="py-3 my-4" style=" position: relative;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 80px;">
    <ul class="nav justify-content-center border-bottom pb-3 mb-3">
        <form action="/" method="post">
            <input type="hidden" name="command" value="changeToUA">
            <input type="hidden" name="prev" value="/?command=tickets&page=${currentPage}">
            <li class="nav-item"><button type="submit">Українська мова</button></li></form>
        <form action="/" method="post">
            <input type="hidden" name="command" value="changeToEn">
            <input type="hidden" name="prev" value="/?command=tickets&page=${currentPage}">
            <li class="nav-item"><button type="submit">English language</button></li></form> </ul>
</footer>
</body>
</html>
