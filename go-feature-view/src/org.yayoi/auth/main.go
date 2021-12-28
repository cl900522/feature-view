package main

import (
	beego "github.com/astaxie/beego"
	session "github.com/astaxie/beego/session"
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
