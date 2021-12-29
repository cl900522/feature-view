package main

import (
	"bytes"
	"log"
	"os/exec"
	"strings"
)

func main() {
	runCmd("pwd")

	runCmdWithInput("/bin/sh", "cd /home/chenmx/\nls\nexit")
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

func runCmdWithInput(cmdStr string, args string) {
	cmd := exec.Command(cmdStr)

	var out bytes.Buffer
	cmd.Stdout = &out
	cmd.Stdin = strings.NewReader(args)

	err := cmd.Run()
	if err != nil {
		log.Fatal(err)
	}

	log.Print(out.String())
}
