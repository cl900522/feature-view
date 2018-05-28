package main

import (
	"fmt"
	"os"

	"org.yayoi.test/variable"
)

func main() {
	variable.Print()
}

func pritArgs() {
	var s, sep string
	for i := 1; i < len(os.Args); i++ {
		s += sep + os.Args[i]
		sep = " "
	}
	fmt.Println(s)
}
