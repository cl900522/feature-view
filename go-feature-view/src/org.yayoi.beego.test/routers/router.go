package routers

import (
	"fmt"
	"net/http"

	"github.com/astaxie/beego"
	"org.yayoi.beego.test/controllers"
)

func page_not_found(rw http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(rw, "This is a 404 page, we don't have the resource you request")
}

func init() {
	beego.ErrorHandler("404", page_not_found)
	beego.Router("/login", &controllers.MainController{})

}
