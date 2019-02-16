
from time import time, sleep


def taspect(fun):
    def warped(*args, **kwargs):
        s = time()
        res = fun(*args, **kwargs)
        sleep(0.01)
        e = time()
        print("Cost:", e-s)
        return res
    return warped


def naspect(n):
    def taspect(fun):
        def warped(*args, **kwargs):
            s = time()
            for _ in range(n):
                res = fun(*args, **kwargs)
            e = time()
            print(n, "Times Cost:", e-s)
            return res
        return warped
    return taspect


@taspect
@naspect(11)
def add(a, b):
    return a+b


print(add(12, 22))
