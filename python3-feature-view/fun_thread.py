#!python
#!/usr/bin/python

import _thread,time
import threading

def output(msg):
    print(msg)

threadLock = threading.Lock()

'''RLock
threading.RLock() 多重锁，在同一线程中可用被多次acquire。如果使用RLock，那么acquire和release必须成对出现，
调用了n次acquire锁请求，则必须调用n次的release才能在线程中释放锁对象'''

def print_time(threadName, delay, counter):
    while counter:
        time.sleep(delay)
        print ("%s: %s" % (threadName, time.ctime(time.time())))
        counter -= 1

class MyThread(threading.Thread):
    def __init__(self, threadID, name, counter):
        threading.Thread.__init__(self)
        self.threadID = threadID
        self.name = name
        self.counter = counter

    def run(self):
        threadLock.acquire()
        print ("开始线程：" + self.name)
        print_time(self.name, self.counter, 5)
        print ("退出线程：" + self.name)
        threadLock.release()


if __name__ == "__main__":
    _thread.start_new_thread(output, ("Nice",));
    _thread.start_new_thread(output, ("Join",));
    _thread.start_new_thread(output, ("Liwei",));
    _thread.start_new_thread(output, ("Good",));

    time.sleep(1)
    input("##Press Enter to go on run Mythread $:...")

    thread1 = MyThread(1, "Thread-1", 1)
    thread2 = MyThread(2, "Thread-2", 2)
    # thread2.setDaemon(True) //thread2 will auto stop 
    thread2.daemon = True
    thread1.start()
    thread2.start()
    thread1.join()
    # thread2.join()

    print("Exit in 1s...")
    # time.sleep(1)
