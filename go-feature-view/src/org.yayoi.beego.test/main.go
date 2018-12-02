package main

import (
	"github.com/astaxie/beego"
	"github.com/astaxie/beego/session"
	_ "org.yayoi.beego.test/routers"
)

var globalSessions *session.Manager

func main() {
	beego.Run()

	sessionConfig := &session.ManagerConfig{
		CookieName:      "gosessionid",
		EnableSetCookie: true,
		Gclifetime:      3600,
		Maxlifetime:     3600,
		Secure:          false,
		CookieLifeTime:  3600,
		ProviderConfig:  "./tmp",
	}
	globalSessions, _ = session.NewManager("memory", sessionConfig)
	go globalSessions.GC()
}
