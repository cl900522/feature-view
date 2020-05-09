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
	a  = iota //0
	a1 = iota //1
	a2 = iota //2
)

const (
	b  = iota //0
	b1        //1
	b2        //2
)

const (
	c  = 1 << iota //1
	c1             //2
	c2             //4
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
}
