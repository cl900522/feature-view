package main

import (
	"fmt"
	"reflect"
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
