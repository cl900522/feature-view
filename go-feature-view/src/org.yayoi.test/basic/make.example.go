package main

import (
	"fmt"
	"unsafe"
)

func main() {
	newObject()
	newMap()

	makeSplice()
	makeMap()
	makeChannel()
}

type Book struct {
	name string
	id   int64
}

func newObject() {
	b := new(Book)
	b.id = 12
	b.name = "Go"
	fmt.Println(b)
	fmt.Println(*b)

	fmt.Println(unsafe.Pointer(b))
}

func newMap() {
	b := new(map[string]string)
	if b != nil {
		fmt.Println("new map empty")
		return
	}
	(*b)["1"] = "2"
	fmt.Println(*b)
}

func makeSplice() {
	arr := make([]int, 20)
	arr[12] = 1
	fmt.Println(arr)
}

func makeMap() {
	map1 := make(map[int32]string, 2)
	map1[1] = "hello"
	map1[2] = "world"
	map1[3] = "golang"

	fmt.Println(map1)
	delete(map1, 3) //删除元素
	fmt.Println(map1)
}
func makeChannel() {
	channel1 := make(chan int, 20)
	channel1 <- 12
	channel1 <- 22
	fmt.Println(<-channel1)
	fmt.Println(<-channel1)
}
