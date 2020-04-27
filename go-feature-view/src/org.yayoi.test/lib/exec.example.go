package main

import (
	"bytes"
	"log"
	"os/exec"
)

func main() {
	runCmd("pwd")
}

func runCmd(cmdStr string) {
	cmd := exec.Command(cmdStr)
	var out bytes.Buffer

	cmd.Stdout = &out

	err := cmd.Run()
	if err != nil {
		log.Fatal(err)
	}

	log.Print(out.String())
}
