<#import "spring.ftl" as spring/>
<#include "./common/common_macro.ftl" parse=true />
<html>
    <head>
         <@htmlHeaders title="page.title.register" />
    </head>
    <body>
        <@pageHead />

        <div class="form form-horizontal" style="width:300px; margin:200px auto;">
            <fieldset>
                <div class="form-group">
                    <label class="control-label"><@spring.message code="fields.email" /></label>
                    <div class="controls">
                        <input class="form-control" type="text" name="email" id="email" placeholder="<@spring.message code="hint.input" /><@spring.message code="fields.email" />"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label"><@spring.message code="fields.password" /></label>
                    <div class="controls">
                        <input class="form-control" type="password" name="password" id="password" placeholder="<@spring.message code="hint.input" /><@spring.message code="fields.password" />"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label"><@spring.message code="fields.duplicate.password" /></label>
                    <div class="controls">
                        <input class="form-control" type="password" name="dupPassword" id="dupPassword" placeholder="<@spring.message code="hint.input" /><@spring.message code="fields.duplicate.password" />"/>
                    </div>
                </div>

                <div class="form-group" style="margin-top: 30px">
                    <div class="controls">
                        <button class="form-control btn btn-success" id="goRegister"><@spring.message code="button.register" /></button>
                    </div>
                </div>
            </fieldset>
        </div>

        <div style="position: absolute;right: 0px;top: 80px" id="messageBox">
        </div>
    </body>
    <#include "./common/common_static_resource.ftl" parse=false>
    <script>
        $(function () {
            var progressBar = new ProgressBar("validateProcess");
            var messageBox = new MessageBox("messageBox");
            var rsaPublicKey = "${rsa_public_key}";

            var encrypt = new JSEncrypt();
            encrypt.setPublicKey(rsaPublicKey );

            $("#goRegister").click(function () {
                var pwd = $("#password").val();
                var dupPwd = $("#dupPassword").val();
                if (pwd !== dupPwd) {
                    messageBox.error("两次输入的密码不一致，请重新入确认！");
                    return;
                }
                progressBar.reset();
                progressBar.go();
                var encryptPwd = encrypt.encrypt(pwd);//加密后的字符串
                $.ajax({
                    type: "post",
                    cache: false,
                    url: "/account/register",
                    data: {
                        "email": $("#email").val(),
                        "password": encryptPwd
                    },
                    dataType: "json",
                    success: function (response) {
                        progressBar.setProgress(100);
                        if (response.code == "0") {
                            messageBox.success("注册成功，请稍后查看您的邮箱并激活账号！");
                            let sec = 5;
                            messageBox.warn(sec + "秒后自动跳转登录页...");
                            setInterval(function () {
                                sec--;
                                if(sec == 0) {
                                    location.href = "/";
                                } else {
                                    messageBox.warn(sec + "秒后自动跳转登录页...");
                                }
                            }, 1000);
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
        })
    </script>
</html>