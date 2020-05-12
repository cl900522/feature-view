package main

import (
	"fmt"
	"time"
)

func main() {
	switch0(0)
	switch1()
	switch2()
}

const (
	SUN = 0
	MON = 1
	TUE = 2
	WEN = 3
)

func GetDay() int {
	return WEN
}

func switch0(day int) {
	switch day {
	case SUN:
		fmt.Println("Sunday")
	case MON:
		fmt.Println("Monday")
	case TUE:
		fmt.Println("Tuesday")
	case GetDay():
		fmt.Println("Wensday")
	case TUE + 2:
		fmt.Println("Tuesday")
	default:
		fmt.Println("Dont know")

	}
}

func switch1() {
	t := time.Now()
	switch {
	case t.Hour() < 12:
		fmt.Println("Good morning!")
	case t.Hour() < 17:
		fmt.Println("Good afternoon.")
	default:
		fmt.Println("Good evening.")
	}
}

func switch2() {
	m := 2
	switch m {
	case 0, 1:
		fmt.Println("1")
	case 2:
		fallthrough
	case 3:
		fmt.Println("3")
	default:
		fmt.Println("unsupport")
	}
}
