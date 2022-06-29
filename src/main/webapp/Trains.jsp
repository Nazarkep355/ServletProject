<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.example.finalproject3.Entity.User" %>
<%@ page import="com.example.finalproject3.Entity.Train" %>
<%@ page import="com.example.finalproject3.Utility.Utility" %>
<%@ page import="com.example.finalproject3.Utility.TrainUtility" %>
<%@ taglib uri="WEB-INF/LocalTagLib.tld" prefix="m" %>
<%--
  Created by IntelliJ IDEA.
  User: Quant
  Date: 20.05.2022
  Time: 3:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><m:locale-tag key="FoundTrains"/></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">

</head>
<body>
<div class="container">

    <header class="d-flex justify-content-center py-3">
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="/" class="nav-link active" aria-current="page"><m:locale-tag key="Home"/></a></li>
            <li class="nav-item"><a href="/?command=stations&page=1" class="nav-link"><m:locale-tag key="Stations"/></a></li>
           <c:if test="${user!=null}"> <li class="nav-item"><a href="/?command=tickets&page=1" class="nav-link"><m:locale-tag key="Tickets"/></a></li>
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
<div style="min-height: 350px">
    <c:if test="${stationNotFound==true}">
        <small class="text-muted" ><m:locale-tag key="Stationisntfound"/></small>
    </c:if>

    <form action="/" method="get" class="row gx-3 gy-2 align-items-center mx-auto">
        <input type="hidden" name="page" value="1">
        <input type="hidden" name="command" value="findTrainByTwoStations">
        <div class="col-sm-3">
            <label class="visually-hidden" for="From">Name</label>
            <input type="text" name="station1" class="form-control" required="required" id="From"
                   placeholder="<m:locale-tag key="DepartureStation"/>">
        </div>
        <div class="col-sm-3">
            <label class="visually-hidden" for="To">Name</label>
            <input type="text" name="station2" required="required" class="form-control" id="To"
                   placeholder="<m:locale-tag key="ArrivalStation"/>" >
        </div>
        <div class="col-sm-3">
            <input type="date" class="form-control" name="date">
        </div>
        <div class="col-sm-3">
            <button type="submit" class="btn btn-primary" ><m:locale-tag key="FindTrain"/></button>
        </div>
    </form>
    <c:if test="${trains.size()<1}" >
    <div style="min-height: 70%">
        <h2 style="text-align: center; position: center; "><m:locale-tag key="NoDirectTrains"/>
        </h2></div></c:if>
      <c:if test="${currentPage!=null&&filteredByOne==null&&filteredByTwo==null}">
        <nav style="margin-left: 50%" aria-label="Page navigation example mx-auto">
            <ul class="pagination">
               <c:if test="${currentPage>1}">
                   <li class="page-item"><a class="page-link"
                                            href="/?command=trains&page=${currentPage-1}">${currentPage-1}</a></li>
               </c:if><li class="page-item"><a class="page-link" href="/?command=trains&page=${currentPage}">${currentPage}</a></li>
                <li class="page-item"><a class="page-link" href="/?command=trains&page=${currentPage+1}">${currentPage+1}</a></li>
            </ul>
        </nav></c:if>
        <c:if test="${filteredByOne}">
        <ul style="margin-left: 50%" class="pagination">
            <c:if test="${currentPage>1}">
                <li class="page-item"><a class="page-link"
                                         href="/?command=findTrainByStation&page=${currentPage-1}&station=${station1.getName()}">${currentPage-1}</a></li>
            </c:if><li class="page-item"><a class="page-link" href="/?command=findTrainByStation&page=${currentPage}&station=${station1.getName()}">${currentPage}</a></li>
            <li class="page-item"><a class="page-link" href="/?command=findTrainByStation&page=${currentPage+1}&station=${station1.getName()}">${currentPage+1}</a></li>
        </ul>
        </nav>
        </c:if>
        <c:if test="${filteredByTwo}">
        <ul  style="margin-left: 50%" class="pagination">
            <c:if test="${currentPage>1}">
                <li class="page-item"><a class="page-link"
                                         href="/?command=findTrainByTwoStations&page=${currentPage-1}&station1=${station1.getName()}&station2=${station2.getName()}">${currentPage-1}</a></li>
            </c:if><li class="page-item"><a class="page-link" href="/?command=findTrainByTwoStations&page=${currentPage}&station1=${station1.getName()}&station2=${station2.getName()}">${currentPage}</a></li>
            <li class="page-item"><a class="page-link" href="/?command=findTrainByTwoStations&page=${currentPage+1}&station1=${station1.getName()}&station2=${station2.getName()}">${currentPage+1}</a></li>
        </ul>
        </nav>
        </c:if>
    <c:if test="${noTrains==null||noTrains==false}"><div>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th scope="col" ><m:locale-tag key="Route"/></th>
                  <c:if test="${filteredByOne==null&&filteredByTwo==null}">
            <th scope="col" >${station1}  <m:locale-tag key="DepartureTime" toLowerCase="true"/></th>
            <th scope="col" >${station2} <m:locale-tag key="ArrivalTime" toLowerCase="true"/></th>
                     </c:if>
                <c:if test="${filteredByOne==true}">
                <th scope="col" >${station1.name}  <m:locale-tag key="DepartureTime" toLowerCase="true"/></th>
                <th scope="col" >${station2} <m:locale-tag key="ArrivalTime" toLowerCase="true"/></th>
                </c:if>
                <c:if test="${filteredByTwo==true}">
                    <th scope="col" >${station1.name}  <m:locale-tag key="DepartureTime" toLowerCase="true"/></th>
                    <th scope="col" >${station2.name} <m:locale-tag key="ArrivalTime" toLowerCase="true"/></th>
                </c:if>
                <th scope="col" ><m:locale-tag key="Cost"/></th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${filteredByTwo==null&&filteredByOne==null}">
                <c:forEach var="train" items="${trains}">
            <div>
                <tr onclick="location.replace('/?command=trainInfo&trainId=${train.getId()}')" >
                    <td scope="row">${TrainUtility.fromTo(train)}</td>
                    <td scope="row">${Utility.dateToString(train.getAgenda().get(train.getStations().get(0)))}</td>
                    <td scope="row">${Utility.dateToString(train.getAgenda().get(train.getStations()
                    .get(train.getStations().size()-1)))}</td>
                    <td scope="row">${train.getCost()}</td>
                </tr>
            </div>
    </c:forEach>
            </c:if>
        <c:if test="${filteredByTwo==true||filteredByOne==true}">
            <c:forEach var="train" items="${trains}">
            <div>
                <c:if test="${filteredByTwo}">
                    <tr onclick="location.replace('/?command=trainInfo&trainId=${train.getId()}&from=${train.getStations().indexOf(station1)}&to=${train.getStations().indexOf(station2)}')">
                    </c:if>
                        <c:if test="${filteredByOne}">
                    <tr onclick="location.replace('/?command=trainInfo&trainId=${train.getId()}')"></c:if>
                    <td scope="row" >${TrainUtility.fromTo(train)}</td>

                    <td scope="row" >${Utility.dateToString(train.getAgenda().get(station1))}</td>
                <c:if test='${noLastStation==null}'>
                    <td scope="row">  ${ Utlity.dateToString(train.getAgenda()
                .get(station2))}</td>
                </c:if>
                    <c:if test='${noLastStation==true}'>
                        <td scope="row" >${Utility.dateToString(train.getAgenda().get(train.getStations().get(train.getStations().size()-1)))}</td>
                    </c:if>
                    <td scope="row" >${train.getCost()}</td>
                </tr>
            </div>

            </c:forEach>
        </c:if>

    </tbody>
    </table>
    </div></c:if>
        <footer class="py-3 my-4" style=" position: absolute;
        left: 0;
        bottom: 0;
        width: 100%;
        height: 80px;">
        <ul class="nav justify-content-center border-bottom pb-3 mb-3">
            <form action="/" method="post">
                <input type="hidden" name="command" value="changeToUA">
                <input type="hidden" name="prev" value="/?command=trains&page=${currentPage}">
                <li class="nav-item"><button type="submit">Українська мова</button></li></form>
            <form action="/" method="post">
                <input type="hidden" name="command" value="changeToEn">
                <input type="hidden" name="prev" value="/?command=trains&page=${currentPage}">
                <li class="nav-item"><button type="submit">English language</button></li></form></ul>
        </footer>
</body>
</html>
