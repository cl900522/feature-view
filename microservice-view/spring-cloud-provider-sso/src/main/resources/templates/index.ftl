<#include "./common/common_macro.ftl" parse=true />
<html>
    <head>
        <@htmlHeaders title="统一登录首页" />
    </head>
    <body>
        <@pageHead />

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
                <div class="form-group" style="margin-top: 10px">
                    <div class="controls">
                        <a class="form-control btn btn-danger" href="./account/register">新用户注册</a>
                    </div>
                </div>
            </fieldset>
        </div>

        <div style="position: fixed;right: 0px;top: 80px" id="messageBox">
        </div>
    </body>
    <#include "./common/common_static_resource.ftl" parse=true />
    <script>
        var progressBar = new ProgressBar("validateProcess");
        var messageBox = new MessageBox("messageBox");
        var pwdSalt = "${pwd_salt}";
        var rsaPublicKey = "${rsa_public_key}";

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
                            messageBox.error(response.message);
                        }
                    },
                    error: function (data) {
                        progressBar.setProgress(0);
                        messageBox.error("系统错误");
                    }
                });
            });
        });
    </script>
</html>