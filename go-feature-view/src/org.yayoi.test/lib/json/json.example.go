package main

import (
	"encoding/json"
	"fmt"
)

type Domain struct {
	Host string `json:"host"`
	Port int    `json:"port"`
}

func main() {
	jbytes, _ := json.Marshal(Domain{Host: "192.1.2.2"})
	fmt.Println(string(jbytes))

	var de Domain
	json.Unmarshal(jbytes, &de)
	fmt.Println(de.Host)
}
