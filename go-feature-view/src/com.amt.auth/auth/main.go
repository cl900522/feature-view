package main

import "com.amt.auth/user/model"

func main() {
	var u = model.User{"A", 12, "Eating"}
	u.Say("Hello!")
}
