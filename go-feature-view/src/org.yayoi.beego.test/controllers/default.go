package controllers

import (
	"github.com/astaxie/beego"
	userdao "org.yayoi.beego.test/model"
)

type MainController struct {
	beego.Controller
}

const (
	SESSION_KEY_OF_LOGIN_USER = "LOGIN_USER"
	LOGIN_TOKEN               = "token"
)

func (c *MainController) Get() {
	loginUser := c.GetSession(SESSION_KEY_OF_LOGIN_USER)
	callBack := c.GetString("callBack")

	if loginUser == nil {
		beego.Debug("session user is nil")
		token := c.Ctx.GetCookie(LOGIN_TOKEN)
		if token != "" {
			u := userdao.FindByToken(token)
			if u != nil {
				beego.Debug("find user with token: ", token, "->", u)
				loginUser = *u
				c.SetSession(SESSION_KEY_OF_LOGIN_USER, loginUser)
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

	if find != nil && find.Password == pwd {
		loginUser := find
		loginUser.Token = "2222222222"
		loginUser.CookieId = "1111111111"
		userdao.Update(*loginUser)

		beego.Debug(*loginUser)

		c.SetSession(SESSION_KEY_OF_LOGIN_USER, loginUser)
		c.Ctx.SetCookie(LOGIN_TOKEN, loginUser.Token, 7*24*3600, "/")

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
