package flow

import "fmt"

func Defer0() {
	defer fmt.Println("world")

	fmt.Println("hello")
}

func Defer1() {
	fmt.Println("counting")

	for i := 0; i < 10; i++ {
		defer fmt.Println(i)
	}

	fmt.Println("done")
}
