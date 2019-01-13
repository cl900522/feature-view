#!python
#!/usr/bin/python3


class Vector:
    x=0
    y=0

    def __init__(self, x, y):
        self.x = x
        self.y = y
        print(self.__class__)

    def __add__(me, other):
        return Vector(me.x+other.x, me.y+other.y)

    def __str__(self):
        return 'Vector (%d,%d)' % (self.x, self.y)

    def __gt__(self, other):
        if (self.x**2 + self.y**2 - other.x**2 - other.y**2 > 0):
            return 1
        else:
            return -1

v1 = Vector(2,2)
v2 = Vector(1,5)
v3 = v1+v2

print(v3)
L = [v1, v2, v3]
print(v1>v2)
print(id(v3))
