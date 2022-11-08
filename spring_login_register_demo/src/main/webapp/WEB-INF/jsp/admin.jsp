<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <title>Admin Welcome Page</title>
    <link href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-4"></div>
            <div class="col">
                <h2>Welcome ${username}</h2>
            </div>
            <div class="col-4"></div>
        </div>
    </div>

    <table class=" table table-hover" border="3" align="center">
        <thead>
            <tr>
                <th align="center">User name</th>
                <th align="center">Password</th>
                <th align="center">Gender Id</th>
                <th align="center">Board Id</th>
                <th align="center">Joinig Date</th>
                <th align="center">Delete User </th>
            </tr>
        </thead>
        <tbody id="userTable"></tbody>
        <%--<c:forEach items="${usersList}" var="user">
            <c:if test="${user.userName != 'admin'}">
                <tr>
                    <td align="center"><c:out value="${user.userName}" /></td>
                    <td align="center"><c:out value="${user.password}" /></td>
                    <td align="center"><c:out value="${user.gender_id}" /></td>
                    <td align="center"><c:out value="${user.board_id}" /></td>
                    <td align="center"><c:out value="${user.joining_date}" /></td>
                    <td align="center"><a href="delete/${user.userName}">Delete ${user.userName}</a> </td>
                </tr>
            </c:if>

        </c:forEach>--%>
    </table>
    <script src="/webjars/jquery/3.6.0/jquery.min.js" type="text/javascript"> </script>
    <script type="text/javascript">
        $(document).ready(function () {
            getAllUsers();
        });
        var data="";
        function getAllUsers() {
            //console.log("inside get all users")
            $.ajax({
                    type : "GET",
                    url : "getAllUsers",
                    success : function(response) {
                        data =response
                        $('.tr').remove()
                        for(i=0;i<data.length;i++)
                        {
                            if(data[i].userName != 'admin')
                            {
                                $('#userTable').append(
                                    '<tr class="tr">'+
                                    '<td>'+data[i].userName+'</td>'+
                                    '<td>'+data[i].password+'</td>'+
                                    '<td>'+data[i].gender_id+'</td>'+
                                    '<td>'+data[i].board_id+'</td>'+
                                    '<td>'+data[i].joining_date+'</td>'+
                                    '<td><button class="btn btn-danger" onclick="deleteUser(\'' + data[i].userName + '\')">Delete</button></td> </tr>'
                                );
                            }

                        }
                    },
                    error : function (error) {
                        alert("Error is " + error)
                    }
                });
        }

        function deleteUser(user) {
            //alert('delete called for:  ' +user );
            $.ajax({
                type : "GET",
                url : "/delete/" + user,
                success : function(response) {
                    getAllUsers();
                },
                error : function(err) {
                    alert("error is" + err)
                }
            });
        }

    </script>

</body>
</html>