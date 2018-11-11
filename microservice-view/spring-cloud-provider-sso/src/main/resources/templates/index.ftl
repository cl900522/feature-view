<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div class="header container" style="height: 60px;width: 100%;max-width: 100%">
            <div class="row">
                <div class="col-lg-2" style="line-height: 40px;font-size: 16px">
                    <div class="pull-left" style="display: block">
                        <img src="https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=117732031,468073355&fm=58&bpow=1024&bpoh=1024" class="img-rounded" style="height: 40px;width: 40px;">
                        <span style="height: 40px;width: 40px;">统一登录系统</span>
                    </div>
                </div>
                <div class="col-lg-1" style="line-height: 40px;font-size: 16px">

                </div>
            </div>
        </div>
        <div class="progress" style="width: 100%;max-width: 100%;height: 10px">
            <div id="validateProcess" class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
                <span class="sr-only">x% 完成</span>
            </div>
        </div>

        <div class="form form-horizontal" style="width:300px; margin:200px auto;">
            <fieldset>
                <div class="form-group">
                    <label class="control-label">账号/邮箱/手机</label>
                    <div class="controls">
                        <input class="form-control" type="text" name="loginName" id="loginName" placeholder="输入账号/邮箱/手机"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label">密码</label>
                    <div class="controls">
                        <input class="form-control" type="password" name="password" id="password" placeholder="输入密码"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="checkbox">
                        <label class="control-label"><input type="checkbox"/>记住我</label>
                    </div>
                </div>
                <input type="hidden" name="redirectUrl" id="redirect_url" value="${redirect_url}"/>
                <div class="form-group" style="margin-top: 30px">
                    <div class="controls">
                        <button class="form-control btn btn-success" id="goLogin">登录</button>
                    </div>
                </div>
            </fieldset>
        </div>
    </body>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/common.css">
    <script type="application/javascript" src="/js/jquery.min.js"></script>
    <script type="application/javascript" src="/js/bootstrap.min.js"></script>
    <script type="application/javascript" src="https://passport.cnblogs.com/scripts/jsencrypt.min.js"></script>
    <script type="application/javascript" src="/js/common.js"></script>
    <script>
        var progressBar = new ProgressBar("validateProcess");
        var pwdSalt = "${pwd_salt}";
        var rsaPublicKey = "${publicKey}";

        var encrypt = new JSEncrypt();
        encrypt.setPublicKey(rsaPublicKey );

        $(function () {
            $("#goLogin").click(function () {
                progressBar.reset();
                progressBar.go();
                var encryptData = encrypt.encrypt($("#password").val() + pwdSalt);//加密后的字符串
                $.ajax({
                    type: "POST",
                    cache: false,
                    url: "/validate",
                    data: {
                        "loginName": $("#loginName").val(),
                        "password": encryptData,
                        "passwordSalt": pwdSalt,
                        "redirectUrl": $("#redirect_url").val()
                    },
                    dataType: "json",
                    success: function (response) {
                        progressBar.setProgress(100);
                        if (response.code == "0") {
                            location.href = response.data;
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