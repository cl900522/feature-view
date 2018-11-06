<html>
    <head></head>
    <body>
    <div>
        <div>
            <label>账号<input type="text" name="loginName" id="loginName"/></label> <br>
            <label>密码<input type="text" name="password" id="password"/></label> <br>
            <input type="hidden" name="redirect-url" id="redirect-url" value="${redirectUrc!""}"/>
            <input type="submit" id="goLogin">
        </div>
    </div>
    </body>
    <script type="application/javascript" src="/js/jquery-1.7.1.js"></script>
    <script>
        $(function () {
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