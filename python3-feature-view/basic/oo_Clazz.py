
class Clazz:
    objects = "started"

    def do():
        print("i am doing")

    def cover(self):
        self.objects="mine"

Clazz.do()

print(Clazz.objects)

c = Clazz()
print(c.objects)
c.cover()
print(c.objects)
c.do()