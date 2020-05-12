package main

import (
	"fmt"
	"math/rand"
)

func main() {
	if1(20)
	if2(-1)
	if3()
}

/**
 * [v] variable is avaliable in if scope
 *
 */
func if1(lim int) int {
	r := rand.New(rand.NewSource(99))
	if v := r.Int(); v < lim {
		return v
	}
	return lim
}

func if2(v int) (x int) {
	if v < 0 {
		x = v
	} else if v < 20 {
		x = v * 2
	} else {
		x = v - 3
	}
	return
}

func if3() {
	if a := 2; a > 0 {
		fmt.Println("a is bigger than 0:", a)
	}
}
