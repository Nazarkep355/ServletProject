<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.example.finalproject3.Entity.User" %>
<%@ page import="com.example.finalproject3.Entity.Train" %>
<%@ taglib uri="WEB-INF/LocalTagLib.tld" prefix="m" %>
<%--
  Created by IntelliJ IDEA.
  User: Quant
  Date: 23.05.2022
  Time: 13:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><m:locale-tag key="CreateRoute"/></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">

    <header class="d-flex justify-content-center py-3">
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="/" class="nav-link active" aria-current="page"><m:locale-tag key="Home"/></a></li>
            <li class="nav-item"><a href="/?command=stations&page=1" class="nav-link"><m:locale-tag key="Stations"/></a></li>
            <c:if test="${user!=null}">
                <li class="nav-item"><a href="/?command=tickets&page=1"  class="nav-link"><m:locale-tag key="Tickets"/></a></li>
                <li class="nav-item"><form action="/"  method="post">
                    <input type="hidden" name="command" value="signOut">
                    <button type="submit" class="nav-link"><m:locale-tag key="SignOut"/></button></form></li>
            </c:if>
        </ul>
        <c:if test="${user!=null}">
            <span class="fs-4" style="position: absolute;right: 15px">${user.getName()}</span>
            <p><small style="margin-top:40px;position: absolute;right: 15px">${user.getMoney()} UAH</small></p>
        </c:if>
    </header>
<div style="min-height: 70%">
    <div class="col-md-10 mx-auto col-lg-5">
        <form action="/" method="post"  class="p-4 p-md-5 border rounded-3 bg-light">
            <input name="command" value="createRoute" type="hidden">
           <c:if test='${error!=null&&error.equals("station not found")}'>
               <small style="color: red" class="text-muted"><m:locale-tag key="Stationisntfound"/> <m:locale-tag key="InDataBase"/></small></c:if>
            <div class="form-floating mb-3">
                <input  required="required" type="number" name="cost" class="form-control"
                         id="name" pattern="[\\d]+">
                <label for="number input"><m:locale-tag key="Cost"/></label>
            </div>

            <div class="form-floating mb-3">
                <input onchange="resetField()"  type="number" name="stationNumber" class="form-control"
                       required="required" id="number input">
                <label for="number input"><m:locale-tag key="NumberOfStations"/></label>
            </div>
            <div id ="form">

            </div>
            <script>
                function resetField(){ const form =document.getElementById('form');
                    form.innerText=''
                    const numberInput = document.getElementById('number input');
                    let numberOfStations = numberInput.value;
                    if(numberOfStations<1)
                        numberOfStations=1;
                    for(let i =1;i<=numberOfStations;i++){
                        const divStation =document.createElement('div')
                        divStation.setAttribute('class','form-floating mb-3');
                        const inputStation = document.createElement('input');
                        inputStation.setAttribute('name','station'+i);
                        inputStation.setAttribute('class','form-control');
                        inputStation.setAttribute('required','required')
                        inputStation.setAttribute('id','inputStation'+i);
                        const labelStation = document.createElement('label');
                        labelStation.setAttribute('for','inputStation'+i);
                        labelStation.innerText='<m:locale-tag key="EnterStationName"/> '+i;
                        divStation.appendChild(inputStation);
                        divStation.appendChild(labelStation);
                        form.appendChild(divStation);
                        if(i<numberOfStations){const divDelay= document.createElement('div');
                        divDelay.setAttribute('class','form-floating mb-3');
                        const inputDelay = document.createElement('input');
                        inputDelay.setAttribute('name','delay'+i);
                        inputDelay.setAttribute('class','form-control');
                        inputDelay.setAttribute('required','required')
                        inputDelay.setAttribute('type','number');
                        inputDelay.setAttribute('id','inputDelay'+i);
                        const labelDelay = document.createElement('label');
                        labelDelay.setAttribute('for','inputDelay'+i);
                        labelDelay.innerText='<m:locale-tag key="EnterDelay"/> '+i;
                        divDelay.appendChild(inputDelay);
                        divDelay.appendChild(labelDelay);
                        form.appendChild(divDelay);}
                    }
                }
            </script>
            <button class="w-100 btn btn-lg btn-primary"  type="submit"><m:locale-tag key="PlanRoute"/></button>
            <small class="text-muted"><m:locale-tag key="IfStationNoExistRouteNotCreate"/></small>
        </form>
    </div>
</div>

    <footer class="py-3 my-4" style=" position: relative;
        left: 0;
        bottom: 0;
        width: 100%;
        height: 80px;">
        <ul class="nav justify-content-center border-bottom pb-3 mb-3">
            <form action="/" method="post">
                <input type="hidden" name="command" value="changeToUA">
                <input type="hidden" name="prev" value="/?command=createRoutePage">
                <li class="nav-item"><button type="submit">Українська мова</button></li></form>
            <form action="/" method="post">
                <input type="hidden" name="command" value="changeToEn">
                <input type="hidden" name="prev" value="/?command=createRoutePage">
                <li class="nav-item"><button type="submit">English language</button></li></form>
        </ul>
    </footer>
</div>










</body>
</html>
