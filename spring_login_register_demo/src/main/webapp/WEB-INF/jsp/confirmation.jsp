<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <title>Confirmation Page</title>
    <link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-2"></div>
            <div class="col">
                <h2>Welcome To Confirmation Page</h2>
            </div>
            <div class="col-2"></div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-2"></div>
            <div class="col">
                <h2>UserName ${showUser.userName}</h2>
                <h2>Password is ${showUser.password}</h2>
                <h2>Gender is ${showUser.gender}</h2>
                <h2>Board is ${showUser.board}</h2>
                <h2>Certificates are ${showUser.certificates}</h2>
                <%--<c:forEach items="${user.certificate_ids}" var="certificate">
                    <h4><c:out value="${certificate}" /></h4>
                </c:forEach>--%>
                <a href="/login" class="btn btn-primary btn-block mt-3">Go to Login Page</a><br>
                <a href="/update/${showUser.userName}" class="btn btn-success btn-block mt-3">Click Here to Update Data</a>
                <img src="data:image/jpg;base64,${showUser.base64file}" width="240" height="300"/><br>
            </div>
            <div class="col-2"></div>
        </div>
    </div>

</body>
</html>