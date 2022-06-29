<%--
  Created by IntelliJ IDEA.
  User: Quant
  Date: 23.05.2022
  Time: 14:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="WEB-INF/LocalTagLib.tld" prefix="m" %>
<html>
<head>
    <title>${title}</title>
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
<div style="min-height: 70%">
    <h2 style="text-align: center; position: center; " >${title}
    </h2>
</div>
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
            <li class="nav-item"><button type="submit">English language</button></li></form>
    </ul>
</footer>
</body>
</body>
</html>
