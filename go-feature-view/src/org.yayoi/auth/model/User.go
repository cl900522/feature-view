package model

import (
	"github.com/astaxie/beego/orm"
	_ "github.com/go-sql-driver/mysql"
)

type User struct {
	Id       int
	Token    string
	UserName string `orm:"size(100)"`
	Password string `orm:"size(100)"`
	CookieId string `orm:"size(100)"`
}

func init() {
	// set default database
	orm.RegisterDataBase("default", "mysql", "root:root@tcp(127.0.0.1:3306)/test?charset=utf8", 30)

	// register model
	orm.RegisterModel(new(User))

	// create table
	orm.RunSyncdb("default", false, true)

}

func Add(u User) {
	var o orm.Ormer = orm.NewOrm()
	o.Insert(u)
}

func FindByName(userName string) (u *User) {
	o := orm.NewOrm()
	qs := o.QueryTable("user")

	var users []*User
	num, err := qs.Filter("userName", userName).All(&users)
	if num > 0 && err == nil {
		u = users[0]
	}

	return
}

func FindByCookie(cookie string) (u *User) {
	o := orm.NewOrm()
	qs := o.QueryTable("user")

	var users []*User
	num, err := qs.Filter("cookie_id", cookie).All(&users)
	if num > 0 && err == nil {
		u = users[0]
	}

	return
}

func FindByToken(token string) (u *User) {

	o := orm.NewOrm()
	qs := o.QueryTable("user")

	var users []*User
	num, err := qs.Filter("token", token).All(&users)
	if num > 0 && err == nil {
		return users[0]
	}

	return
}

func Update(u User) {
	o := orm.NewOrm()
	o.Update(&u)
}
