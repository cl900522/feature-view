from sqlite3 import connect
from contextlib import contextmanager


class ctxmanger:
    def __init__(self, gen):
        self.gen = gen

    def __call__(self, *args, **kwargs):
        self.args, self.kwargs = args, kwargs
        return self

    def __enter__(self):
        self.op = self.gen(*self.args, **self.kwargs)
        next(self.op)

    def __exit__(self, *args):
        next(self.op, None)


@contextmanager
# @ctxmanger
def temp(cur):
    print("create table")
    cur.execute("create table points(x int, y int)")
    try:
        yield
    finally:
        print("drop table")
        cur.execute("drop table points")


with connect("sqlite3.db") as conn:
    cur = conn.cursor()
    with temp(cur):
        cur.execute("insert into points(x,y) values(1, 1)")
        cur.execute("insert into points(x,y) values(1, 21)")
        cur.execute("insert into points(x,y) values(12, 1)")
        for row in cur.execute("select * from points"):
            print(row)
