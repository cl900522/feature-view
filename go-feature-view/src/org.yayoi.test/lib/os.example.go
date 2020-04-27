package main

import (
	"fmt"
	"log"
	"os"
)

func main() {
	file()

	env()

	pipe()

	fmt.Printf("pageSize=%d\n", os.Getpagesize())
	fmt.Printf("tempDir=%s\n", os.TempDir())
	os.Exit(1)
}

func file() {
	f, err := os.OpenFile("access.log", os.O_RDWR|os.O_CREATE, 0755)
	if err != nil {
		log.Fatal(err)
	}
	f.WriteString("hello world")

	fi, err := f.Stat()

	fmt.Printf("isDir=%t, modtime=%d, name=%s\n", fi.IsDir(), fi.ModTime(), fi.Name())
	f.Close()
}

func env() {
	os.Setenv("jd", "jd.com")
	jd := os.Getenv("jd")
	fmt.Printf("jd=%s\n", jd)
	os.Unsetenv("jd")
	jd2 := os.Getenv("jd")
	fmt.Printf("jd=%s\n", jd2)
}

func pipe() {
	r, w, _ := os.Pipe()
	//w.WriteString("hello there")
	w.Write([]byte("hello there"))

	rbs := make([]byte, 100)
	len, _ := r.Read(rbs)
	fmt.Printf("read from pip: %s\n", string(rbs[0:len]))
}
