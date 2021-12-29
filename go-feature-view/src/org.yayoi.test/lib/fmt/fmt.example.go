package main

import "fmt"

func main() {
	scan()
	print()
}

func scan() {
	var name string
	var age int32
	/* scan */
	fmt.Println("请输入名称和年龄：")
	c1, _ := fmt.Scan(&name, &age)
	if c1 > 0 {
		fmt.Print("")
	}
	fmt.Printf("name=%s, age=%d\n", name, age)
	/* sscan */
	c2, _ := fmt.Sscan("join 12", &name, &age)
	if c2 > 0 {
		fmt.Print("")
	}
	fmt.Printf("name=%s, age=%d\n", name, age)

	/* scanf */
	var a, b, c int
	for i := 0; i < 2; i++ {
		fmt.Scanf("%d,%d,%d", &a, &b, &c)
		fmt.Println(a, b, c)
	}

}

func print() {
	out := fmt.Sprintf("Name: %s\n", "Hello")
	fmt.Println(out)
}
