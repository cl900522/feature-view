package model

import "fmt"

type User struct {
	Name  string
	Age   int
	Hobby string
}

func (user User) Say(words string) {
	fmt.Println("i am name", user.Name, ": ", words)
}
