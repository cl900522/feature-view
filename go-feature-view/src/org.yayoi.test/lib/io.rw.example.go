package main

import (
	"bufio"
	"bytes"
	"encoding/base64"
	"encoding/binary"
	"fmt"
	"io"
	"os"
	"strings"
	"time"
)

func main() {
	pipeTest()
	return

	bufferReader()
	copyTest()

	stringReader()
	bytesReader()

	objectIO()
	objectIOToFile()
}

func base64Test() {
	input := []byte("foo\x00bar")
	encoder := base64.NewEncoder(base64.StdEncoding, os.Stdout)
	encoder.Write(input)

	input2 := []byte("foo\x00bar")
	buffer2 := new(bytes.Buffer)
	encoder2 := base64.NewEncoder(base64.StdEncoding, buffer2)
	encoder2.Write(input2)
	fmt.Println(string(buffer2.Bytes()))
}

func stringReader() {
	r := strings.NewReader("Google")
	bs := make([]byte, 100)
	r.Read(bs)
	fmt.Println("strings.Reader-> bytes: ", string(bs))

	r = strings.NewReader("Google")
	buf := new(bytes.Buffer)
	buf.ReadFrom(r)
	fmt.Println("strings.Reader-> buffer: ", string(buf.Bytes()))
}

func bytesReader() {
	r := bytes.NewReader([]byte("Google"))
	buf := new(bytes.Buffer)
	buf.ReadFrom(r)
	fmt.Println("byte.Reader-> buffer: ", string(buf.Bytes()))
}

/**

使用binary包时，必须使用明确的长度确定的类型，可以用int32，但别用int

*/

type Protocol struct {
	Version  uint8
	BodyLen  uint16
	Reserved [2]byte
	Unit     uint8
	Value    uint32
	// Name string 如果带有sting字段写入会产生错误
}

func objectIO() {
	var p *Protocol = new(Protocol)
	p.Version = 12
	p.Unit = 99

	var writer bytes.Buffer
	err := binary.Write(&writer, binary.LittleEndian, *p)
	if err != nil {
		fmt.Println("Err Write")
		return
	}
	fmt.Println(writer.Len())

	var bin []byte = writer.Bytes()
	co := new(Protocol)
	err = binary.Read(bytes.NewReader(bin), binary.LittleEndian, co)
	if err != nil {
		fmt.Println("Err Read")
		return
	}
	fmt.Println(co)
}

func objectIOToFile() {
	var p *Protocol = new(Protocol)
	p.Version = 12
	p.Unit = 99

	outf, _ := os.Create("tmp.txt")
	err := binary.Write(outf, binary.LittleEndian, *p)
	if err != nil {
		fmt.Println("Err Write")
		return
	}
	outf.Close()

	co := new(Protocol)
	inf, _ := os.Open("tmp.txt")
	err = binary.Read(inf, binary.LittleEndian, co)
	if err != nil {
		fmt.Println("Err Read")
		return
	}
	fmt.Println(co)

	inf.Close()

	err = os.Remove("tmp.txt")
	if err != nil {
		fmt.Println("删除失败")
		return
	}
}

/**
文件拷贝
*/
func copyTest() {
	in, _ := os.Open("./.gitignore")
	out, _ := os.Create("gitignore.cp.go")
	io.Copy(out, in)
	out.Close()
	in.Close()

	fmt.Println("copy success")
	defer func() {
		time.Sleep(time.Second * 5)
		os.Remove(out.Name())
		fmt.Println("delete success")
	}()
}

func bufferReader() {
	file, _ := os.Open(".gitignore")
	reader := bufio.NewReader(file)

	for {
		line, err := reader.ReadString('\n')
		if err != nil {
			if err == io.EOF {
				break
			} else {
				fmt.Println(err)
				os.Exit(1)
			}
		}
		fmt.Print(line)
	}

}

func pipeTest() {
	proverbs := new(bytes.Buffer)
	proverbs.WriteString("Channels orchestrate mutexes serialize\n")
	proverbs.WriteString("Cgo is not Go\n")
	proverbs.WriteString("Errors are values\n")
	proverbs.WriteString("Don't panic\n")

	piper, pipew := io.Pipe()

	// 将 proverbs 写入 pipew 这一端
	go func() {
		defer pipew.Close()
		io.Copy(pipew, proverbs)
	}()

	// 从另一端 piper 中读取数据并拷贝到标准输出
	io.Copy(os.Stdout, piper)
	piper.Close()
}
