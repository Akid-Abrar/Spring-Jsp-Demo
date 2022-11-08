<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <title>Update Form</title>
        <link href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-2"></div>
                <div class="col">
                    <h2>Upate information Of ${usershow.userName} </h2>
                </div>
                <div class="col-2"></div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-2"></div>
                <div class="col">
                    <form:form modelAttribute="user" onsubmit="return false">
                        <div class="form-group mt-3">
                            <label>Password</label>
                            <form:hidden id="userName" name="userName" class="form-control" placeholder="${usershow.userName}" path="userName" />
                        </div>
                        <div class="form-group mt-3">
                            <label>Password</label>
                            <form:input type="password" id="password" name="password" class="form-control" placeholder="${usershow.password}" path="password" />
                        </div>
                        <div class="form-group mt-3">
                            <label>Gender</label><br>
                            <c:forEach items="${genderList}" var="gender">
                                <c:set var="contains" value="false" />
                                <c:if test="${gender_id eq gender.key}">
                                    <c:set var="contains" value="true" />
                                </c:if>
                                <c:choose>
                                    <c:when test="${contains eq true}">
                                        <form:radiobutton id="gender_id" name="gender_id" label="${gender.value}" value = "${gender.key}" path = "gender_id" checked = "checked"/>                                    </c:when>
                                    <c:otherwise>
                                        <form:radiobutton id="gender_id" name="gender_id" label="${gender.value}" value = "${gender.key}" path = "gender_id" />                                    </c:otherwise>
                                </c:choose>

                                <br>
                            </c:forEach>
<%--
                            <form:radiobuttons items="${genderList}" id="gender_id" name="gender_id" label="${gender.value}" value = "${gender.key}" path = "gender_id" />
--%>
                        </div>
                        <div class="form-group mt-3">
                            <label class="mr-sm-2" >Board</label><br>
                            <form:select path = "board_id">
                                <c:forEach items="${boardList}" var="board">
                                    <form:option id="board_id" name="board_id" label="${board.value}" value = "${board.key}"  />
                                </c:forEach>
                            </form:select>
                        </div>

                        <div class="form-group mt-3">
                            <label class="mr-sm-2" >Certificates</label><br>
                            <c:forEach items="${certificateList}" var="certificate">
                                <c:set var="contains" value="false" />
                                <c:forEach var="selected_certificate" items="${selected_certificate_ids}">
                                    <c:if test="${selected_certificate eq certificate.key}">
                                        <c:set var="contains" value="true" />
                                    </c:if>
                                </c:forEach>
                                <c:choose>
                                    <c:when test="${contains eq true}">
                                        <form:checkbox id="certificate_id" class="certificateCheckbox" name="certificate_id" label="${certificate.value}" value = "${certificate.key}" path = "certificate_ids" checked = "checked"/>
                                    </c:when>
                                    <c:otherwise>
                                        <form:checkbox id="certificate_id" class="certificateCheckbox" name="certificate_id" label="${certificate.value}" value = "${certificate.key}" path = "certificate_ids" />
                                    </c:otherwise>
                                </c:choose>

                                <br>
                            </c:forEach>
                                <%--<form:checkboxes items = "${certificateList}" path = "certificate-ids" /> --%>
                        </div>

                        <div class="form-group mt-3">
                            <label>Joining date</label>
                            <form:input id="joining_date" name="joining_date"  type="datepicker"  placeholder="${usershow.joining_date}" path="joining_date" />
                        </div>

                        <div class="form-group mt-3"></div>
<%--                        <form:button id="updateStudent" onclick="updateUser(${usershow.userName})"  class="btn btn-primary mt-3" >Update</form:button>--%>
                        <button id="updateStudent" onclick="updateUser()"  class="btn btn-primary mt-3" >Update</button>
                        <a href="/user/${usershow.userName}" class="btn btn-primary btn-block mt-3">Go to HomePage</a><br>
                    </form:form>
                </div>
                <div class="col-2"></div>
            </div>
        </div>
        <script src="/webjars/jquery/3.6.0/jquery.min.js" type="text/javascript"> </script>
        <script type="text/javascript">

            $(document).ready(function() {
            });

            function updateUser(username) {
                var arrayValue = [];
                // use name or class name
                $("#certificate_id:checked").map(function(){
                    if($(this).prop("checked")){
                        arrayValue.push($(this).val())
                    }
                });

                var gender_val = $("#gender_id:checked").val()
                console.log("gender_val is "+ gender_val)
                console.log("checked values")
                console.log(arrayValue);
                $.ajax({
                    type: "POST",
                    url: "/update/"+username,
                    data : {
                        userName : $("#userName").val(),
                        password : $("#password").val(),
                        //gender_id : $("#gender_id").val(),
                        gender_id : gender_val,
                        board_id : $("#board_id").val(),
                        joining_date : $("#joining_date").val(),
                        //certificate_ids : $("#certificate_ids").val()
                        certificate_ids : arrayValue
                    },
                    dataType: 'json',
                    cache: false,
                    //timeout: 600000,
                    async: false,
                    success : function(response) {
                        console.log(response)
                        console.log("success called ")
                    },
                    error : function(err) {
                        console.log("error occured");
                        console.log(arguments);
                        console.log(err)
                        //alert("errr" + err)
                    }
                });
            }
        </script>
    </body>
</html>
