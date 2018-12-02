package flow

import (
	f "fmt"
)

/**
 * 测试defer，让直行延后
 * @type {[type]}
 */
func Defer0() {
	defer f.Println("world")

	f.Println("hello")
}

/**
 * 测试defer，让直行延后
 * @type {[type]}
 */
func Defer1() {
	f.Println("counting")

	for i := 0; i < 10; i++ {
		defer f.Println(i)
	}

	f.Println("done")
}
