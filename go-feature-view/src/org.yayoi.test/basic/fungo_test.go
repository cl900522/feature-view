package basic

import (
	"fmt"
	"testing"
)

/**
 * 1. Go中的数组是值类型，换句话说，如果你将一个数组赋值给另外一个数组，那么，实际上就是将整个数组拷贝一份
 * 2. 如果Go中的数组作为函数的参数，那么实际传递的参数是一份数组的拷贝，而不是数组的指针。这个和C要区分开。因此，在Go中如果将数组作为函数的参数传递的话，那效率就肯定没有传递指针高了。
 * 3. array的长度也是Type的一部分，这样就说明[10]int和[20]int是不一样的。array的结构用图示表示是这样的：
 * @type {[type]}
 */
func TestChangeArr(t *testing.T) {
	arr := [3]int{1, 2, 4}
	arr1 := ChangeArr(arr)
	arr2 := arr
	arr2[1] = 100

	fmt.Println("arr", arr)
	fmt.Println("arr1", arr1)
	fmt.Println("arr2", arr2)
}

func TestChangeSplice(t *testing.T) {
	arr := []int{1, 2, 4}
	arr1 := ChangeSplice(arr)

	fmt.Println("arr", arr)
	fmt.Println("arr1", arr1)
}

func TestGetPointer(t *testing.T) {
	g1 := G{Id: "1"}
	g2 := new(G)
	g3 := new(G)

	g1p1 := &g1
	g1p2 := GetPointer(g1)
	fmt.Println(g1p1 == g1p2)
	fmt.Println(g1)
	fmt.Println(*g1p2)
	t.Log("函数入参的对象是经过拷贝的，而不是原对象")

	fmt.Println(g2)
	fmt.Println(g3)

}
