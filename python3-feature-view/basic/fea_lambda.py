import  threading
import _thread
import time

f = lambda : print("f running",end="\n")

def b():
    print("b running",end="\n")

_thread.start_new_thread(f,())
_thread.start_new_thread(b,())

time.sleep(1)