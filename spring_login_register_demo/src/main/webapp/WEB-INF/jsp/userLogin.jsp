<html>
<head>
    <title>Login Form</title>
    <link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

    <div class="container">
        <div class="row">
            <div class="col-2"></div>
            <div class="col">
                <h2>Login Page</h2>
            </div>
            <div class="col-2"></div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-2"></div>
            <div class="col">
                <div>
                    <c: if test="${not empty errorMsg}">
                        <div >
                            ${errorMsg}
                        </div>
                    </c:>
                </div>
                <form method="post" action="userLogin">
                    <div class="form-group mt-3">
                        <label for="exampleInputName">User Name</label>
                        <input type="text" class="form-control" id="exampleInputName" name="username" placeholder="Enter User Name">
                    </div>
                    <div class="form-group mt-3">
                        <label for="exampleInputPassword1">Password</label>
                        <input type="password" class="form-control" name="password" id="exampleInputPassword1" placeholder="Password">
                    </div>
                    <button type="submit" class="btn btn-primary mt-3">Login</button>
                </form>
            </div>
            <div class="col-2"></div>
        </div>
    </div>

</body>
</html>