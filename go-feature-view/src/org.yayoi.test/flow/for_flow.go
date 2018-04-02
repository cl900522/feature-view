package flow

import "fmt"

func for0() {
	for i := 0; i < 100; i++ {
		fmt.Print("", i)
	}
}

/**
 * replace for
 * while(true) {
 * } in java language
 *
 */
func for1() {
	i := 0
	for {
		fmt.Print("", i)
		i++
	}
}

func for2() {
	sum := 1
	for sum < 100 {
		sum += sum
	}
	fmt.Println(sum)
}
