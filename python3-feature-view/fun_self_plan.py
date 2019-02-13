#!python
#!/usr/bin/python3

import threading
import _thread
from time import ctime
from atexit import register

def atexit_1():
    print("All Done")

@register
def atexit_2():
    print("All finished",end="")

register(atexit_1)

lock = _thread.allocate_lock()

lock.acquire()

print(lock.locked())

lock.release()

print(lock.locked())

print(ctime())
