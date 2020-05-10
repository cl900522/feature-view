package main

import "fmt"

func main() {
	err1()
	err2()
}

func err1() {
	perr := ParamError{field: "name", cause: "is null"}
	var e error = perr
	fmt.Println(e.Error())
}

type ParamError struct {
	field string
	cause string
}

func (t ParamError) Error() string {
	return t.field + " has error-> " + t.cause
}

func err2() {
	defer func() {
		if r := recover(); r != nil {
			fmt.Println("error case object is:", r)
		}
	}()

	panic(ParamError{field: "age", cause: " not int"})
}
