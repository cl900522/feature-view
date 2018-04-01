package main

import (
	"fmt"

	"org.yayoi.sso.portal/user/model"
)

func main() {
	fmt.Println("Hello world")
	var user = model.User{"", "", "", 12}
	var issame = user.Check("abc")
	fmt.Println(issame)
}
