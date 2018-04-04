<html>
<head></head>
<body>
    <div class="" style="margin:200px auto auto auto;width:500px">
        <form action="/login?callBack={{.callBack}}" method="post" style="display:block;margin:0px auto">
            <table>
                <thead style="text-align:center">
                    <tr><td colspan="3"><span>SSO登录认证</span></td></tr>
                </thead>
                <tbody>
                    <tr><td><span>账号</span></td><td colspan="2"><input type="text" name="userName"/></td></tr>
                    <tr><td><span>密码</span></td><td colspan="2"><input type="password" name="password"/></td></tr>
                    <tr><td><span>验证码</span></td><td><input type="text" name="validateCode"/></td><td><img src="/vircode.jpg" alt=""></td></tr>
                    <tr><td colspan="3"><input type="submit" value="提交"></input></td></tr>
                    <tr><td colspan="3"><span style="color:red">{{.error}}</span></td></tr>
                <tbody>
            </table>
        </form>
    </div>
</body>
</html>
