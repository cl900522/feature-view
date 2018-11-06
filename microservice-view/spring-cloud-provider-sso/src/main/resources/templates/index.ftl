<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div class="header container" style="width:100%;max-width: 100%">
            <div class="row left">
                <div class="col-md-1">单点登录系统</div>
            </div>
        </div>
        <div class="progress" style="width: 100%;max-width: 100%;height: 8px">
            <div id="validateProcess" class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
                <span class="sr-only">40% 完成</span>
            </div>
        </div>

        <div class="container" role="form" style="width:500px; margin:100px auto;">
            <div class="row">
                <div class="input-group input-group-md">
                    <label class="input-group-addon">账号</label><input class="form-control" type="text" name="loginName" id="loginName"/>
                </div>
            </div>
            <div class="row">
                <div class="input-group input-group-md">
                    <label class="input-group-addon">密码</label><input class="form-control" type="password" name="password" id="password"/>
                </div>
            </div>
            <input type="hidden" name="redirect-url" id="redirect-url" value="${redirectUrc!""}"/>
            <div class="row">
                <div class="input-group input-group-m">
                    <button class="btn btn-success btn-md" id="goLogin">登录</button>
                </div>
            </div>
        </div>
    </body>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/common.css">
    <script type="application/javascript" src="/js/jquery-1.7.1.js"></script>
    <script type="application/javascript" src="/js/bootstrap.min.js"></script>
    <script type="application/javascript" src="/js/common.js"></script>
    <script>
        var progressBar = new ProgressBar("validateProcess");
        $(function () {
            progressBar.reset();
            progressBar.go();
            $("#goLogin").click(function () {
                $.ajax({
                    type: "POST",
                    cache: false,
                    url: "/validate",
                    data: {
                        loginName: $("#loginName").val(),
                        password: $("#password").val(),
                        "redirect-url": $("#redirect-url").val()
                    },
                    dataType: "json",
                    success: function (response) {
                        progressBar.setProgress(100);

                        if (response.code == "0") {
                            console.log("success");
                        } else {
                            console.log(response.message);
                        }
                    },
                    error: function (data) {
                    }
                });
            });
        });
    </script>
</html>