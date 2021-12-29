package main

import (
	"bytes"
	"fmt"
	"log"
	"os/exec"
	"reflect"
	"strconv"
)

func main() {
	o := order{
		ordId:      1234,
		customerId: 567,
	}

	printInt()

	printOrder(o)

	printSql(o)

	createQuery(o)

	reflectRunCmd("pwd")

	relfectPrints()
}

func printInt() {
	i := 10
	fmt.Printf("%d %T \n", i, i)
}

type order struct {
	ordId      int
	customerId int
}

func printOrder(q interface{}) {
	fmt.Println(q)
}

func printSql(o order) {
	sql := fmt.Sprintf("insert into order values(%d, %d)", o.ordId, o.customerId)
	fmt.Println(sql)
}

func createQuery(q interface{}) {
	if reflect.ValueOf(q).Kind() == reflect.Struct {
		v := reflect.ValueOf(q)
		fmt.Println("Number of fields", v.NumField())
		for i := 0; i < v.NumField(); i++ {
			f := v.Field(i)
			fmt.Printf("Field:%d, type:%T, value:%v\n", i, f, f)
		}

		t := reflect.TypeOf(q)
		fmt.Println("Number of fields", t.NumField())
		for i := 0; i < t.NumField(); i++ {
			f := t.Field(i)
			fmt.Printf("Field:%d, type:%T, value:%v, name:%s\n", i, f, f, f.Name)
		}
	}
}

func reflectRunCmd(cmdStr string) {
	params := make([]reflect.Value, 1)  //参数
	params[0] = reflect.ValueOf(cmdStr) //参数设置为20

	f := reflect.ValueOf(exec.Command)
	c := f.Call(params)

	cmd := (c[0].Interface().(*exec.Cmd))

	var out bytes.Buffer

	cmd.Stdout = &out

	err := cmd.Run()
	if err != nil {
		log.Fatal(err)
	}

	log.Print(out.String())
}

func prints(i int) string {
	fmt.Println("i =", i)
	return strconv.Itoa(i)
}

func relfectPrints() {
	fv := reflect.ValueOf(prints)

	params := make([]reflect.Value, 1)                 //参数
	params[0] = reflect.ValueOf(20)                    //参数设置为20
	rs := fv.Call(params)                              //rs作为结果接受函数的返回值
	fmt.Println("result:", rs[0].Interface().(string)) //当然也可以直接是rs[0].Interface()
}
