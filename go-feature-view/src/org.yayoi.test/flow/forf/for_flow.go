package main

import "fmt"

func main() {
	for0()
	for1()
	for2()
	for3()
	for4()
	for5()
	for6()
}

func for0() {
	for i := 0; i < 100; i++ {
		fmt.Print("", i)
	}
}

/**
* replace for
* while(true) {
* } in java language

  go语言没有while和do-while语法支持
*
*/
func for1() {
	i := 0
	for {
		fmt.Println("", i)
		i++

		if i >= 20 {
			break
		}
	}
}

func for2() {
	sum := 1
	for sum < 100 {
		sum += sum
	}
	fmt.Println(sum)
}

func for3() {
	fmt.Println("for3 test->")
	arr := make([]int, 10)
	for i, val := range arr {
		fmt.Printf("i = %d, v = %d\n", i, val)
	}
}

func for4() {
	fmt.Println("for4 test->")
	arr := make(map[string]int, 10)
	arr["java"] = 1
	arr["c"] = 4
	arr["go"] = 23
	arr["delphi"] = 56
	for i, val := range arr {
		fmt.Printf("key = %s, v = %d\n", i, val)
	}
}

func for5() int {
	i := 0
	for {
		i++
		if i > 10 {
			return 2
		}
	}
}

func for6() {
	stop := 0
	for i, j := 1, 1; ; i, j = i+1, j+1 {
		if i*2+100 < j*10 {
			stop = i
			break fin
		}
	}

fin:
	fmt.Printf("stop idx = %d \n", stop)
	return
}
