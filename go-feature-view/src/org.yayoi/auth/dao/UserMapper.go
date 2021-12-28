package dao

import (
	"github.com/astaxie/beego/orm"
	"org.yayoi/auth/model"
)

type UserMapper struct {
}

func newMapper() *UserMapper {
	// set default database
	orm.RegisterDataBase("default", "mysql", "root:root@tcp(127.0.0.1:3306)/test?charset=utf8", 30)

	// register model
	orm.RegisterModel(new(model.User))
	return &UserMapper{}
}

var UserDao UserMapper
UserDao := newMapper()

func (c UserMapper) Add(u model.User) {
	var o orm.Ormer = orm.NewOrm()
	o.Insert(u)
}

func (c UserMapper) FindByName(userName string) (u *model.User) {
	o := orm.NewOrm()
	qs := o.QueryTable("user")

	var users []*model.User
	num, err := qs.Filter("userName", userName).All(&users)
	if num > 0 && err == nil {
		u = users[0]
	}

	return
}

func c UserMapper Update(u model.User) {
	o := orm.NewOrm()
	o.Update(&u)
}
