package controllers

import (
	"github.com/astaxie/beego"
	"org.yayoi.beego.test/model"
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
		cookieId := c.Ctx.GetCookie(LOGIN_USER_COOKIE)
		if cookieId == "123981023" {
			loginUser = model.User{"123981023", "123"}
		}
	}

	if loginUser == nil {
		c.Data["callBack"] = callBack
		c.TplName = "login.tpl"
	} else {
		if callBack == "" {
			c.Ctx.WriteString("Validate Success")
		} else {
			temp := loginUser.(model.User)
			c.Redirect(callBack+"?token="+temp.Token, int(302))
		}
	}

}

func (c *MainController) Post() {
	user := c.GetString("userName")
	pwd := c.GetString("password")
	callBack := c.GetString("callBack")

	if user == "admin" && pwd == "admin" {
		loginUser := model.User{"123981023", "123"}
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
