package main

import (
	"fmt"
	"unsafe"
)

type order struct {
	id      int64
	thirdId int32
	len     int
	active  bool
	name    string
	date    int64
}

func main() {
	o := order{
		id:      12,
		thirdId: 23,
		len:     1,
		active:  true,
		name:    "nick5123",
		date:    222,
	}
	offsetOfField(o)
	offsetChanegeFieldValue()
}
func offsetOfField(o order) {
	ptr := unsafe.Pointer(&o)
	fmt.Printf("ptr->%d\n", ptr)
	fmt.Printf("id offset->%d\n", unsafe.Offsetof(o.id))
	fmt.Printf("thirdId offset->%d\n", unsafe.Offsetof(o.thirdId))
	fmt.Printf("len offset->%d\n", unsafe.Offsetof(o.len))
	fmt.Printf("active offset->%d\n", unsafe.Offsetof(o.active))
	fmt.Printf("name offset->%d\n", unsafe.Offsetof(o.name))
	fmt.Printf("date offset->%d\n", unsafe.Offsetof(o.date))
}

type T struct {
	t1 byte
	t2 int32
	t3 int64
	t4 string
	t5 bool
}

func offsetChanegeFieldValue() {
	fmt.Println("----------unsafe.Pointer---------")
	t := &T{1, 2, 3, "this is a example", true}
	ptr := unsafe.Pointer(t)
	t1 := (*byte)(ptr)
	fmt.Println(*t1)

	t2 := (*int32)(unsafe.Pointer(uintptr(ptr) + unsafe.Offsetof(t.t2)))
	*t2 = 99
	fmt.Println(t)
	t3 := (*int64)(unsafe.Pointer(uintptr(ptr) + unsafe.Offsetof(t.t3)))
	fmt.Println(*t3)
	*t3 = 123
	fmt.Println(t)
}
