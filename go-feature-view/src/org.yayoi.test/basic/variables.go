package main

import "fmt"

var b bool = false

var name string = "Jone"

var (
	i1 int = 12
	i2 int8
	i3 int16
	i4 int32
	i6 int64
)

var (
	u1 uint
	u2 uint8
	u3 uint16
	u4 uint32 = uint32(12)
	u5 uint64 = 1<<64 - 1
	u6 uintptr
)

var b1 byte = 'c' // alias for uint8

var r1 rune = 'c' // alias for int32 represents a Unicode code point

var f1 float32 = 25123.22
var f2 float64 = float64(f1)

var c1 complex64 = -5 + 12i
var c2 complex128 = -5 + 12i

var c, python, java = true, false, "no!"

//c, python, java := true, false, "no!" // := can only be use in function

const Pi = 3.14
const (
	USE_HAND = false
	USE_HEAD = false // Constants cannot be declared using the := syntax.
)

// iota比较特殊，可以被认为是一个可被编译器修改的常量，在每一个const关键字出现时被
// 重置为0，然后在下一个const出现之前，每出现一次iota，其所代表的数字会自动增1。
const (
	a0 = iota //0
	a1 = iota //1
	a2 = iota //2
)

const (
	m0 = iota //0
	m1        //1
	m2        //2
)

const (
	n0 = 1 << iota //1
	n1             //2
	n2             //4
)

// 一种枚举的定义方式
const (
	Sunday = iota
	Monday
	Tuesday
	Wednesday
	Thursday
	Friday
	Saturday
	numberOfDays
)

func main() {
	var m1 map[string]string
	m1 = make(map[string]string)
	// 最后给已声明的map赋值
	m1["a"] = "aa"
	m1["b"] = "bb"
	fmt.Printf("%s\n", m1)

	m2 := make(map[string]string)
	// 然后赋值
	m2["a"] = "aa"
	m2["b"] = "bb"
	fmt.Printf("%s\n", m2)

	/**
	Go语言中的大多数类型都基于值语义,包括:
	 基本类型,如 byte 、 int 、 bool 、 float32 、 float64 和 string 等;
	 复合类型,如数组(array) 、结构体(struct)和指针(pointer)等。
	*/

	arrIsValueOrRef()
	mapIsValueOrRef()
	chanIsValueOrRef()
	objIsValueOrRef()
}

//数传递一个数组的时候基于引用语义,但是在结构体中定义数组变量的时候基于值语义(表现在为结构体赋值的时候,该数组会被完整地复制)
func arrIsValueOrRef() {
	var a = [3]int{1, 2, 3}
	var b = a
	b[1]++
	fmt.Println(a, b)

	// 如果d取a的一部分，则还是引用
	var d = a[:2]
	d[1]++
	fmt.Println(a, d)

	var c = &a
	c[1]++
	fmt.Println(a, *c)

}

// map作为函数入参或者结构体定义都是引用语义
func mapIsValueOrRef() {
	var a = make(map[string]int, 10)
	a["java"] = 1
	var b = a
	b["java"] = 2
	fmt.Println(a, b)

	changeMap(a)
	fmt.Println(a)
}

func changeMap(m map[string]int) {
	m["java"] = 22
}

// chan作为函数入参或者结构体定义都是引用语义
func chanIsValueOrRef() {
	var a = make(chan int, 10)
	a <- 12
	a <- 11
	var b = a
	b <- 10
	b <- 9

	putChan(a)

	for {
		if len(a) == 0 {
			break
		}
		fmt.Printf("val =%d,cap = %d, len = %d\n", <-a, cap(a), len(a))
	}
}

func putChan(c chan int) {
	c <- 99
}

type book struct {
	name string
	age  int
}

// 对象作为函数入参或者结构体赋值都是基于值语义(表现在为该对象会被完整地复制)
func objIsValueOrRef() {
	var a = book{name: "golang", age: 22}
	var b = a
	b.name = "clang"
	b.age = 10
	fmt.Println(a, b)

	changeBook(a)
	fmt.Println(a)

	changeBookPtr(&a)
	fmt.Println(a)

}

func changeBook(bok book) {
	bok.name = "javalang"
}

func changeBookPtr(bok *book) {
	bok.name = "javalang"
}
