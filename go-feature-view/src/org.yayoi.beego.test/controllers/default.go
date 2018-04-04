package controllers

import (
	"fmt"

	"github.com/astaxie/beego"
	userdao "org.yayoi.beego.test/model"
)

type MainController struct {
	beego.Controller
}

const (
	LOGIN_USER        = "LONG_USER"
	LOGIN_USER_COOKIE = "token"
)

func (c *MainController) Get() {
	loginUser := c.GetSession(LOGIN_USER)
	callBack := c.GetString("callBack")

	if loginUser == nil {
		beego.Debug("login user is nil")
		cookieId := c.Ctx.GetCookie(LOGIN_USER_COOKIE)
		if cookieId != "" {
			u := userdao.FindByToken(cookieId)
			beego.Debug("find user by token:" + cookieId)
			beego.Debug(u)
			if &u != nil {
				loginUser = u
			}
		}
	}

	if loginUser == nil {
		c.Data["callBack"] = callBack
		c.TplName = "login.tpl"
	} else {
		if callBack == "" {
			c.Ctx.WriteString("Validate Success")
		} else {
			temp := loginUser.(userdao.User)
			c.Redirect(callBack+"?token="+temp.Token, int(302))
		}
	}

}

func (c *MainController) Post() {
	user := c.GetString("userName")
	pwd := c.GetString("password")
	callBack := c.GetString("callBack")

	find := userdao.FindByName(user)
	fmt.Println("Find:" + find.Password)

	if &find != nil && find.Password == pwd {
		loginUser := find
		loginUser.Token = "2222222222"
		loginUser.CookieId = "1111111111"
		userdao.Update(loginUser)

		c.SetSession(LOGIN_USER, loginUser)
		c.Ctx.SetCookie(LOGIN_USER_COOKIE, loginUser.Token, 7*24*3600, "/")

		if callBack == "" {
			c.Ctx.WriteString("Validate Success")
		} else {
			c.Redirect(callBack+"?token="+loginUser.Token, int(302))
		}
	} else {
		c.Data["callBack"] = callBack
		c.Data["error"] = "UserName or Password is wrong!"
		c.TplName = "login.tpl"
	}
}
