<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Register Form</title>
    <link href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

    <div class="container">
        <div class="row">
            <div class="col-2"></div>
            <div class="col">
                <h2>Welcome To Register Page</h2>
            </div>
            <div class="col-2"></div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-2"></div>
            <div class="col">
                <div>
                    <c if test="${not empty successMsg}">
                        <div >
                            ${successMsg}
                        </div>
                    </c>
                </div>
                <form:form method="post" action="register" modelAttribute="user" enctype="multipart/form-data">
                    <div class="form-group mt-3">
                        <label>User Name</label>
                        <form:input type="text" class="form-control" path="userName" placeholder="Enter User Name" />
                    </div>
                    <div class="form-group mt-3">
                        <label>Password</label>
                        <form:input type="password" class="form-control" placeholder="Password" path="password" />
                    </div>
                    <div class="form-group mt-3">
                        <label>Gender</label><br>
                        <c:forEach items="${genderList}" var="gender">
                            <form:radiobutton label="${gender.value}" value = "${gender.key}" path = "gender_id" />
                            <br>
                        </c:forEach>
                    </div>
                    <div class="form-group mt-3">
                        <label class="mr-sm-2" >Board</label><br>
                        <form:select path = "board_id">
                            <c:forEach items="${boardList}" var="board">
                                <form:option label="${board.value}" value = "${board.key}"  />
                            </c:forEach>
                        </form:select>
                    </div>

                    <div class="form-group mt-3">
                        <label class="mr-sm-2" >Certificates</label><br>
                        <c:forEach items="${certificateList}" var="certificate">
                            <form:checkbox label="${certificate.value}" value = "${certificate.key}" path = "certificate_ids" />
                            <br>
                        </c:forEach>
                        <%--<form:checkboxes items = "${certificateList}" path = "certificate-ids" /> --%>
                    </div>

                    <div class="form-group mt-3">
                        <label>Joining date</label>
                        <form:input type="datepicker" id="datepicker" path="joining_date" placeholder="yyyy-mm-dd"/>
                    </div>

                    <div class="form-group mt-3">
                        <label>Profile Picture</label> <br>
                        <form:input path="file"  type="file"/>
                    </div>
                    <form:button type="submit" class="btn btn-primary mt-3" >Register</form:button>

                </form:form>
            </div>
            <div class="col-2"></div>
        </div>
    </div>

</body>
</html>