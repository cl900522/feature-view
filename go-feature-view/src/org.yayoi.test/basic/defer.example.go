package main

import "fmt"

/**

defer和go一样都是Go语言提供的关键字。defer用于资源的释放，会在函数返回之前进行调用。
如果有多个defer表达式，调用顺序类似于栈，越后面的defer表达式越先被调用。

defer关键字的实现跟go关键字很类似，不同的是它调用的是runtime.deferproc而不是runtime.newproc。
在defer出现的地方，插入了指令call runtime.deferproc，然后在函数返回之前的地方，插入指令call runtime.deferreturn。

*/

func main() {
	fmt.Println(f())
	fmt.Println(f1())

	fmt.Println(f2(0))
	fmt.Println(f3(0))
}

/**
是返回的result值++
*/
func f() (result int) {
	defer func() {
		result++
	}()
	return 0
}

func f1() (r int) {
	t := 5
	defer func() {
		t = t + 5
	}()
	return t
}

func f2(t int) int {
	t += 1
	defer func() {
		t = t + 1
	}()
	return t
}

func f3(t int) int {
	t += 1
	defer func(t int) {
		t = t + 1
	}(t)
	return t
}
