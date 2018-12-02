<#import "spring.ftl" as spring/>
<#include "./common/common_macro.ftl" parse=true />

<html>
    <head>
        <@htmlHeaders title="page.title.sso" />
    </head>
    <body>
        <@pageHead />

        <div class="form form-horizontal" style="width:300px; margin:200px auto;">
            <fieldset>
                <div class="form-group">
                    <label class="control-label"><@spring.message code="fields.email" /></label>
                    <div class="controls">
                        <input class="form-control" type="text" name="loginName" id="loginName" placeholder="<@spring.message code="hint.input" /><@spring.message code="fields.email" />"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label"><@spring.message code="fields.password" /></label>
                    <div class="controls">
                        <input class="form-control" type="password" name="password" id="password" placeholder="<@spring.message code="hint.input" /><@spring.message code="fields.password" />"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="checkbox">
                        <label class="control-label"><input type="checkbox"/><@spring.message code="hint.remember.me" /></label>
                    </div>
                </div>
                <input type="hidden" name="redirectUrl" id="redirect_url" value="${redirect_url}"/>
                <div class="form-group" style="margin-top: 30px">
                    <div class="controls">
                        <button class="form-control btn btn-success" id="goLogin"><@spring.message code="button.login"/></button>
                    </div>
                </div>
                <div class="form-group" style="margin-top: 10px">
                    <div class="controls">
                        <a class="form-control btn btn-danger" href="./account/register"><@spring.message code="button.to.register" /></a>
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