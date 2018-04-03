package routers

import (
	"github.com/astaxie/beego"
	"org.yayoi.beego.test/controllers"
)

func init() {
	beego.Router("/", &controllers.MainController{})

}
