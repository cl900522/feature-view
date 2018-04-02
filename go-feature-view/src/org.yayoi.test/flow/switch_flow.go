package flow

import (
	"fmt"
	"time"
)

const (
	SUN = 0
	MON = 1
	TUE = 2
	WEN = 3
)

func GetDay() int {
	return WEN
}

func swith0(day int) {
	switch day {
	case SUN:
		fmt.Print("Sunday")
	case MON:
		fmt.Print("Monday")
	case TUE:
		fmt.Print("Tuesday")
	case GetDay():
		fmt.Print("Wensday")
	case TUE + 2:
		fmt.Print("Tuesday")
	default:
		fmt.Print("Dont know")

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
