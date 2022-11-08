<html>
<head>
    <title>User Welcome Page</title>
    <link href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="row"></div>
        <div class="row">
            <div class="col"></div>
            <div class="col-lg-6">
                <h2>Welcome ${user.userName}</h2>
                <h2>Joining date ${user.joining_date}</h2>
                <img src="data:image/jpg;base64,${user.base64file}" width="240" height="300"/><br>
                <a href="/update/${user.userName}" class="btn btn-primary btn-block mt-3" >Click Here to Update Data</a>
            </div>
            <div class="col"></div>
        </div>
        <div class="row">

        </div>
    </div>

</body>
</html>