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
                <h2>Welcome</h2>
            </div>
            <div class="col-2"></div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-2"></div>
            <div class="col">
                <div class="alert alert-success d-none" role="alert" id="success_alert">
                    Successfully Added User !!!
                </div>
                <div class="alert alert-danger d-none" role="alert" id="failure_alert">
                    Couldn't Add User !!!
                </div>
                <form method="post" id="userForm" name="userForm" >

                    <div class="form-group mt-3 d-none">
                        <label>ID</label>
                        <input type="text" class="form-control" id="id" name="id" placeholder="Your Name" />
                    </div>

                    <div class="form-group mt-3">
                        <label>User Name</label>
                        <input type="text" class="form-control" id="userName" name="userName" path="userName" placeholder="Your Name" />
                    </div>
                    <div class="form-group mt-3">
                        <label>Password</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="Password" />
                    </div>
                    <div class="form-group mt-3">
                        <label>Father's Name</label>
                        <input type="text" class="form-control" id="fatherName" name="fatherName" placeholder="Father's Name" />
                    </div>
                    <div class="form-group mt-3">
                        <label>Mother's Name</label>
                        <input type="text" class="form-control" id="motherName" name="motherName"  placeholder="Mother's Name" />
                    </div>
                    <div class="form-group mt-3">
                        <label>Current Address</label>
                        <input type="text" class="form-control" id="address" name="address" placeholder="Current Address" />
                    </div>
                    <div class="form-group mt-3">
                        <label>Date of Birth</label>
                        <input type="date" id="dateOfBirth" name="dateOfBirth" placeholder="yyyy-mm-dd"/>
                    </div>
                    <div class="form-group mt-3">
                        <label>Nid Number</label>
                        <input type="text" class="form-control" id="nid" name="nid" placeholder="Enter 10 Digit NID Number" />
                    </div>
                    <div class="form-group mt-3">
                        <label>Email Address</label>
                        <input type="text" class="form-control" id="email" name="email" placeholder="Enter Your Email Address" />
                    </div>
                    <div class="form-group mt-3">
                        <label>Contact Number</label>
                        <input type="text" class="form-control" id="contactNo" name="contactNo" placeholder="Enter Your Active Contact Number" />
                    </div>
                    <button id="saveUser" type="button" class="btn btn-outline-success mt-3" >Add</button>
                    <button id="updateUser" type="button"  onclick="updateUserbtn()" class="btn btn-outline-success mt-3">Update</button>

                </form>
            </div>
            <div class="col-2"></div>
        </div>
        <div>
            <h3>User Record</h3>

            <br>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">UserName</th>
                    <th scope="col">Father Name</th>
                    <th scope="col">Mother Name</th>
                    <th scope="col">Address</th>
                    <th scope="col">Date of Birth</th>
                    <th scope="col">NID Number</th>
                    <th scope="col">Email Address</th>
                    <th scope="col">Contact Number</th>
                    <th scope="col">Edit</th>
                    <th scope="col">Delete</th>
                </tr>
                </thead>
                <tbody id="userTable">

                </tbody>
            </table>
        </div>
    </div>

    <script src="/webjars/jquery/3.6.0/jquery.min.js" type="text/javascript"> </script>
    <script type="text/javascript">
        $(document).ready(function() {
            $(".success-alert").hide();
            $('#updateUser').hide();
            getAllUsers();

            $('#saveUser').click(function() {
                $.ajax({
                    type : "POST",
                    url : "/insertRegisteredUser",
                    data : {
                        userName : $("#userName").val(),
                        password : $("#password").val(),
                        fatherName : $("#fatherName").val(),
                        motherName : $("#motherName").val(),
                        address : $("#address").val(),
                        dateOfBirth : $("#dateOfBirth").val(),
                        nid : $("#nid").val(),
                        email : $("#email").val(),
                        contactNo : $("#contactNo").val(),
                    },
                    success : function(response) {
                        $("#success-alert").fadeTo(2000, 500).slideUp(500, function() {
                            $("#success-alert").slideUp(500);
                        });
                        $('#success_alert').removeClass("d-none");
                        //$('#success_alert').delay(1000).fadeOut('slow');
                        getAllUsers();
                        $('#userForm')[0].reset() ///change
                        //$('#success_alert').close();

                    },
                    error : function(err) {
                        $('#failure_alert').removeClass("d-none");
                        getAllUsers();
                        $('#userForm')[0].reset()
                    }
                });
            });
        });

        var data="";
        function getAllUsers() {
            //console.log("inside get all users")
            $.ajax({
                type : "GET",
                url : "/getAllRegisteredUser",
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
                                '<td>'+data[i].fatherName+'</td>'+
                                '<td>'+data[i].motherName+'</td>'+
                                '<td>'+data[i].address+'</td>'+
                                '<td>'+data[i].dateOfBirth+'</td>'+
                                '<td>'+data[i].nid+'</td>'+
                                '<td>'+data[i].email+'</td>'+
                                '<td>'+data[i].contactNo+'</td>'+
                                '<td><button class="btn btn-warning" onclick="editUser(' + data[i].id + ')">Edit</button></td> '+
                                '<td><button class="btn btn-danger" onclick="deleteUser(' + data[i].id + ');">Delete</button></td> '+
                                '</tr>'
                            );
                        }

                    }
                },
                error : function (error) {
                    alert("Error is " + error)
                }
            });
        }

        function editUser(id) {
            //alert("id got "+ id)
            $.ajax({
                type : "GET",
                url : "getOneRegisteredUser/" + id,
                dataType : 'json',
                    success : function(response) {
                            $("#id").val(id),
                            $("#userName").val(response.userName) ,
                            $("#password").val(response.password),
                            $("#fatherName").val(response.fatherName),
                            $("#motherName").val(response.motherName),
                            $("#address").val(response.address) ,
                            $("#dateOfBirth").val(response.dateOfBirth),
                            $("#nid").val(response.nid),
                            $("#email").val(response.email),
                            $("#contactNo").val(response.contactNo)

                    $('#saveUser').hide();
                    $('#updateUser').show();
                    //$('#idfield').show();
                },
                error : function(err) {
                    alert("error is" + err)
                }
            });
        }

        function updateUserbtn() {
            $.ajax({
                type : "POST",
                url : "updateRegisteredUser",
                data : {
                    id : $("#id").val(),
                    userName : $("#userName").val(),
                    password : $("#password").val(),
                    fatherName : $("#fatherName").val(),
                    motherName : $("#motherName").val(),
                    address : $("#address").val(),
                    dateOfBirth : $("#dateOfBirth").val(),
                    nid : $("#nid").val(),
                    email : $("#email").val(),
                    contactNo : $("#contactNo").val(),
                },
                success : function(result) {
                    getAllUsers();
                    $('#saveUser').show();
                    $('#updateUser').hide();
                    // $('#idfield').hide();
                    $('#userForm')[0].reset()
                },
                error : function(err) {
                    alert("error is" + err)
                }
            });
        }

        function deleteUser(id) {
            $.ajax({
                url : "deleteRegisteredUser/" + id,
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