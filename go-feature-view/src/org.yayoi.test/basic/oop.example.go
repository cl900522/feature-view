package main

import "fmt"

func main() {
	var node1 Node
	node1.value = node1

	teacherTest()

	boxInteger()
}

func teacherTest() {
	var man Man
	fmt.Println("value of man is ", man)
	fmt.Printf("type of man is %T\n", man)

	var teacher Teacher
	fmt.Println("value of teacher is ", teacher)
	fmt.Printf("type of teacher is %T\n", teacher)

	man = teacher
	man.Speak("Great")
	getType(teacher)

	var player1 Player = &teacher
	player1.Jump()
	player1.Run()

	// var player2 Player = teacher
	// throws exception 因为Jump 方法的使用的指针接收器进行定义的
	// 指针接收器的方法将对指针或值都起作用
	// 在接口的情况下，如果方法有指针接收器，那么接口将具有动态类型的指针而不是动态类型的值。
}

type Object interface {
	ToString() string
}
type Comparable interface {
	Compare(o interface{}) bool
}

type Player interface {
	Jump() error
	Run() error
}

/**
嵌入式接口
接口不能实现其他接口或扩展它们，但我们可以通过合并两个或多个接口来创建新接口。
*/
type Man interface {
	Object
	Comparable
	Speak(words string) error
	Walk()
}

type Teacher struct {
	name string
}

func (t Teacher) Speak(words string) error {
	var err error
	fmt.Printf("%T say: %s\n", t, words)
	return err
}

func (t Teacher) Walk() {
	fmt.Printf("i am walking")
}

func (t Teacher) ToString() string {
	return t.name
}

func (t Teacher) Compare(o interface{}) bool {
	return false
}

func (t *Teacher) Jump() error {
	fmt.Println("i am pointer jumping")
	var err error
	return err
}

func (t Teacher) Run() error {
	fmt.Println("i am pointer running")
	var err error
	return err
}

type Node struct {
	prev, next *Node
	value      interface{}
}

/**
o.(type)不可以在switch外使用

如：fmt.Println(o.(type)) 会报错
*/
func getType(o interface{}) {

	switch o.(type) {
	case string:
		fmt.Println("type is string")
	case Teacher:
		fmt.Println("type is Teacher")
	}
}

type integer int

//类型都是基于值传递的。要想修改变量的值,只能传递指针
//基于指针定义，可以改变i的值
func (t *integer) compare(o integer) bool {
	return *t-o > 0
}

func (t *integer) add(o integer) {
	*t += o
}

func boxInteger() {
	var i integer = 12
	fmt.Println(i.compare(4))
	i.add(2)
	fmt.Println(i)
}
