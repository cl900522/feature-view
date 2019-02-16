

from expert.libraray import Basic

assert hasattr(Basic, "hasLeg"), "Basic has no leg fun"


class User(Basic):
    def __init__(self):
        pass

    def jump(self):
        if self.hasLeg():
            print("I can jump 100m")


print("load User success")
if __name__ != "__main__":
    print(User().jump())
