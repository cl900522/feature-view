package main

import (
	"fmt"
	"os/user"
)

func main() {
	t, _ := user.Current()
	printUser(*t)

	u, _ := user.Lookup("chenmx")
	printUser(*u)

	r, _ := user.Lookup("root")
	printUser(*r)
}

func printUser(t user.User) {
	fmt.Println("==================")
	fmt.Printf("Name->%s\n", t.Name)
	fmt.Printf("HomeDir->%s\n", t.HomeDir)
	fmt.Printf("Uid->%s\n", t.Uid)
	fmt.Printf("Gid->%s\n", t.Gid)
	fmt.Printf("Username->%s\n", t.Username)
}
