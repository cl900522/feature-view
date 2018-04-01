package variable

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

func Print() {
	fmt.Println("v")
	return
}
