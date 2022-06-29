<%--
  Created by IntelliJ IDEA.
  User: Quant
  Date: 19.05.2022
  Time: 19:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="WEB-INF/LocalTagLib.tld" prefix="m" %>

<!DOCTYPE html>
<html>
<head>
    <title><m:locale-tag key="Stations"/></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">

    <header class="d-flex justify-content-center py-3">
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="/" class="nav-link active" aria-current="page"><m:locale-tag key="Home"/></a></li>
            <li class="nav-item"><a href="/?command=stations&page=1" class="nav-link"><m:locale-tag key="Stations"/></a></li>
            <c:if test="${isLogged == true}">
                <li class="nav-item"><a href="/?command=tickets&page=1"  class="nav-link"><m:locale-tag key="Tickets"/></a></li>
                <li class="nav-item"><form action="/"  method="post">
                    <input type="hidden" name="command" value="signOut">
                    <button type="submit" class="nav-link"><m:locale-tag key="SignOut"/></button></form></li>
            </c:if>
        </ul>
        <c:if test="${isLogged==true}">
            <span class="fs-4" style="position: absolute;right: 15px">${user.getName()}</span>
            <p><small style="margin-top:40px;position: absolute;right: 15px">${user.getMoney()} UAH</small></p>
        </c:if>
    </header>
</div>
<hr>
<c:if  test='${PleaseChooseStationAgain==true}'>
    <h2 style="text-align: center; position:center;">PleaseChooseStationAgain</h2>
</c:if>
<ul style="margin-left: 47%" class="pagination">
    <c:if test="${currentPage>2}">
        <li class="page-item"><a class="page-link"
                                 href="/?command=stations&page=1"><m:locale-tag key="GoToFirstPage"/></a></li>
    </c:if>
    <c:if test="${currentPage>1}">
        <li class="page-item"><a class="page-link"
                                 href="/?command=stations&page=${currentPage-1}">${currentPage-1}</a></li>
    </c:if><li class="page-item"><a class="page-link" href="/?command=stations&page=${currentPage}">${currentPage}</a></li>
    <li class="page-item"><a class="page-link" href="/?command=stations&page=${currentPage+1}">${currentPage+1}</a></li>
</ul>
</nav>
<div style="min-: 70%">
    <c:forEach var="station" items="${stations}">
        <p><a class="nav-link active" style="font-size: 20px" href="/?command=findTrainByStation&page=1&station=${station.getName()}">${station.getName()}</a></p>
    </c:forEach>
</div>

<footer class="py-3 my-4" style="    position: relative;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 80px;">
    <ul class="nav justify-content-center border-bottom pb-3 mb-3">
        <form action="/" method="post">
            <input type="hidden" name="command" value="changeToUA">
            <input type="hidden" name="prev" value="/?command=stations&page=${currentPage}">
            <li class="nav-item"><button type="submit">Українська мова</button></li></form>
        <form action="/" method="post">
            <input type="hidden" name="command" value="changeToEn">
            <input type="hidden" name="prev" value="/?command=stations&page=${currentPage}">
            <li class="nav-item"><button type="submit">English language</button></li></form>
    </ul>
</footer>
</body>
</html>