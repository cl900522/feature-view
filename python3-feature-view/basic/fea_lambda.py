import threading
import _thread
import time


def f(): return print("f running", end="\n")


def b():
    print("b running", end="\n")


# 开启线程运行
_thread.start_new_thread(f, ())
_thread.start_new_thread(b, ())

time.sleep(1)
