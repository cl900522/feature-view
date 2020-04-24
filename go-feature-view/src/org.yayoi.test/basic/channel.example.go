package main

import (
	"fmt"
	"time"
)

func main() {
	chan1()
	chan2()
	speaking()
}

func sum(s []int, c chan int) {
	sum := 0
	for _, v := range s {
		sum += v
	}
	c <- sum // 把 sum 发送到通道 c
}

func chan1() {
	s := []int{7, 2, 8, -9, 4, 0}

	c := make(chan int)

	go sum(s[len(s)/2:], c)
	go sum(s[:len(s)/2], c)
	x, y := <-c, <-c // 从通道 c 中接收

	fmt.Println(x, y, x+y)
	close(c)
}

func chan2() {
	// 这里我们定义了一个可以存储整数类型的带缓冲通道
	// 缓冲区大小为2
	ch := make(chan int, 2)

	// 因为 ch 是带缓冲的通道，我们可以同时发送两个数据
	// 而不用立刻需要去同步读取数据
	ch <- 1
	ch <- 2

	// 获取这两个数据
	fmt.Println(<-ch)
	fmt.Println(<-ch)
	close(ch) // 关闭缓冲区
}

func say(s string) {
	for i := 0; i < 5; i++ {
		time.Sleep(100 * time.Millisecond)
		fmt.Println(s)
	}
}

func speaking() {
	go say("hello")
	say("world")
}
