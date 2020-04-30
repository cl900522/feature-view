package main

/**
用户层眼中看到的goroutine中的“block socket”，实际上是通过Go runtime中的netpoller通过Non-block socket + I/O多路复用机制“模拟”出来的，真实的underlying socket实际上是non-block的，只是runtime拦截了底层socket系统调用的错误码，并通过netpoller和goroutine 调度让goroutine“阻塞”在用户层得到的Socket fd上。比如：当用户层针对某个socket fd发起read操作时，如果该socket fd中尚无数据，那么runtime会将该socket fd加入到netpoller中监听，同时对应的goroutine被挂起，直到runtime收到socket fd 数据ready的通知，runtime才会重新唤醒等待在该socket fd上准备read的那个Goroutine。而这个过程从Goroutine的视角来看，就像是read操作一直block在那个socket fd上似的。具体实现细节在后续场景中会有补充描述。
*/
func main() {

}
